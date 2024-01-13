package com.bhub.challenge.message.model;

import com.bhub.challenge.model.DeliveryNoteDocument;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Setter
@Getter
public class DeliveryNoteEvent extends ApplicationEvent {

    DeliveryNoteDocument deliveryNoteDocument;

    public DeliveryNoteEvent(Object source, DeliveryNoteDocument deliveryNoteDocument) {
        super(source);
        this.deliveryNoteDocument = deliveryNoteDocument;
    }

}
