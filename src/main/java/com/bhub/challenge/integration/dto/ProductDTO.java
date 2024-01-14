package com.bhub.challenge.integration.dto;

import java.util.UUID;

public record ProductDTO(
        UUID productId,
        String productName,

        Integer qty
) {
}
