package com.bhub.challenge.integration.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CheckoutInfo(
        UUID checkoutOrderId,
        UUID userId,
        List<ProductDTO> products,
        BigDecimal totalAmount
) {
}
