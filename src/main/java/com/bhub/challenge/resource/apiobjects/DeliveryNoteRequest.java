package com.bhub.challenge.resource.apiobjects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DeliveryNoteRequest(
        @NotNull UUID id,
        @NotNull UUID userId,
        @NotNull UUID checkoutOrderId,
        @NotBlank String department

) {
}
