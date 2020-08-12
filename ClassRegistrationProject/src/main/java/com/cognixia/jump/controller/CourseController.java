package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceAlreadyExistsException;
import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Course;
import com.cognixia.jump.repository.CourseRepository;

/**
 * The controller for the Courses. 
 * @author Lori White and Tara Kelly
 * @version v4 (08/12/2020)
 */
@RequestMapping("/api")
@RestController
public class CourseController {

	@Autowired
	CourseRepository service;
	/**
	 * Retrieves all the courses in the database.
	 * @author Tara Kelly
	 * @return List - the courses in the database.
	 */
	@GetMapping("/courses")
	public List<Course> getAllCourses() {
		return service.findAll();
	}
	/**
	 * Retrieves a course pertaining to an id.
	 * @author Lori White and Tara Kelly
	 * @param id the course id to search for
	 * @return Course - the course pertaining to the id
	 * @throws ResourceNotFoundException is thrown when the id does not match an existing course in the database
	 */
	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable long id) throws ResourceNotFoundException {

		Optional<Course> course = service.findById(id);

		if (course.isEmpty()) {
			throw new ResourceNotFoundException("Course with id = " + id + " does not exist.");
		}

		return course.get();
	}
	/**
	 * Adds a course to DB.
	 * @author Lori White and Tara Kelly
	 * @param newCourse the new Course
	 * @return ResponseEntity whether or not course was inserted correctly
	 * @throws ResourceAlreadyExistsException is thrown when the id does match an existing course in the database
	 */
	@PostMapping("/add/course")
	public ResponseEntity<Course> addCourse(@Valid @RequestBody Course newCourse) throws ResourceAlreadyExistsException {
		if(service.existsById(newCourse.getId())) {
			throw new ResourceAlreadyExistsException("Course with id = " + newCourse.getId() + " already exists.");
		}
		
		Course created = service.save(newCourse);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	/**
	 * Retrieves a list of courses that are within the specified department.
	 * @author Lori White
	 * @param department the department name to search for
	 * @return List - the list of courses that are in the department specified
	 */
	@GetMapping("/courses/department/{department}")
	public List<Course> getCoursesByDepartment(@PathVariable String department) {
		return service.findByDepartmentContaining(department);
	}
	/**
	 * Retrieves the course with the name that was searched for.
	 * @author Lori White
	 * @param name the name of the course to search for
	 * @return Course - the course that was found
	 */
	@GetMapping("/courses/name/{name}")
	public List<Course> getCourseByName(@PathVariable String name) {
		return service.findByNameContaining(name);
	}
}
