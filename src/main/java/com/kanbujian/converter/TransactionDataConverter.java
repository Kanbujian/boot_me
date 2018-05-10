package com.kanbujian.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanbujian.entity.TransactionData;
import com.kanbujian.util.JsonObjectMapper;

import javax.persistence.AttributeConverter;
import java.io.IOException;

public class TransactionDataConverter implements AttributeConverter<TransactionData, String> {
    private static final ObjectMapper objectMapper = JsonObjectMapper.getInstance();

    @Override
    public String convertToDatabaseColumn(TransactionData o) {
        try{
            return objectMapper.writeValueAsString(o);
        }catch (JsonProcessingException ex){
            return null;
        }
    }

    @Override
    public TransactionData convertToEntityAttribute(String s) {
        try{
            if (s == null){
                return null;
            }
            return objectMapper.readValue(s, TransactionData.class);
        }catch (IOException ex){
            return null;
        }
    }
}
