package com.thales.interview.application.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record UpdateEventDateRequestDto(
        UUID eventId,
        LocalDate newDate
) {
}
