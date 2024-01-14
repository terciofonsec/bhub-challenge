package com.bhub.challenge.service;

import com.bhub.challenge.dto.DeliveryNoteDTO;
import com.bhub.challenge.integration.CheckoutServiceIntegration;
import com.bhub.challenge.integration.DomainServerIntegration;
import com.bhub.challenge.integration.dto.CheckoutInfo;
import com.bhub.challenge.integration.dto.UserInfo;
import com.bhub.challenge.message.DeliveryNotePublisher;
import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.repository.DeliveryNoteRepository;
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
    private final DomainServerIntegration domainServerIntegration;
    private final CheckoutServiceIntegration checkoutServiceIntegration;
    private final DeliveryStatusEngine statusEngine;

  // we are saving this event on db first in order to prevent event loss, using a kinda outbox pattern we can guarantee the possibility to reprocess it if needed.
  public void createDeliveryNote(final DeliveryNoteDTO deliveryNoteRequest) {
    try {
      log.info("creating delivery note: {}", deliveryNoteRequest);
      checkForIdempotency(deliveryNoteRequest);
      DeliveryNoteDocument deliveryNoteDocument = mapper.map(deliveryNoteRequest,
          DeliveryNoteDocument.class);
      deliveryNoteDocument = repository.save(deliveryNoteDocument);
      publisher.sendEvent(deliveryNoteDocument);
      log.info("Delivery note created and published, documentId: {}", deliveryNoteDocument.getId());
    } catch (Exception e) {
      log.error("Error on creating delivery note on database, deliveryNoteRequest={}, ex-msg={}",
          deliveryNoteRequest, e.getMessage());
      throw e;
    }
  }

  private void checkForIdempotency(final DeliveryNoteDTO deliveryNoteRequest) {
    var result = repository.findByUserIdAndCheckoutOrderIdAndDepartment(
        deliveryNoteRequest.getUserId(),
        deliveryNoteRequest.getCheckoutOrderId(),
        deliveryNoteRequest.getDepartment());

    if (!result.isEmpty()) {
      throw new ResourceAlreadyExistsException(String.format(
          "The delivery note already created, userID=%s, checkoutOrderId=%s, department=%s",
          deliveryNoteRequest.getUserId(),
          deliveryNoteRequest.getCheckoutOrderId(),
          deliveryNoteRequest.getDepartment()));
    }
  }


    public void eventProcess(final DeliveryNoteDocument deliveryNoteDocument) {
        try {
            var deliveryNoteDocumentInInternalContext = changeDeliveryNoteStatus(deliveryNoteDocument); // Internal context means we are setting new state for the document, so we need to create a new object since we are using a final keyword in order to present side effects
            UserInfo userInfo = domainServerIntegration.getUserInfo(deliveryNoteDocumentInInternalContext.getUserId());
            CheckoutInfo checkoutInfo = checkoutServiceIntegration.getCheckoutOrderInfo(deliveryNoteDocumentInInternalContext.getUserId(), deliveryNoteDocumentInInternalContext.getCheckoutOrderId());
            log.info("Generating delivery note and sending it to targeted department, userInfo={}, checkoutDto={}", userInfo, checkoutInfo); //Abstracting another flows that could generate specific object to integrate with some other service to impress the note
            deliveryNoteDocumentInInternalContext = changeDeliveryNoteStatus(deliveryNoteDocumentInInternalContext);
            publisher.sendEvent(deliveryNoteDocumentInInternalContext);
        } catch (Exception ex) {
            log.error("Error on processing event, deliveryNote={}, ex-msg={}", deliveryNoteDocument, ex.getMessage());
            repository.save(deliveryNoteDocument.withStatus(DeliveryNoteDocument.DeliveryNoteStatus.ERROR));
        }

    }
    //Strategy pattern in order to help be more clear about the allowed status based on the current status
    private DeliveryNoteDocument changeDeliveryNoteStatus(DeliveryNoteDocument deliveryNoteDocument){
        return repository.save(deliveryNoteDocument.withStatus(statusEngine.getNextStatus(deliveryNoteDocument.getStatus())));
    }


}
