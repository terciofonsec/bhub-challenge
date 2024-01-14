package com.bhub.challenge.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryNoteDTO{
  private String id;
  private UUID userId;
  private UUID checkoutOrderId;
  private String department;

}
