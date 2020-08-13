package com.cognixia.jump.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * The model for Course.
 * @author Tara Kelly
 * @version v3 (08/10/2020)
 */
@Entity
@Table(name = "course")
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", unique = true, nullable = false)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String department;
	@Column(columnDefinition = "int default 3")
	private Integer noCredits;
	
	/**
	 * The default constructor.
	 * @author Tara Kelly
	 */
	public Course() {
		this("N/A", "N/A", 0);
	}
	/**
	 * The overloaded constructor.
	 * @author Tara Kelly
	 * @param name the name of the course
	 * @param department the department the course is associated with
	 * @param noCredits the number of credits the course is worth
	 */
	public Course(@NotBlank String name, @NotBlank String department, Integer noCredits) {
		super();
		this.name = name;
		this.department = department;
		this.noCredits = noCredits;
	}
	/**
	 * Retrieves the course id.
	 * @author Tara Kelly
	 * @return Long - the course id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Retrieves the name of the course.
	 * @author Tara Kelly
	 * @return String - the name of the course
	 */
	public String getName() {
		return name;
	}
	/**
	 * Updates the name of the course.
	 * @author Tara Kelly
	 * @param name the name of the course
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Retrieves the department the course is associated with
	 * @author Tara Kelly
	 * @return String - the department the course is associated with
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * Updates the department the course is associated with.
	 * @author Tara Kelly
	 * @param department the department the course is associated with
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * Retrieves the number of credits the course is worth.
	 * @author Tara Kelly
	 * @return Integer - the number of credits the course is worth
	 */
	public Integer getNoCredits() {
		return noCredits;
	}
	/**
	 * Updates the number of credits the course is worth.
	 * @author Tara Kelly
	 * @param noCredits the number of credits the course is worth
	 */
	public void setNoCredits(Integer noCredits) {
		this.noCredits = noCredits;
	}
	/**
	 * Retrieves the serial version UID for this class.
	 * @author Tara Kelly
	 * @return long - the serial version UID for this class
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Creates a string representation of a course.
	 * @author Tara Kelly
	 * @return String - a string representation of a course
	 */
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", department=" + department + ", noCredits=" + noCredits + "]";
	}
}
