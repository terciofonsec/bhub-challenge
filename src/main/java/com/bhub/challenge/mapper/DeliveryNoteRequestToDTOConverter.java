package com.bhub.challenge.mapper;

import com.bhub.challenge.dto.DeliveryNoteDTO;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class DeliveryNoteRequestToDTOConverter implements
    Converter<DeliveryNoteRequest, DeliveryNoteDTO> {


  @Override
  public DeliveryNoteDTO convert(
      MappingContext<DeliveryNoteRequest, DeliveryNoteDTO> mappingContext) {
    DeliveryNoteRequest source = mappingContext.getSource();
    DeliveryNoteDTO destination = mappingContext.getDestination();
    if(destination == null){
      destination = new DeliveryNoteDTO();
    }
    destination.setDepartment(source.getDepartment());
    destination.setCheckoutOrderId(source.getCheckoutOrderId());
    destination.setUserId(source.getUserId());
    return destination;
  }
}
