package com.kanbujian.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper {
    private static ObjectMapper instance = null;

    public static ObjectMapper getInstance(){
        if (instance == null) {
            instance = new ObjectMapper();
            instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        return instance;
    }

    private JsonObjectMapper(){
    }
}
