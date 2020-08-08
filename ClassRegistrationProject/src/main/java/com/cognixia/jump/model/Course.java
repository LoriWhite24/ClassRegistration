package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String department;
	private int noCredits;

	public Course() {
		this(-1L, "N/A", "N/A", 0);
	}
	
	public Course(Long id, String name, String department, int noCredits) {
		super();
		this.id = id;
		this.name = name;
		this.department = department;
		this.noCredits = noCredits;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getNoCredits() {
		return noCredits;
	}

	public void setNoCredits(int noCredits) {
		this.noCredits = noCredits;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", department=" + department + ", noCredits=" + noCredits + "]";
	}
	
}
