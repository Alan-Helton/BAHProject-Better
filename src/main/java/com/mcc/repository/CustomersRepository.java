package com.mcc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mcc.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

	public Optional <Customer> findByName(String name);
	
	public long deleteByName(String name);
	
}