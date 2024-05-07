package ma.adria.adapter.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import ma.adria.adapter.classification.EventClassification;
import ma.adria.adapter.classification.EventClassifier;
import ma.adria.adapter.dto.DebeziumEvent;
import ma.adria.adapter.producer.KafkaProducer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static ma.adria.adapter.dto.DebeziumEvent.OperationType.CREATE;

/**
 * Listener component responsible for capturing Change Data Capture (CDC) events from a Debezium engine
 * and processing them accordingly.
 */
@Component
@Slf4j
public class CDCListener {
    private final Executor executor;
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;
    private final ObjectMapper objectMapper;
    private final EventClassifier eventClassifier;
    private final KafkaProducer kafkaProducer;

    public CDCListener(Configuration postgresConnector, ObjectMapper objectMapper, EventClassifier eventClassifier, KafkaProducer kafkaProducer) {
        this.objectMapper = objectMapper;
        this.eventClassifier = eventClassifier;
        this.kafkaProducer = kafkaProducer;
        this.executor = Executors.newSingleThreadExecutor();

        // Create a new DebeziumEngine instance.
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(postgresConnector.asProperties())
                //This is where your CDC events will be passed to
                .notifying(this::handleEvent)
                .build();
    }

    /**
     * Handles incoming ChangeEvent from the Debezium engine.
     *
     * @param event ChangeEvent containing the CDC event data.
     */
    private void handleEvent(ChangeEvent<String, String> event) {
        log.info("received change event: {}", event);
        try {
            final var debeziumEvent = objectMapper.readValue(event.value(), DebeziumEvent.class);
            if (CREATE.equals(debeziumEvent.getPayload().getOperationType())) {
                final Map<String, Object> eventRow = debeziumEvent.getPayload().getAfter();
                log.info("new inserted row {}", eventRow);
                final EventClassification eventClassification = eventClassifier.classify(eventRow);
                log.info("event classification {}", eventClassification);
                if (EventClassification.NON_APPLICABLE.equals(eventClassification)) {
                    log.info("ignoring non-applicable event classification {}", eventClassification);
                    return;
                }
                final String eventAsMessage = eventClassification.getMapProcessingFunction().map(eventRow, eventClassification, objectMapper);
                log.info("event as message {}", eventAsMessage);
                kafkaProducer.sendEvent(eventAsMessage, eventClassification.getTopicId());
            }
        } catch (JsonProcessingException e) {
            log.error("failed to deserialize change event", e);
        }
    }


    @PostConstruct
    private void start() {
        log.info("Starting CDC listener");
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        log.info("Stopping CDC listener");
        if (this.debeziumEngine != null) {
            this.debeziumEngine.close();
        }
    }


}
