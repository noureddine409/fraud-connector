package ma.adria.adapter.listener;

import io.debezium.config.Configuration;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class CDCListener {
    private final Executor executor;
    private final DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    public CDCListener(Configuration postgresConnector) {
        this.executor = Executors.newSingleThreadExecutor();

        // Create a new DebeziumEngine instance.
        this.debeziumEngine = DebeziumEngine.create(Json.class)
                .using(postgresConnector.asProperties())
                //This is where your CDC events will be passed to
                .notifying(this::handleEvent)
                .build();
    }

    private void handleEvent(ChangeEvent<String, String> event) {
        log.info("received change event: {}", event);
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
