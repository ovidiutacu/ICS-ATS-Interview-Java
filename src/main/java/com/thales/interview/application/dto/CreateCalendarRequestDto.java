package com.thales.interview.application.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record CreateCalendarRequestDto(
        String name,
        String description
) {
}
