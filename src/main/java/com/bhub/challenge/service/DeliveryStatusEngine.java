package com.bhub.challenge.service;

import com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DeliveryStatusEngine {

    private Map<DeliveryNoteStatus, DeliveryNoteStatus> statuses;

    public DeliveryStatusEngine(){
        this.statuses = Map.of(DeliveryNoteStatus.CREATED, DeliveryNoteStatus.PROCESSING, DeliveryNoteStatus.PROCESSING, DeliveryNoteStatus.FINISHED);
    }


    public DeliveryNoteStatus getNextStatus(DeliveryNoteStatus status){
        return this.statuses.get(status);
    }
}
