package com.bhub.challenge.resource;

import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import com.bhub.challenge.service.DeliveryNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/delivery-notes")
@RequiredArgsConstructor
@Slf4j
public class DeliveryNoteResource {

  private final DeliveryNoteService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createDeliveryNote(@Valid @RequestBody DeliveryNoteRequest deliveryNoteRequest) {
    log.info("Processing payment for checkoutOrder: {}", deliveryNoteRequest.checkoutOrderId());
    service.createDeliveryNote(deliveryNoteRequest);
  }

}