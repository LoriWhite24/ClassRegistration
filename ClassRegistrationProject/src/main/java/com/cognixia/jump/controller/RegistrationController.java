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

/**
 * The controller for Registration.
 * @author Lori White
 * @version v2 (08/08/2020)
 */
@RequestMapping("/api")
@RestController
public class RegistrationController {
	@Autowired
	RegistrationRepository service;
	/**
	 * Retrieves all the registry entries.
	 * @author Lori White
	 * @return List - the list of registry entries
	 */
	@GetMapping("/registration")
	public List<Registration> getAllRegistryEntries() {
		return service.findAll();
	}
	/**
	 * Adds a registry entry to the database.
	 * @author Lori White
	 * @param registryEntry a new registry entry to add  
	 * @return ResponseEntity - a response of whether the registry entry was added or not
	 */
	@PostMapping("/add/registration")
	public ResponseEntity<String> addRegistryEntry(@Valid @RequestBody Registration registryEntry) {
		if(service.existsById(registryEntry.getId())) {
			return ResponseEntity.status(400).body("Account with id = " + registryEntry.getId() + " already exists.");
		} else {
			Registration created = service.save(registryEntry);
			return ResponseEntity.status(201).body("Created: " + created);
		}
	}
}
