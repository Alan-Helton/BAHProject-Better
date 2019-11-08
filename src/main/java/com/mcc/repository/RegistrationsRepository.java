package com.mcc.repository;

import org.springframework.data.repository.CrudRepository;

import com.mcc.domain.Registration;

public interface RegistrationsRepository extends CrudRepository<Registration, Long>{

}
