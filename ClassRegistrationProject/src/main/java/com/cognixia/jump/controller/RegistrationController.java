package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
