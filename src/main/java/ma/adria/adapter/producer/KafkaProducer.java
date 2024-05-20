package ma.adria.adapter.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Component responsible for sending messages to Kafka topics.
 */
@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.event.topic}")
    private String defaultTopicName;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic(defaultTopicName);
        log.info("KafkaProducer initialized with default topic: {}", defaultTopicName);
    }

    public void sendEvent(String message) {
        final String topicName = defaultTopicName;
        log.info("Preparing to send message to topic: {}", topicName);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        log.info("Message sent to topic {}: {}", topicName, message);

        future.thenAccept(result -> log.info("Message [{}] delivered to topic [{}] with offset [{}]", message, topicName, result.getRecordMetadata().offset())).exceptionally(ex -> {
            log.error("Failed to deliver message [{}] to topic [{}]. Exception: {}", message, topicName, ex.getMessage());
            log.debug("Stack trace: ", ex);
            return null;
        });

        log.debug("Exiting sendEvent method");
    }
}
