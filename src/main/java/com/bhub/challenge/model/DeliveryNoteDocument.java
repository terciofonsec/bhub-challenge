package com.bhub.challenge.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "DeliveryNote")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryNoteDocument {

  @Id
  private String id;
  private String department;
  private UUID userId;
  private UUID checkoutOrderId;
  @With
  private DeliveryNoteStatus status = DeliveryNoteStatus.CREATED;
  @CreatedDate
  @Field("createdDate")
  private LocalDateTime createDate;
  @LastModifiedDate
  @Field("updateDate")
  private LocalDateTime updateDate;

  public enum DeliveryNoteStatus {
    CREATED, PROCESSING, FINISHED, ERROR
  }

}
