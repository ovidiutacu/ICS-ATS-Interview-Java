package com.thales.interview.domain.repository;

import com.thales.interview.domain.model.Calendar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalendarRepository {

    List<Calendar> findAll();

    Optional<Calendar> findById(UUID id) throws IllegalArgumentException;

    Optional<Calendar> findByName(String name) throws IllegalArgumentException;

    Calendar save(Calendar calendar) throws IllegalArgumentException;

    void delete(Calendar calendar) throws IllegalArgumentException;

    void deleteAll();
}
