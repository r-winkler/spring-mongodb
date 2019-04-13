package com.keysoft.mongodb.configuration;


import com.keysoft.mongodb.converters.ZonedDateTimeReadConverter;
import com.keysoft.mongodb.converters.ZonedDateTimeWriteConverter;
import com.keysoft.mongodb.listeners.ReleaseCascadeConvertMongoEventListener;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;


import java.net.UnknownHostException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.ofInstant;

@Configuration
public class AppConfig extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    @Override
    protected String getDatabaseName() {
        return "trackzilla";
    }

    @Bean
    public MongoTemplate getMongoTemplate() throws UnknownHostException {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory()), new MongoMappingContext());
        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory(), converter);
    }

    public @Bean
    MongoDbFactory mongoDbFactory()  {
        return new SimpleMongoDbFactory(new MongoClient(), "trackzilla");
    }

     @Bean
     public ReleaseCascadeConvertMongoEventListener releaseCascadingMongoEventListener() {
        return new ReleaseCascadeConvertMongoEventListener();
     }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}