package com.bhub.challenge.resource;

import com.bhub.challenge.dto.DeliveryNoteDTO;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import com.bhub.challenge.service.DeliveryNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-notes")
@RequiredArgsConstructor
@Slf4j
public class DeliveryNoteResource {

  private final DeliveryNoteService service;
  private final ModelMapper mapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createDeliveryNote(@Valid @RequestBody DeliveryNoteRequest deliveryNoteRequest) {
    log.info("Processing payment for checkoutOrder: {}", deliveryNoteRequest.getCheckoutOrderId());
    service.createDeliveryNote(mapper.map(deliveryNoteRequest, DeliveryNoteDTO.class));
  }

}