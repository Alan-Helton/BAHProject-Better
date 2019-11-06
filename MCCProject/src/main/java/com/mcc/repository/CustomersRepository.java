package com.mcc.repository;

import org.springframework.data.repository.CrudRepository;

import com.mcc.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

	public Customer findByName(String name);
	
}