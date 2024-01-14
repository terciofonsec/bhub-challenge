package com.bhub.challenge.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters.DateToLocalDateTimeConverter;
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
@EnableMongoAuditing
public class MongodbConfig {

  @Bean
  public MongoCustomConversions customConversions() {
    return new MongoCustomConversions(Arrays.asList(
        LocalDateTimeToDateConverter.INSTANCE,
        DateToLocalDateTimeConverter.INSTANCE
    ));
  }
}
