package com.bhub.challenge.service;

import com.bhub.challenge.message.DeliveryNotePublisher;
import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.repository.DeliveryNoteRepository;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import com.bhub.challenge.service.exceptions.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeliveryNoteService {

  private final ModelMapper mapper;
  private final DeliveryNotePublisher publisher;
  private final DeliveryNoteRepository repository;


  // we are saving this event on db first order to prevent event lost, using a kinda outbox pattern we can guarantee the possibility to reprocess it if needed.
  public void createDeliveryNote(final DeliveryNoteRequest deliveryNoteRequest) {
    try {
      log.info("creating delivery note: {}", deliveryNoteRequest);
      checkForIdempotency(deliveryNoteRequest);
      DeliveryNoteDocument deliveryNoteDocument = mapper.map(deliveryNoteRequest,
          DeliveryNoteDocument.class);
      repository.save(deliveryNoteDocument);
      publisher.sendEvent(deliveryNoteDocument);
      log.info("Delivery note created and published, documentId: {}", deliveryNoteDocument.getId());
    } catch (Exception e) {
      log.error("Error on creating delivery note on database, deliveryNoteRequest={}",
          deliveryNoteRequest);
      throw e;
    }
  }

  private void checkForIdempotency(final DeliveryNoteRequest deliveryNoteRequest) {
    var result = repository.findByUserIdAndCheckoutOrderIdAndDepartment(
        deliveryNoteRequest.userId(),
        deliveryNoteRequest.checkoutOrderId(),
        deliveryNoteRequest.department());

    if (!result.isEmpty()) {
      throw new ResourceAlreadyExistsException(String.format(
          "The delivery note already created, userID=%s, checkoutOrderId=%s, department=%s",
          deliveryNoteRequest.userId(),
          deliveryNoteRequest.checkoutOrderId(),
          deliveryNoteRequest.department()));
    }
  }

}
