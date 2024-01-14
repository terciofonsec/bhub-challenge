package com.bhub.challenge.service;

import com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeliveryStatusEngineTest {

  private DeliveryStatusEngine statusEngine = new DeliveryStatusEngine();

  @Test
  public void givenAStatus_whenCurrentStatusIsCreated_thenShouldReturnNextStatusProcessing(){

    DeliveryNoteStatus currentStatus = DeliveryNoteStatus.CREATED;
    DeliveryNoteStatus expectedStatus = DeliveryNoteStatus.PROCESSING;
    DeliveryNoteStatus calculatedStatus = statusEngine.getNextStatus(currentStatus);

    Assertions.assertEquals(expectedStatus, calculatedStatus);
  }
  @Test
  public void givenAStatus_whenCurrentStatusIsProcessing_thenShouldReturnNextStatusFinished(){

    DeliveryNoteStatus currentStatus = DeliveryNoteStatus.PROCESSING;
    DeliveryNoteStatus expectedStatus = DeliveryNoteStatus.FINISHED;
    DeliveryNoteStatus calculatedStatus = statusEngine.getNextStatus(currentStatus);

    Assertions.assertEquals(expectedStatus, calculatedStatus);
  }
}
