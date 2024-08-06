package com.amazon.ata.dynamodbscanandserialization.icecream.converter;

import com.amazon.ata.dynamodbscanandserialization.icecream.exception.SundaeSerializationException;
import com.amazon.ata.dynamodbscanandserialization.icecream.model.Sundae;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SundaeConverter implements DynamoDBTypeConverter<String, List<Sundae>> {
    private ObjectMapper mapper;

    public SundaeConverter() {
        mapper = new ObjectMapper();
    }

    @Override
    public String convert(List<Sundae> sundaeList) {
        if (sundaeList.isEmpty()) {
            return "";
        }

        try {
            return mapper.writeValueAsString(sundaeList);
        } catch (JsonProcessingException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }
    }

    @Override
    public List<Sundae> unconvert(String s) {
        if (s.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            return mapper.readValue(s, new TypeReference<List<Sundae>>(){});
        } catch (IOException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }
    }
}
