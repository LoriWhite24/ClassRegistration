package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>{

}
