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

import com.mcc.domain.Customer;
import com.mcc.domain.Event;
import com.mcc.repository.EventsRepository;

@RestController
@RequestMapping("/events")
public class EventAPI {
	
	@Autowired
	EventsRepository repo;
	
	@GetMapping
	public Iterable<Event> getAll(){
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Event> getEvent(@PathVariable("id")long id){
		return repo.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> addEvent (@RequestBody Event newEvent,
			UriComponentsBuilder uri){
		newEvent = repo.save(newEvent);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newEvent.getId()).toUri();
		ResponseEntity<?> response = ResponseEntity.created(location).build();
		return response;
	}
	
	@PutMapping("/{eventId}")
	public ResponseEntity<?> putEvent (@RequestBody Event newEvent,
			@PathVariable("eventId") long eventId){
		newEvent = repo.save(newEvent);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{eventId}")
	public ResponseEntity<?> deleteEvent (@PathVariable("eventId") long eventId){
		repo.deleteById(eventId);
		return ResponseEntity.ok().build();
	}
	

}
