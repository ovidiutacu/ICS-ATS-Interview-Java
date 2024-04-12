package com.thales.interview.infra.repository;

import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import com.thales.interview.infra.entity.CalendarEntity;
import com.thales.interview.infra.mapper.CalendarModelEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CalendarRepositoryImpl implements CalendarRepository {

    private final CalendarJpaRepository jpaRepository;

    @Override
    public List<Calendar> findAll() {

        return jpaRepository.findAll().stream().map(CalendarModelEntityMapper::toModel).toList();
    }

    @Override
    public Optional<Calendar> findById(UUID id) throws IllegalArgumentException {

        if (id == null) {
            throw new IllegalArgumentException("Can't get calendar by null id from database");
        }
        return jpaRepository.findById(id).map(CalendarModelEntityMapper::toModel);
    }

    @Override
    public Optional<Calendar> findByName(String name) throws IllegalArgumentException {

        if (name == null) {
            throw new IllegalArgumentException("Can't get calendar by null name from database");
        }
        return jpaRepository.findByName(name).map(CalendarModelEntityMapper::toModel);
    }

    @Override
    public Calendar save(Calendar calendar) throws IllegalArgumentException {

        if (calendar == null) {
            throw new IllegalArgumentException("Can't save null calendar to database");
        }
        CalendarEntity toSaveEntity = CalendarModelEntityMapper.toEntity(calendar);
        CalendarEntity savedEntity = jpaRepository.save(toSaveEntity);
        return CalendarModelEntityMapper.toModel(savedEntity);
    }

    @Override
    public void delete(Calendar calendar) throws IllegalArgumentException {

        if (calendar == null) {
            throw new IllegalArgumentException("Can't delete null calendar from database");
        }
        jpaRepository.delete(CalendarModelEntityMapper.toEntity(calendar));
    }

    @Override
    public void deleteAll() {

        jpaRepository.deleteAll();
    }
}
