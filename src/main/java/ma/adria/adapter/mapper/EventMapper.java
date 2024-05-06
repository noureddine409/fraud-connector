package ma.adria.adapter.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.adria.adapter.classification.EventClassification;

import java.util.Map;

@FunctionalInterface
public interface EventMapper {
    String map(Map<String, Object> row, EventClassification classification, ObjectMapper objectMapper);
}
