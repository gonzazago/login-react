package com.react.loginreact.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfigure {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
