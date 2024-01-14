package com.bhub.challenge.mapper;

import com.bhub.challenge.dto.DeliveryNoteDTO;
import com.bhub.challenge.model.DeliveryNoteDocument;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class DeliveryNoteDTOToDocumentConverter implements
    Converter<DeliveryNoteDTO, DeliveryNoteDocument> {


  @Override
  public DeliveryNoteDocument convert(
      MappingContext<DeliveryNoteDTO, DeliveryNoteDocument> mappingContext) {
    DeliveryNoteDTO source = mappingContext.getSource();
    DeliveryNoteDocument destination = mappingContext.getDestination();
    destination.setDepartment(source.getDepartment());
    destination.setCheckoutOrderId(source.getCheckoutOrderId());
    destination.setUserId(source.getCheckoutOrderId());
    return null;
  }
}
