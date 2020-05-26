package com.tinder.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonConverterJackson<T> implements JsonConverter<T> {
    private static ObjectMapper mapper = new ObjectMapper();
    private static JsonConverterJackson jsonConverterJackson;

    private JsonConverterJackson() {
    }

    public static JsonConverterJackson getInstance() {
        if (jsonConverterJackson == null) {
            jsonConverterJackson = new JsonConverterJackson();
        }
        return jsonConverterJackson;
    }


    @Override
    public String toJson(T t) throws JsonProcessingException {
        return mapper.writeValueAsString(t);
    }

    @Override
    public T jsonParse(String json) throws JsonProcessingException {
        return mapper.readValue(json, new TypeReference<T>() {
        });
    }
}
