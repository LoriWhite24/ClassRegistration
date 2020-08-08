package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognixia.jump.model.Course;
import com.cognixia.jump.repository.CourseRepository;

public class CourseController {

	@Autowired
	CourseRepository service;

	@GetMapping("/courses")
	public List<Course> getAllCourses() {
		return service.findAll();
	}

	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable long id) {

		Optional<Course> course = service.findById(id);

		if (course.isPresent()) {
			return course.get();
		}

		return new Course();
	}
}
