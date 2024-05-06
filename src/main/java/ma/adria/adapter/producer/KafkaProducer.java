package ma.adria.adapter.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.event.topic}")
    private String defaultTopicName;
    @Value("${kafka.event.topic-prefix}")
    private String topicPrefix;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic(defaultTopicName);
    }

    public void sendEvent(String message) {
        kafkaTemplate.send(defaultTopicName, message);
        log.info("Message sent to default topic {}", defaultTopicName);
    }

    public void sendEvent(String message, String topicId) {
        final String topicName = topicPrefix + topicId;
        kafkaTemplate.send(topicName, message);
        log.info("Message sent to topic {}", topicName);
    }
}
