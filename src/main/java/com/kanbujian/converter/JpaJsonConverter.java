package com.kanbujian.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class JpaJsonConverter implements AttributeConverter<Object, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Object o) {
        try{
            return objectMapper.writeValueAsString(o);
        }catch (JsonProcessingException ex){
            return null;
        }
    }

    @Override
    public Object convertToEntityAttribute(String s) {
        try{
            return objectMapper.readValue(s, Object.class);
        }catch (IOException ex){
            return null;
        }
    }
}
