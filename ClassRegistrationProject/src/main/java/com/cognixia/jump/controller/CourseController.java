package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Course;
import com.cognixia.jump.repository.CourseRepository;

/**
 * The controller for the Courses. 
 * @author Lori White and Tara Kelly
 * @version v2 (08/08/2020)
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
	 * @author Tara Kelly
	 * @param id the course id to search for
	 * @return Course - the course pertaining to the id
	 */
	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable long id) {

		Optional<Course> course = service.findById(id);

		if (course.isPresent()) {
			return course.get();
		}

		return new Course();
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