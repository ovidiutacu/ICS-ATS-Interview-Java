package com.thales.interview.application.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UpdateEventNameRequestDto(
        UUID eventId,
        String newName
) {
}
