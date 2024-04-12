package com.thales.interview.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UpdateEventNameRequest(
        UUID eventId,
        String newName
) {
}
