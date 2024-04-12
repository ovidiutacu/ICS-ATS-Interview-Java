package com.thales.interview.infra.repository;

import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.infra.entity.EventEntity;
import com.thales.interview.infra.mapper.EventModelEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final EventJpaRepository jpaRepository;

    @Override
    public List<Event> findAll() {

        return jpaRepository.findAll().stream().map(EventModelEntityMapper::toModel).toList();
    }

    @Override
    public Optional<Event> findById(UUID id) throws IllegalArgumentException {

        if (id == null) {
            throw new IllegalArgumentException("Can't get event by null id from database");
        }
        return jpaRepository.findById(id).map(EventModelEntityMapper::toModel);
    }

    @Override
    public List<Event> findByCalendarId(UUID calendarId) throws IllegalArgumentException {

        if (calendarId == null) {
            throw new IllegalArgumentException("Can't get event by null calendarId from database");
        }
        return jpaRepository.findByCalendarId(calendarId).stream()
                .map(EventModelEntityMapper::toModel)
                .toList();
    }

    @Override
    public Event save(Event event) throws IllegalArgumentException {

        if (event == null) {
            throw new IllegalArgumentException("Can't save null event to database");
        }
        EventEntity toSaveEntity = EventModelEntityMapper.toEntity(event);
        EventEntity savedEntity = jpaRepository.save(toSaveEntity);
        return EventModelEntityMapper.toModel(savedEntity);
    }

    @Override
    public void delete(Event event) throws IllegalArgumentException {

        if (event == null) {
            throw new IllegalArgumentException("Can't delete null event from database");
        }
        jpaRepository.delete(EventModelEntityMapper.toEntity(event));
    }

    @Override
    public void deleteAll() {

        jpaRepository.deleteAll();
    }
}
