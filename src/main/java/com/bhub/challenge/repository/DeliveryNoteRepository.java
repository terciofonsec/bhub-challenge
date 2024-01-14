package com.bhub.challenge.repository;

import com.bhub.challenge.model.DeliveryNoteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryNoteRepository extends MongoRepository<DeliveryNoteDocument, UUID> {

  @Query("{ 'userId' : ?0, 'checkoutOrderId' : ?1, 'department' : ?2 }")
  List<DeliveryNoteDocument> findByUserIdAndCheckoutOrderIdAndDepartment(UUID userId,
      UUID checkoutOrderId, String department);
}
