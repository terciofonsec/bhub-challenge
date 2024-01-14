package com.bhub.challenge.stub;

import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;

import java.time.LocalDateTime;
import java.util.UUID;

public class DeliveryNoteStub {

  public static DeliveryNoteRequest gimmeDeliveryNoteRequest() {
    return new DeliveryNoteRequest(UUID.randomUUID(), UUID.randomUUID(), "First department");
  }

  public static DeliveryNoteRequest gimmeDeliveryNoteRequest(UUID userID, UUID checkoutOrderId,
      String department) {
    return new DeliveryNoteRequest(userID, checkoutOrderId, department);
  }

  public static DeliveryNoteRequest gimmeDeliveryNoteRequestMissingRequiredFields() {
    return new DeliveryNoteRequest(null, null, "");
  }

  public static DeliveryNoteDocument gimmeDeliveryNoteDocumentCreated(DeliveryNoteRequest request,
      DeliveryNoteDocument.DeliveryNoteStatus status) {
    return new DeliveryNoteDocument(
        UUID.randomUUID().toString(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        request.department(),
        request.userId(),
        request.checkoutOrderId(),
        status);
  }

  public static DeliveryNoteDocument gimmeDeliveryNoteDocumentCreated(
      DeliveryNoteDocument.DeliveryNoteStatus status) {
    return new DeliveryNoteDocument(
        UUID.randomUUID().toString(),
        LocalDateTime.now(),
        LocalDateTime.now(),
        "department",
        UUID.randomUUID(),
        UUID.randomUUID(),
        status);
  }
}


