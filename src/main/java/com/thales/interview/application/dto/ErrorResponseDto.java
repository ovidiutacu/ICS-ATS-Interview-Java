package com.thales.interview.application.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(
        int status,
        String message
) {
}
