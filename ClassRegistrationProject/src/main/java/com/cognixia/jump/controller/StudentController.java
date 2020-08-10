package com.cognixia.jump.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Student;
import com.cognixia.jump.repository.StudentRepository;

/**
 * The controller for Students.
 * @author Tara Kelly
 * @version v1 (08/08/2020)
 */
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository service;
	/**
	 * Retrieves a student pertaining to the id.
	 * @author Tara Kelly
	 * @param id the student id to search for
	 * @return Student - the student pertaining to the id
	 */
	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable long id) {

		Optional<Student> student = service.findById(id);

		if (student.isPresent()) {
			return student.get();
		}

		return new Student();
	}
	
//	@PutMapping("/update/student")
//	
	/**
	 * Creates a student.
	 * @author Tara Kelly
	 * @param newStudent the student to add to the database
	 * @return ResponseEntity - whether the student was added or not
	 */
	@PostMapping("/add/student")
	public ResponseEntity<String> addStudent(@Valid @RequestBody Student newStudent) {
		if(service.existsById(newStudent.getId())) {
			return ResponseEntity.status(400).body("Account with id = " + newStudent.getId() + " already exists.");
		} else {
			Student created = service.save(newStudent);
			return ResponseEntity.status(201).body("Created: " + created);
		}
	}

}
