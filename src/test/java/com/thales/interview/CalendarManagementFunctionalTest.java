package com.thales.interview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thales.interview.application.CalendarDtoMother;
import com.thales.interview.application.EventDtoMother;
import com.thales.interview.application.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalendarManagementFunctionalTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {

        testRestTemplate.delete("/calendars");
        testRestTemplate.delete("/events");
    }

    @Test
    void givenNoCalendarAvailable_whenGetCalendars_thenEmptyListIsReturned() {

        //when
        ResponseEntity<List> getCalendarsResponse = testRestTemplate.getForEntity("/calendars", List.class);
        //then
        assertEquals(HttpStatus.OK, getCalendarsResponse.getStatusCode());
        assertNotNull(getCalendarsResponse.getBody());
        assertTrue(getCalendarsResponse.getBody().isEmpty());
    }

    @Test
    void givenNotExistingId_whenGetCalendarById_thenNotFoundIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        //when
        ResponseEntity<ErrorResponseDto> getCalendarResponse = testRestTemplate.getForEntity(
                "/calendars/" + id,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.NOT_FOUND, getCalendarResponse.getStatusCode());
    }

    @Test
    void givenNotExistingName_whenGetCalendarByName_thenNotFoundIsReturned() {

        //given
        String name = UUID.randomUUID().toString();
        //when
        ResponseEntity<ErrorResponseDto> getCalendarResponse = testRestTemplate.getForEntity(
                "/calendars/name/" + name,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.NOT_FOUND, getCalendarResponse.getStatusCode());
    }

    @Test
    void whenDeleteCalendars_thenNoContentIsReturned() {

        //when
        ResponseEntity<?> deleteCalendarsResponse = testRestTemplate.exchange(
                "/calendars",
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarsResponse.getStatusCode());
    }

    @Test
    void givenNotExistingId_whenDeleteCalendarById_thenNoContentIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        //when
        ResponseEntity<?> deleteCalendarResponse = testRestTemplate.exchange(
                "/calendars/" + id,
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarResponse.getStatusCode());
    }

    @Test
    void givenNotExistingName_whenGetCalendarByName_thenNoContentIsReturned() {

        //given
        String name = UUID.randomUUID().toString();
        //when
        ResponseEntity<?> deleteCalendarResponse = testRestTemplate.exchange(
                "/calendars/name/" + name,
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarResponse.getStatusCode());
    }

    @Test
    void givenCreateCalendarRequestWithoutName_whenCreateCalendar_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CreateCalendarRequestDto.builder()
                .description("Dummy Description")
                .build();
        //when
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createCalendarResponse.getStatusCode());
    }

    @Test
    void givenValidCreateCalendarRequest_whenCreateCalendar_thenCreatedCalendarIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        //when
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //then
        assertEquals(HttpStatus.CREATED, createCalendarResponse.getStatusCode());
        assertNotNull(createCalendarResponse.getBody());
        assertNotNull(createCalendarResponse.getBody().id());
        assertEquals(createCalendarRequest.name(), createCalendarResponse.getBody().name());
        assertEquals(createCalendarRequest.description(), createCalendarResponse.getBody().description());
        assertTrue(createCalendarResponse.getBody().events().isEmpty());
    }

    @Test
    void givenCreateCalendarRequestWithAlreadyExistingName_whenCreateCalendar_thenCreatedCalendarIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //when
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //then
        assertEquals(HttpStatus.CONFLICT, createCalendarResponse.getStatusCode());
    }

    @Test
    void givenExistingId_whenDeleteCalendarById_thenNoContentIsReturnedAndCalendarIsDeleted() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //when
        ResponseEntity<?> deleteCalendarResponse = testRestTemplate.exchange(
                "/calendars/" + createCalendarResponse.getBody().id(),
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarResponse.getStatusCode());
        ResponseEntity<ErrorResponseDto> getCalendarResponse = testRestTemplate.getForEntity(
                "/calendars/" + createCalendarResponse.getBody().id(),
                ErrorResponseDto.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getCalendarResponse.getStatusCode());
    }

    @Test
    void givenExistingName_whenDeleteCalendarByName_thenNoContentIsReturnedAndCalendarIsDeleted() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        //when
        ResponseEntity<?> deleteCalendarResponse = testRestTemplate.exchange(
                "/calendars/name/" + createCalendarResponse.getBody().name(),
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarResponse.getStatusCode());
        ResponseEntity<ErrorResponseDto> getCalendarResponse = testRestTemplate.getForEntity(
                "/calendars/name/" + createCalendarResponse.getBody().name(),
                ErrorResponseDto.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getCalendarResponse.getStatusCode());
    }

    @Test
    void givenNoEventsAvailable_whenGetEvents_thenEmptyListIsReturned() {

        //when
        ResponseEntity<List> getEventsResponse = testRestTemplate.getForEntity("/events", List.class);
        //then
        assertEquals(HttpStatus.OK, getEventsResponse.getStatusCode());
        assertNotNull(getEventsResponse.getBody());
        assertTrue(getEventsResponse.getBody().isEmpty());
    }


    @Test
    void givenNotExistingId_whenGetEventById_thenNotFoundIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        //when
        ResponseEntity<ErrorResponseDto> getEventResponse = testRestTemplate.getForEntity("/events/" + id, ErrorResponseDto.class);
        //then
        assertEquals(HttpStatus.NOT_FOUND, getEventResponse.getStatusCode());
    }

    @Test
    void whenDeleteEvents_thenNoContentIsReturned() {

        //when
        ResponseEntity<?> deleteEventsResponse = testRestTemplate.exchange(
                "/events",
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteEventsResponse.getStatusCode());
    }

    @Test
    void givenNotExistingId_whenDeleteEventById_thenNoContentIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        //when
        ResponseEntity<?> deleteEventResponse = testRestTemplate.exchange(
                "/events/" + id,
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteEventResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutCalendarId_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto createEventsRequest = validRequest.toBuilder().calendarId(null).build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutExistingCalendar_whenCreateEvents_thenNotFoundIsReturned() {

        //given
        CreateEventsRequestDto createEventsRequest = EventDtoMother.completeCreateEventsRequestDto();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.NOT_FOUND, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutType_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto createEventsRequest = validRequest.toBuilder().type(null).build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutName_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto createEventsRequest = validRequest.toBuilder().name(null).build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutStartDate_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto request = validRequest.toBuilder().startDate(null).build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                request,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithoutEndDate_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto createEventsRequest = validRequest.toBuilder().endDate(null).build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenCreateEventsRequestWithStartDateHigherThanEndDate_whenCreateEvents_thenBadRequestIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        CreateEventsRequestDto createEventsRequest = validRequest.toBuilder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(1))
                .build();
        //when
        ResponseEntity<ErrorResponseDto> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                ErrorResponseDto.class
        );
        //then
        assertEquals(HttpStatus.BAD_REQUEST, createEventsResponse.getStatusCode());
    }

    @Test
    void givenValidCreateEventsRequest_whenCreateEvents_thenCreatedEventsAreReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto validRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        //when
        ResponseEntity<List> createEventsResponse = testRestTemplate.postForEntity(
                "/events",
                validRequest,
                List.class
        );
        //then
        assertEquals(HttpStatus.CREATED, createEventsResponse.getStatusCode());
        assertNotNull(createEventsResponse.getBody());
        assertEquals(DAYS.between(validRequest.startDate(), validRequest.endDate()) + 1, createEventsResponse.getBody().size());
        for (Object eventAsObject : createEventsResponse.getBody()) {
            EventResponseDto event = objectMapper.convertValue(eventAsObject, EventResponseDto.class);
            assertEquals(validRequest.type(), event.type());
            assertEquals(validRequest.name(), event.name());
            assertEquals(validRequest.description(), event.description());
            assertEquals(validRequest.calendarId(), event.calendarId());
        }
    }

    @Test
    void givenCalendarWithEvents_whenDeleteCalendarById_thenAssociatedEventsAreDeleted() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        ResponseEntity<CalendarResponseDto> createCalendarResponse = testRestTemplate.postForEntity(
                "/calendars",
                createCalendarRequest,
                CalendarResponseDto.class
        );
        CreateEventsRequestDto createEventsRequest = EventDtoMother.completeCreateEventsRequestDtoForCalendar(createCalendarResponse.getBody().id());
        testRestTemplate.postForEntity(
                "/events",
                createEventsRequest,
                List.class
        );
        ResponseEntity<List> getEventsResponse = testRestTemplate.getForEntity("/events", List.class);
        assertNotNull(getEventsResponse.getBody());
        assertFalse(getEventsResponse.getBody().isEmpty());
        //when
        ResponseEntity<?> deleteCalendarResponse = testRestTemplate.exchange(
                "/calendars/" + createCalendarResponse.getBody().id(),
                HttpMethod.DELETE,
                null,
                ResponseEntity.class
        );
        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteCalendarResponse.getStatusCode());
        getEventsResponse = testRestTemplate.getForEntity("/events", List.class);
        assertNotNull(getEventsResponse.getBody());
        assertTrue(getEventsResponse.getBody().isEmpty());
    }
}
