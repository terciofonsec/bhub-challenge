package com.bhub.challenge.service;


import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bhub.challenge.message.DeliveryNotePublisher;
import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import com.bhub.challenge.service.exceptions.ResourceAlreadyExistsException;
import com.bhub.challenge.stub.DeliveryNoteStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
public class DeliveryNoteServiceTest {

  @MockBean
  DeliveryNotePublisher publisher;
  @Autowired
  DeliveryNoteService service;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Test
  @DirtiesContext
  public void givenDeliveryNote_whenThereIsAlreadyADeliveryNoteCreated_thenShouldThrowResourceAlreadyExistsException() {
    //Given

    DeliveryNoteRequest request = DeliveryNoteStub.gimmeDeliveryNoteRequest();
    DeliveryNoteDocument deliveryNoteDocument = DeliveryNoteStub.gimmeDeliveryNoteDocumentCreated(
        request, DeliveryNoteStatus.CREATED);
    mongoTemplate.insert(deliveryNoteDocument, "DeliveryNote");
    assertThrows(ResourceAlreadyExistsException.class, () -> service.createDeliveryNote(request));

  }


}
