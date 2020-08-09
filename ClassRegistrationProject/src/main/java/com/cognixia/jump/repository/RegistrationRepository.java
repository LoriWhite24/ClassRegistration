package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Registration;
import com.cognixia.jump.model.Student;

/**
 * The Repository for Registration.
 * @author Lori White
 * @version v1 (08/08/2020)
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
	//added by Lori White
	List<Registration> findAll();
	//added by Lori White
	List<Registration> findByStudentContaining(Student student);
}
