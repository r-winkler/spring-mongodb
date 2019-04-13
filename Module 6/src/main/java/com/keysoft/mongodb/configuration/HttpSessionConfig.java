package com.keysoft.mongodb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.mongo.JacksonMongoSessionConverter;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@EnableMongoHttpSession
public class HttpSessionConfig {

    @Bean
    JacksonMongoSessionConverter mongoSessionConverter() {
        return new JacksonMongoSessionConverter();
    }

}
