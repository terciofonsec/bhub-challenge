package com.bhub.challenge.resource.apiobjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeliveryNoteRequest{
        @NotNull private UUID userId;
        @NotNull private UUID checkoutOrderId;
        @NotBlank private String department;

}
