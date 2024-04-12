package com.thales.interview.application.service;

import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;
import com.thales.interview.application.mapper.EventModelDtoMapper;
import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;
import com.thales.interview.domain.service.CreateEventService;
import com.thales.interview.domain.service.DeleteEventService;
import com.thales.interview.domain.service.ReadEventService;
import com.thales.interview.domain.service.UpdateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EventFacade {

    private final CreateEventService createEventService;
    private final ReadEventService readEventService;
    private final UpdateEventService updateEventService;
    private final DeleteEventService deleteEventService;

    public List<EventResponseDto> getEvents() {

        return readEventService.getEvents().stream()
                .map(EventModelDtoMapper::toDto)
                .toList();
    }

    public EventResponseDto getEventById(UUID id) {

        return EventModelDtoMapper.toDto(readEventService.getEventById(id));
    }

    public void deleteEvents() {

        deleteEventService.deleteAllEvents();
    }

    public void deleteEventById(UUID id) {

        deleteEventService.deleteEventById(id);
    }

    public List<EventResponseDto> createEvents(CreateEventsRequestDto createEventsRequestDto) {

        CreateEventsRequest createEventsRequest = EventModelDtoMapper.toModel(createEventsRequestDto);
        List<Event> created = createEventService.createEvents(createEventsRequest);
        return created.stream()
                .map(EventModelDtoMapper::toDto)
                .toList();
    }

    public EventResponseDto updateEventDate(UpdateEventDateRequestDto updateEventDateRequestDto) {

        UpdateEventDateRequest request = EventModelDtoMapper.toUpdateEventDateRequestModel(updateEventDateRequestDto);
        Event updated = updateEventService.updateEventDate(request);
        return EventModelDtoMapper.toDto(updated);
    }

    public EventResponseDto updateEventName(UpdateEventNameRequestDto updateEventNameRequestDto) {

        UpdateEventNameRequest request = EventModelDtoMapper.toUpdateEventNameRequestModel(updateEventNameRequestDto);
        Event updated = updateEventService.updateEventName(request);
        return EventModelDtoMapper.toDto(updated);
    }
}
