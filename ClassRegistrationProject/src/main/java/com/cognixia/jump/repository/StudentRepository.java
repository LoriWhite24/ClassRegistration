package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
