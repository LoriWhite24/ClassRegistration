package com.cognixia.jump.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Registration;
import com.cognixia.jump.repository.RegistrationRepository;

/**
 * The controller for Registration.
 * @author Lori White
 * @version v5 (08/10/2020)
 */
@RequestMapping("/api")
@RestController
public class RegistrationController {
	@Autowired
	RegistrationRepository service;
	/**
	 * Retrieves all the registry entries for a student.
	 * @author Lori White
	 * @param id the student id to search for
	 * @return List - the list of registry entries for this student
	 */
	@GetMapping("/registration/student/{id}")
	public List<Registration> getRegistryEntriesByStudent(@PathVariable long id) {
		return service.findByStudentId(id);
	}
	/**
	 * Retrieves all the registry entries for a course.
	 * @author Lori White
	 * @param id the course id to search for
	 * @return List - the list of registry entries for this course 
	 */
	@GetMapping("/registration/course/{id}")
	public List<Registration> getRegistryEntriesByCourse(@PathVariable long id) {
		return service.findByCourseId(id);
	}
	/**
	 * Withdraws or Re-Enrolls a student in a course.
	 * @author Lori White
	 * @param studentId the student id to search for
	 * @param courseId the course id to search for
	 * @param value the value to update the registry entry with
	 * @return ResponseEntity - a response of whether the registry entry was patched or not
	 */
	@PatchMapping("/update/registration/student/{studentId}/course/{courseId}/withdraw/{value}")
	public ResponseEntity<String> patchRegistryEntry(@PathVariable long studentId, @PathVariable long courseId, @PathVariable boolean value) {
		if(!service.existsByStudentIdandCourseId(studentId, courseId)) {
			return ResponseEntity.status(400).body("Registry Entry with student id = " + studentId + " and course id = " + courseId + " doesn't exist.");
		} else {
			Registration patched = service.findByStudentIdandCourseId(studentId, courseId);
			patched.setHasWithdrawn(value);
			service.save(patched);
			return ResponseEntity.status(200).body("Patched: " + patched);
		}
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
			return ResponseEntity.status(400).body("Registry Entry with id = " + registryEntry.getId() + " already exists.");
		} else {
			Registration created = service.save(registryEntry);
			return ResponseEntity.status(201).body("Created: " + created);
		}
	}
}
