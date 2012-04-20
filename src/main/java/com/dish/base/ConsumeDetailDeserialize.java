package com.dish.base;

import com.dish.domain.ConsumeDetail;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.KeyDeserializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-4-5
 * Time: 下午10:46
 * To change this template use File | Settings | File Templates.
 */
public class ConsumeDetailDeserialize extends JsonDeserializer<List<ConsumeDetail>> {
    @Override
    public List<ConsumeDetail> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ArrayList<ConsumeDetail> details = new ArrayList<ConsumeDetail>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDeserializationConfig(ctxt.getConfig());
        jp.setCodec(mapper);
        if (jp.hasCurrentToken()) {
            JsonNode jsonNodes = mapper.readTree(jp);
            if (jsonNodes != null) {
                return mapper.readValue(jsonNodes, new TypeReference<List<ConsumeDetail>>() {});
            }
        }
        return details;
    }
}
