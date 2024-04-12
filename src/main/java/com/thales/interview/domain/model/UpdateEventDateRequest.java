package com.thales.interview.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record UpdateEventDateRequest(
        UUID eventId,
        LocalDate newDate
) {
}
