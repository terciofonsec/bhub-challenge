package com.bhub.challenge.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "DeliveryNote")
@Getter
public class DeliveryNoteDocument {

  @Id
  private String id;
  private LocalDateTime createDate = LocalDateTime.now();
  private LocalDateTime updateDate = LocalDateTime.now();
  private String department;
  private UUID userId;
  private UUID checkoutOrderId;
  private DeliveryNoteStatus status = DeliveryNoteStatus.CREATED;


  public enum DeliveryNoteStatus {
    CREATED, PROCESSING, FINISHED, ERROR
  }

}
