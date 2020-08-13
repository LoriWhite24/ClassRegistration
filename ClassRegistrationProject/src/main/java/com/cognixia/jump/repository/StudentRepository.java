package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Student;

/**
 * The Repository for Students.
 * @author Lori White and Tara Kelly
 * @version v2 (08/10/2020)
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	//added by Lori White
	@Query("select s from Student s where s.email=?1")
	Student findByEmail(String email);
	//added by Lori White
	@Query("select case when count(s)> 0 then true else false end from Student s where s.email=?1")
	boolean existsByEmail(String email);
}
