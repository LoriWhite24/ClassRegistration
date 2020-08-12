package com.cognixia.jump.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.InvalidLoginException;
import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Student;
import com.cognixia.jump.repository.StudentRepository;

/**
 * The controller for Students.
 * @author Lori White and Tara Kelly
 * @version v4 (08/12/2020)
 */
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository service;
	/**
	 * Retrieves a student pertaining to the id.
	 * @author Lori White and Tara Kelly
	 * @param id the student id to search for
	 * @return Student - the student pertaining to the id
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing student in the database
	 */
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable long id) throws ResourceNotFoundException {

		Optional<Student> student = service.findById(id);

		if (!student.isPresent()) {
			throw new ResourceNotFoundException("Student with id = " + id + " does not exist.");
		}

		return student.get();
	}
	/**
	 * Checks if the student entered the correct values to login.
	 * @author Lori White
	 * @param email the student's email to search for
	 * @param password the student's password to compare
	 * @return boolean - whether the login is valid or not
	 * @throws InvalidLoginException is thrown when the password does not match the existing student's password in the database
	 * @throws ResourceNotFoundException is thrown when the email does not match an existing student in the database
	 */
	@GetMapping("/students/login/username/{email}/password/{password}")
	public Student validLogin(@PathVariable String email, @PathVariable String password) throws InvalidLoginException, ResourceNotFoundException {
		if(!service.existsByEmail(email)) {
			throw new ResourceNotFoundException("Email is Invalid!");
		} 
		
		Student student = service.findByEmail(email);
		if(!student.getPassword().equals(password)) {
			throw new InvalidLoginException();
		} 
		return student;
	}
//	@PutMapping("/update/student")
//	
	/**
	 * Adds a student to DB.
	 * @author Lori White and Tara Kelly
	 * @param newStudent the student to add to the database
	 * @return ResponseEntity - whether the student was added or not
	 * @throws ResourceAlreadyExistsException is thrown when the id does match an existing student in the database
	 */
	@PostMapping("/add/student")
	public ResponseEntity<Student> addStudent(@Valid @RequestBody Student newStudent) throws ResourceAlreadyExistsException {
		if(service.existsById(newStudent.getId())) {
			throw new ResourceAlreadyExistsException("Student with id = " + newStudent.getId() + " already exists.");
		}
		
		Student created = service.save(newStudent);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

}
