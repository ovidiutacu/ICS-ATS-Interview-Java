package com.thales.interview.domain.model;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record Calendar(
        UUID id,
        String name,
        String description
) {
}
