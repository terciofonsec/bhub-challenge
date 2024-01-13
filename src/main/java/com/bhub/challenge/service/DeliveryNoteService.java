package com.bhub.challenge.service;

import com.bhub.challenge.model.DeliveryNoteDocument;
import com.bhub.challenge.repository.DeliveryNoteRepository;
import com.bhub.challenge.resource.apiobjects.DeliveryNoteRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeliveryNoteService {

    private final ModelMapper mapper;
    private final DeliveryNoteRepository repository;


    // we are saving this event on db first order to prevent event lost, using a kinda outbox pattern we can guarantee the possibility to reprocess it if needed.
    public void createDeliveryNote(final DeliveryNoteRequest deliveryNoteRequest) {
        try {
            log.info("creating delivery note: {}", deliveryNoteRequest);
            DeliveryNoteDocument deliveryNoteDocument = mapper.map(deliveryNoteRequest, DeliveryNoteDocument.class);
            repository.save(deliveryNoteDocument);
            log.info("Delivery note created, documentId: {}", deliveryNoteDocument.getId());
        }catch (Exception e){
            log.error("Error on creating delivery note on database, deliveryNoteRequest={}", deliveryNoteRequest);
        }
    }
}
