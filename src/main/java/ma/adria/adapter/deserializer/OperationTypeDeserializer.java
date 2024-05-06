package ma.adria.adapter.deserializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ma.adria.adapter.dto.DebeziumEvent;

import java.io.IOException;

public class OperationTypeDeserializer extends JsonDeserializer<DebeziumEvent.OperationType> {

    @Override
    public DebeziumEvent.OperationType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DebeziumEvent.OperationType.fromCode(jsonParser.getValueAsString());
    }
}
