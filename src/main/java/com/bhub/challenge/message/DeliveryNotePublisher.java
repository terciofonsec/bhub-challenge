package com.bhub.challenge.message;

import com.bhub.challenge.message.model.DeliveryNoteEvent;
import com.bhub.challenge.model.DeliveryNoteDocument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class DeliveryNotePublisher {

    private final ApplicationEventPublisher publisher;

    public void sendEvent(DeliveryNoteDocument deliveryNoteDocument){
        try {
            DeliveryNoteEvent deliveryNoteEvent = new DeliveryNoteEvent(this, deliveryNoteDocument);
            publisher.publishEvent(deliveryNoteEvent);
        }catch (Exception ex){
            log.error("Error on publishing event, but it still saved on database, deliveryNoteDocument={}", deliveryNoteDocument);
        }
    }

}
