package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidHasWithdrawnUpdateException;
import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Registration;
import com.cognixia.jump.model.RegistrationHasWithdrawnOnly;
import com.cognixia.jump.repository.RegistrationRepository;

/**
 * The controller for Registration.
 * @author Lori White
 * @version v6 (08/13/2020)
 */
@RequestMapping("/api")
@RestController
public class RegistrationController {
	@Autowired
	RegistrationRepository service;
	/**
	 * Retrieves all the registry entries for a student.
	 * @author Lori White
	 * @param studentId the student id to search for
	 * @return List - the list of registry entries for this student
	 * @throws ResourceNotFoundException is thrown when the student id does not match any existing registry entries with that specified student id in the database
	 */
	@GetMapping("/registration/student/{studentId}")
	public List<Registration> getRegistryEntriesByStudent(@PathVariable long studentId) throws ResourceNotFoundException {
		if(!service.existsByStudentId(studentId)) {
			throw new ResourceNotFoundException("Registry entries with student id = " + studentId + " does not exist.");
		}
		return service.findByStudentId(studentId);
	}
	/**
	 * Retrieves all the registry entries for a course.
	 * @author Lori White
	 * @param courseId the course id to search for
	 * @return List - the list of registry entries for this course 
	 * @throws ResourceNotFoundException is thrown when the course id does not match any existing registry entries with that specified course id in the database
	 */
	@GetMapping("/registration/course/{courseId}")
	public List<Registration> getRegistryEntriesByCourse(@PathVariable long courseId) throws ResourceNotFoundException {
		if(!service.existsByCourseId(courseId)) {
			throw new ResourceNotFoundException("Registry entries with course id = " + courseId + " does not exist.");
		}
		return service.findByCourseId(courseId);
	}
	
	// adding these methods is to test -TK
	
	
	@PutMapping("/update/registration")
	public @ResponseBody String updateRegistration(@RequestBody Registration updateRegistration) {
		
		// check if restaurant exists, if so, then update	
		Optional<Registration> found = service.findById(updateRegistration.getId());
		
		if(found.isPresent()) {
			service.save(updateRegistration);
			return "Saved: " + updateRegistration.toString();
		}else {
			return "Could not update registration, the id = " + updateRegistration.getId() + " doesn't exist";
		}
	}
	
	/**
	 * Withdraws or Re-Enrolls a student in a course.
	 * @author Lori White
	 * @param partialRegistryEntry the registry entry value to update the registry entry with
	 * @return ResponseEntity - a response of whether the registry entry was patched or not 
	 * @throws ResourceNotFoundException is thrown when the student id and course id does not match any existing registry entries with that specified student id and course id in the database
	 * @throws InvalidHasWithdrawnUpdateException is thrown when the has withdrawn value matches the existing registry entrie's has withdrawn value in the database
	 */
	@PatchMapping("/update/registration/has_withdrawn")
	public ResponseEntity<Registration> patchRegistryEntry(@RequestBody RegistrationHasWithdrawnOnly partialRegistryEntry) throws ResourceNotFoundException, InvalidHasWithdrawnUpdateException {
		if(!service.existsByStudentIdandCourseId(partialRegistryEntry.getStudentId(), partialRegistryEntry.getCourseId())) {
			throw new ResourceNotFoundException("Registry Entry with student id = " + partialRegistryEntry.getStudentId() + " and course id = " + partialRegistryEntry.getCourseId() + " doesn't exist.");
		} 
		
		Registration patched = service.findByStudentIdandCourseId(partialRegistryEntry.getStudentId(), partialRegistryEntry.getCourseId());
		if(partialRegistryEntry.getHasWithdrawn().equals(patched.getHasWithdrawn())) {
			throw new InvalidHasWithdrawnUpdateException(patched.getHasWithdrawn());
		}
		patched.setHasWithdrawn(partialRegistryEntry.getHasWithdrawn());
		service.save(patched);
		return new ResponseEntity<>(patched, HttpStatus.ACCEPTED);
	}
	/**
	 * Adds a registry entry to the database.
	 * @author Lori White
	 * @param registryEntry a new registry entry to add  
	 * @return ResponseEntity - a response of whether the registry entry was added or not
	 * @throws ResourceAlreadyExistsException is thrown when the id does match an existing registry entry in the database
	 */
	@PostMapping("/add/registration")
	public ResponseEntity<Registration> addRegistryEntry(@Valid @RequestBody Registration registryEntry) throws ResourceAlreadyExistsException {
		if(service.existsById(registryEntry.getId())) {
			throw new ResourceAlreadyExistsException("Registry entry with id = " + registryEntry.getId() + " already exists.");
		} 
		if(service.existsByStudentIdandCourseId(registryEntry.getStudentId(), registryEntry.getCourseId())) {
			throw new ResourceAlreadyExistsException("Registry entry with student id = " + registryEntry.getStudentId() + " and with course id = " + registryEntry.getCourseId() + " already exists.");
		}
		Registration created = service.save(registryEntry);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
}
