package com.bhub.challenge.message;

import com.bhub.challenge.message.model.DeliveryNoteEvent;
import com.bhub.challenge.service.DeliveryNoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class DeliveryNoteConsumer {

    private final DeliveryNoteService service;

//    @Async // This will prevent the system block the consumer while it is consuming some event and comes a new one over
    @EventListener(condition = "#event.deliveryNoteDocument.status == T(com.bhub.challenge.model.DeliveryNoteDocument.DeliveryNoteStatus).CREATED")
    // We are throwing some different events that can be consumed by other event listener, the idea of this consumer is just for created objects
    public void consumeDeliveryNoteCreatedEvent(DeliveryNoteEvent event) {
        log.info("Consuming delivery note event: {}", event);
        service.eventProcess(event.getDeliveryNoteDocument());
    }
}
