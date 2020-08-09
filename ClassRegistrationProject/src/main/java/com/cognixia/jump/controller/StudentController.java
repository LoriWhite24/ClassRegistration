package com.cognixia.jump.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}