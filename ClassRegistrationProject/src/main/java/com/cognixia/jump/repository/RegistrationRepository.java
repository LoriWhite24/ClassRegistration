package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Registration;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
	List<Registration> findAll();
}
