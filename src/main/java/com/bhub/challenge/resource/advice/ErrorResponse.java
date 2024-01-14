package com.bhub.challenge.resource.advice;

import java.util.List;

public record ErrorResponse(
        List<String> messages
) {
}
