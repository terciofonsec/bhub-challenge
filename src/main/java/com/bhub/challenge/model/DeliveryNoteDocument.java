package com.bhub.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Document("DeliveryNote")
public class DeliveryNoteDocument {

    @Id
    private String id;
    private LocalDateTime createDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();
    private String department;
    private UUID userId;
    private UUID checkoutOrderId;
    private DeliveryNoteStatus status = DeliveryNoteStatus.CREATED;


    public enum DeliveryNoteStatus{
        CREATED, PROCESSING, FINISHED, ERROR
    }

}
