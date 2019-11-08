package com.mcc.api;

import java.net.URI;
import java.util.Optional;

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

import com.mcc.domain.Registration;
import com.mcc.repository.RegistrationsRepository;

@RestController
@RequestMapping("/registrations")
public class RegistrationAPI {
	
	@Autowired
	RegistrationsRepository repo;
	
	@GetMapping
	public Iterable<Registration> getAll(){
		return repo.findAll();		
	}
	
	@GetMapping("/{id}")
	public Optional<Registration> getRegistrationById(@PathVariable("id")long id){
		return repo.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> addRegistration(@RequestBody Registration newRegistration,
			UriComponentsBuilder uri){
		newRegistration = repo.save(newRegistration);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newRegistration.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putRegistration (@RequestBody Registration newRegistration,
			@PathVariable("Id") long registrationId){
		newRegistration= repo.save(newRegistration);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{Id}")
	public ResponseEntity<?> deleteRegistration (@PathVariable("Id") long registrationId){
		repo.deleteById(registrationId);
		return ResponseEntity.ok().build();
	}

}
