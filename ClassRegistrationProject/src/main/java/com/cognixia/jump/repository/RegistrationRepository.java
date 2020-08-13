package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Registration;

/**
 * The Repository for Registration.
 * @author Lori White
 * @version v4 (08/12/2020)
 */
@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
	//added by Lori White
	List<Registration> findByStudentId(Long studentId);
	//added by Lori White
	List<Registration> findByCourseId(Long courseId);
	//added by Lori White
	boolean existsByStudentId(Long studentId);
	//added by Lori White
	boolean existsByCourseId(Long courseId);
	//added by Lori White
	@Query("select r from Registration r where r.studentId=?1 and r.courseId=?2")
	Registration findByStudentIdandCourseId(Long studentId, Long courseId);
	//added by Lori White
	@Query("select case when count(r)> 0 then true else false end from Registration r where r.studentId=?1 and r.courseId=?2")
	boolean existsByStudentIdandCourseId(Long studentId, Long courseId);
}
