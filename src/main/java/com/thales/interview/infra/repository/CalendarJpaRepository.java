package com.thales.interview.infra.repository;

import com.thales.interview.infra.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CalendarJpaRepository extends JpaRepository<CalendarEntity, UUID> {

    @Override
    List<CalendarEntity> findAll();

    @Override
    Optional<CalendarEntity> findById(UUID id);

    Optional<CalendarEntity> findByName(String name);
}
