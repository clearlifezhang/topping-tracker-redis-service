package com.clearlife.toppingtrackerredisservice.config;

import com.clearlife.toppingtrackerredisservice.entity.Topping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class ToppingRedisSerializer implements RedisSerializer<Topping> {
    private final ObjectMapper objectMapper;

    public ToppingRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(Topping topping) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(topping);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing Topping", e);
        }
    }

    @Override
    public Topping deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, Topping.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing Topping", e);
        }
    }
}
