package ma.adria.adapter.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.adria.adapter.classification.EventClassification;

import java.util.Map;

/**
 * Functional interface for mapping event data to a JSON string.
 */
@FunctionalInterface
public interface EventMapper {
    /**
     * Maps event data to a JSON string based on the provided classification.
     *
     * @param row            Event data map.
     * @param classification Event classification.
     * @param objectMapper   ObjectMapper instance to serialize the event data.
     * @return JSON string representing the mapped event.
     */
    String map(final Map<String, Object> row, final EventClassification classification, final ObjectMapper objectMapper);
}
