package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Registration;
import com.cognixia.jump.repository.RegistrationRepository;

@RequestMapping("/api")
@RestController
public class RegistrationController {
	@Autowired
	RegistrationRepository service;
	
	@GetMapping("/registration")
	public List<Registration> getAllRegistryEntries() {
		return service.findAll();
	}
	@PostMapping("/add/registration")
	public ResponseEntity<String> addRegistryEntry(@Valid @RequestBody Registration registryEntry) {
		if(service.existsById(registryEntry.getId())) {
			return ResponseEntity.status(400).body("Account with id = " + registryEntry.getId() + " already exists.");
		} else {
			registryEntry.setId(1L);
			Registration created = service.save(registryEntry);
			return ResponseEntity.status(201).body("Created: " + created);
		}
	}
}
