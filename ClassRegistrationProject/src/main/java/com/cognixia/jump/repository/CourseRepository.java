package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Course;

/**
 * The Repository for Courses.
 * @author Lori White and Tara Kelly
 * @version v2 (08/08/2020)
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{
	//added by Lori White
	List<Course> findByDepartmentContaining(String department);
	//added by Lori White
	List<Course> findByNameContaining(String name);
}
