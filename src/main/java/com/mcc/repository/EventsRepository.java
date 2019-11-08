package com.mcc.repository;

import org.springframework.data.repository.CrudRepository;

import com.mcc.domain.Event;

public interface EventsRepository extends CrudRepository<Event, Long> {

}
