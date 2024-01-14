package com.bhub.challenge.config;

import com.bhub.challenge.mapper.DeliveryNoteRequestToDTOConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setSkipNullEnabled(true);
    mapper.addConverter(new DeliveryNoteRequestToDTOConverter());
    return mapper;
  }

}
