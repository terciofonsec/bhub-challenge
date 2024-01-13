package com.bhub.challenge.resource.advice;

public record ErrorResponse(
        String message,
        String code
) {
}
