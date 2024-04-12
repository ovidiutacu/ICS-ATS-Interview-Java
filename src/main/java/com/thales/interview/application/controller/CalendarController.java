package com.thales.interview.application.controller;

import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.application.service.CalendarFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController {

    private final CalendarFacade calendarFacade;

    @GetMapping
    public ResponseEntity<List<CalendarResponseDto>> getCalendars() {

        return ResponseEntity.ok(calendarFacade.getCalendars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> getCalendarById(@PathVariable UUID id) {

        return ResponseEntity.ok(calendarFacade.getCalendarById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CalendarResponseDto> getCalendarByName(@PathVariable String name) {

        return ResponseEntity.ok(calendarFacade.getCalendarByName(name));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteCalendars() {

        calendarFacade.deleteCalendars();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCalendarById(@PathVariable UUID id) {

        calendarFacade.deleteCalendarById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Object> deleteCalendarByName(@PathVariable String name) {

        calendarFacade.deleteCalendarByName(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(@RequestBody CreateCalendarRequestDto createCalendarRequestDto) {

        CalendarResponseDto created = calendarFacade.createCalendar(createCalendarRequestDto);
        return ResponseEntity.status(CREATED).body(created);
    }
}
