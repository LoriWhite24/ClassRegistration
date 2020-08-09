package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Student;

/**
 * The Repository for Students.
 * @author Tara Kelly
 * @version v1 (08/08/2020)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
