package com.bhub.challenge.repository;

import com.bhub.challenge.model.DeliveryNoteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryNoteRepository extends MongoRepository<DeliveryNoteDocument, UUID> {
}
