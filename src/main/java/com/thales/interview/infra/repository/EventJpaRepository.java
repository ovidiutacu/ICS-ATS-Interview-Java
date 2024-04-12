package com.thales.interview.infra.repository;

import com.thales.interview.infra.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventJpaRepository extends JpaRepository<EventEntity, UUID> {

    @Override
    List<EventEntity> findAll();

    @Override
    Optional<EventEntity> findById(UUID id);

    List<EventEntity> findByCalendarId(UUID calendarId);
}
