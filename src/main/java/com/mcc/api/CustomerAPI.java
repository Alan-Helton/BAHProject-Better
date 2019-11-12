package com.mcc.api;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.mcc.domain.Customer;
import com.mcc.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerAPI {
	
	@Autowired
	CustomersRepository repo;
	
	@GetMapping
	public Iterable<Customer> getAll(){
		return repo.findAll();		
	}
	
	@GetMapping("/id/{customerId}")
	public Optional<Customer> getCustomerById(@PathVariable("customerId")long id){
		return repo.findById(id);
	}
	
	@GetMapping("/{customerName}")
	public Optional<Customer> getCustomerByName(@PathVariable("customerName")String name){
		return repo.findByName(name);
		
	}
	
	@PostMapping
	public ResponseEntity<?> addCustomer (@RequestBody Customer newCustomer,
			UriComponentsBuilder uri){
		if (newCustomer.getId() != 0 
				|| newCustomer.getName() == null 
				||newCustomer.getEmail() == null) {
		
			return ResponseEntity.badRequest().build();
		}
	newCustomer = repo.save(newCustomer);
	URI location = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(newCustomer.getId()).toUri();
	ResponseEntity<?> response = ResponseEntity.created(location).build();
	return response;
	}
	
	@PutMapping("/{customerId}")
	public ResponseEntity<?> putCustomer (@RequestBody Customer newCustomer,
			@PathVariable("customerId") long customerId){
		if (newCustomer.getId() != customerId
				|| newCustomer.getName()==null
				|| newCustomer.getEmail()==null) {
			return ResponseEntity.badRequest().build();
		}
		newCustomer = repo.save(newCustomer);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/id/{customerId}")
	public ResponseEntity<?> deleteCustomer (@PathVariable("customerId") long customerId){
		repo.deleteById(customerId);
		return ResponseEntity.ok().build();
	}
	
	@Transactional
	@DeleteMapping("/{customerName}")
	public ResponseEntity<?> deleteByName (@PathVariable("customerName") String name) {
		long ret = repo.deleteByName(name);
		if (ret < 1) {
			return ResponseEntity.badRequest().build();			
		}
		else {
			return ResponseEntity.ok().build();
		}
		
	}
}
