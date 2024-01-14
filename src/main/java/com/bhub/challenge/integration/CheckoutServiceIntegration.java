package com.bhub.challenge.integration;

import com.bhub.challenge.integration.dto.CheckoutInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

@Component
@Slf4j
public class CheckoutServiceIntegration {

    public CheckoutInfo getCheckoutOrderInfo(UUID userId, UUID checkoutOrderId) {
       var checkoutInfo = new CheckoutInfo(checkoutOrderId, userId, Collections.emptyList(), new BigDecimal(1));
        log.info("Checkout information retrieved from Checkout Service, checkoutInfo={}", checkoutInfo);
        return checkoutInfo;
    }
}
