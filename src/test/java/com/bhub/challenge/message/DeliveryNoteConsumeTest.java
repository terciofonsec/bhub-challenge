package com.bhub.challenge.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus;
import com.bhub.challenge.stub.DeliveryNoteStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DeliveryNoteConsumeTest {

  @Autowired
  private DeliveryNotePublisher publisher;

  @MockBean
  private DeliveryNoteConsumer consumer;

  @Test
  public void givenADeliveryNoteEvent_whenDeliveryHasCreatedStatus_thenShouldConsumeEvent() {

    DeliveryNoteDocument document = DeliveryNoteStub.gimmeDeliveryNoteDocumentCreated(
        DeliveryNoteStatus.CREATED);

    publisher.sendEvent(document);

    verify(consumer, Mockito.times(1)).consumeDeliveryNoteCreatedEvent(any());

  }

  @Test
  public void givenADeliveryNoteEvent_whenDeliveryHasProcessingStatus_thenShouldNotConsumeEvent() {

    DeliveryNoteDocument document = DeliveryNoteStub.gimmeDeliveryNoteDocumentCreated(
        DeliveryNoteStatus.PROCESSING);

    publisher.sendEvent(document);

    verify(consumer, Mockito.times(0)).consumeDeliveryNoteCreatedEvent(any());

  }

  @Test
  public void givenADeliveryNoteEvent_whenDeliveryHasFinishedStatus_thenShouldNotConsumeEvent() {

    DeliveryNoteDocument document = DeliveryNoteStub.gimmeDeliveryNoteDocumentCreated(
        DeliveryNoteStatus.FINISHED);

    publisher.sendEvent(document);

    verify(consumer, Mockito.times(0)).consumeDeliveryNoteCreatedEvent(any());

  }

}
