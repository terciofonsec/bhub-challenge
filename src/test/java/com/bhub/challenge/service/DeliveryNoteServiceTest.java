package com.bhub.challenge.service;


import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bhub.challenge.dto.DeliveryNoteDTO;
import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus;
import com.bhub.challenge.repository.DeliveryNoteRepository;
import com.bhub.challenge.service.exceptions.ResourceAlreadyExistsException;
import com.bhub.challenge.stub.DeliveryNoteStub;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
public class DeliveryNoteServiceTest {

  @Autowired
  DeliveryNoteService service;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private DeliveryNoteRepository repository;

  @Test
  @DirtiesContext
  public void givenDeliveryNote_whenThereIsAlreadyADeliveryNoteCreated_thenShouldThrowResourceAlreadyExistsException() {
    //Given

    DeliveryNoteDTO request = DeliveryNoteStub.gimmeDeliveryNoteDTO();
    DeliveryNoteDocument deliveryNoteDocument = DeliveryNoteStub.gimmeDeliveryNoteDocumentCreated(
        request, DeliveryNoteStatus.CREATED);
    mongoTemplate.insert(deliveryNoteDocument, "DeliveryNote");
    assertThrows(ResourceAlreadyExistsException.class, () -> service.createDeliveryNote(request));

  }

  @Test
  @DirtiesContext
  public void givenDeliveryNote_whenCreateANewOne_thenShouldCreateAndPublishAnEvent() {
    //Given
    DeliveryNoteDTO request = DeliveryNoteStub.gimmeDeliveryNoteDTO();

    //When
    service.createDeliveryNote(request);

    //Then
    List<DeliveryNoteDocument> result = repository.findByUserIdAndCheckoutOrderIdAndDepartment(
        request.getUserId(), request.getCheckoutOrderId(), request.getDepartment());

    Assertions.assertEquals(1, result.size());
    DeliveryNoteDocument document = result.get(0);
    Assertions.assertEquals(request.getCheckoutOrderId(), document.getCheckoutOrderId());
    Assertions.assertEquals(request.getUserId(), document.getUserId());
    Assertions.assertEquals(request.getDepartment(), document.getDepartment());
    Assertions.assertNotNull(document.getUpdateDate());
    Assertions.assertNotNull(document.getCreateDate());

  }


}
