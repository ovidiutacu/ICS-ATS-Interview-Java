package com.thales.interview.application.controller;

import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;
import com.thales.interview.application.service.EventFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventFacade eventFacade;

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getEvents() {

        return ResponseEntity.ok(eventFacade.getEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable UUID id) {

        return ResponseEntity.ok(eventFacade.getEventById(id));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteEvents() {

        eventFacade.deleteEvents();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEventById(@PathVariable UUID id) {

        eventFacade.deleteEventById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<List<EventResponseDto>> createEvents(@RequestBody CreateEventsRequestDto createEventsRequestDto) {

        List<EventResponseDto> created = eventFacade.createEvents(createEventsRequestDto);
        return ResponseEntity.status(CREATED).body(created);
    }

    @PutMapping("/name")
    public ResponseEntity<EventResponseDto> updateEventName(@RequestBody UpdateEventNameRequestDto updateEventNameRequestDto) {

        EventResponseDto updatedEvent = eventFacade.updateEventName(updateEventNameRequestDto);
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/date")
    public ResponseEntity<EventResponseDto> updateEventDate(@RequestBody UpdateEventDateRequestDto updateEventNameRequestDto) {

        EventResponseDto updatedEvent = eventFacade.updateEventDate(updateEventNameRequestDto);
        return ResponseEntity.ok(updatedEvent);
    }
}
