package com.kanbujian.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanbujian.entity.TransactionData;
import com.kanbujian.util.JsonObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class MapConverter implements AttributeConverter<Map<String, Object>, String> {
    private static final ObjectMapper objectMapper = JsonObjectMapper.getInstance();

    @Override
    public String convertToDatabaseColumn(Map map) {
        try{
            return objectMapper.writeValueAsString(map);
        }catch (JsonProcessingException ex){
            return null;
        }
    }

    @Override
    public Map convertToEntityAttribute(String s) {
        try{
            if (s == null){
                return null;
            }
            return objectMapper.readValue(s, Map.class);
        }catch (IOException ex){
            return null;
        }
    }
}
