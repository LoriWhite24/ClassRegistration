package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The model for Registration.
 * @author Lori White
 * @version v2 (08/10/2020)
 */
@Entity
@Table(name = "registration")
public class Registration implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id", unique = true, nullable = false)
	private Long id;
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
	private Date registrationDate;
	@Column(columnDefinition = "tinyint(1) default false")
	private Boolean hasWithdrawn;
	@NotNull
	private Long studentId;
	@NotNull
	private Long courseId;
	
	/**
	 * The default constructor.
	 * @author Lori White
	 */
	public Registration() {
		this(new Date(), false, -1L, -1L);
	}
	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param registrationDate the date the student has signed up for the course
	 * @param hasWithdrawn whether the student has withdrawn from the course that they have registered for
	 * @param studentId the student id of the student that registered for the course
	 * @param courseId the course id of the course the student registered for
	 */
	public Registration(@NotNull Date registrationDate, Boolean hasWithdrawn, @NotNull Long studentId, @NotNull Long courseId) {
		super();
		this.registrationDate = registrationDate;
		this.hasWithdrawn = hasWithdrawn;
		this.studentId = studentId;
		this.courseId = courseId;
	}
	/**
	 * Retrieves the registry entry id.
	 * @author Lori White
	 * @return Long - the registry entry id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Retrieves the date the student has signed up for the course.
	 * @author Lori White
	 * @return Date - the date the student has signed up for the course
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * Updates the date the student has signed up for the course.
	 * @author Lori White
	 * @param registrationDate the date the student has signed up for the course
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * Retrieves the student has withdrawn from the course that they have registered for.
	 * @author Lori White
	 * @return Boolean - the student has withdrawn from the course that they have registered for
	 */
	public Boolean getHasWithdrawn() {
		return hasWithdrawn;
	}
	/**
	 * Updates the student has withdrawn from the course that they have registered for.
	 * @author Lori White
	 * @param hasWithdrawn the student has withdrawn from the course that they have registered for
	 */
	public void setHasWithdrawn(Boolean hasWithdrawn) {
		this.hasWithdrawn = hasWithdrawn;
	}
	/**
	 * Retrieves the student that registered for the course.
	 * @author Lori White
	 * @return Long - the student that registered for the course
	 */
	public Long getStudent() {
		return studentId;
	}
	/**
	 * Updates the student that registered for the course.
	 * @author Lori White
	 * @param studentId the student that registered for the course
	 */
	public void setStudent(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * Retrieves the course the student registered for.
	 * @author Lori White
	 * @return Long - the course the student registered for
	 */
	public Long getCourse() {
		return courseId;
	}
	/**
	 * Updates the course the student registered for.
	 * @author Lori White
	 * @param courseId the course the student registered for
	 */
	public void setCourse(Long courseId) {
		this.courseId = courseId;
	}
	/**
	 * Retrieves the serial version UID for this class.
	 * @author Lori White
	 * @return long - the serial version UID for this class
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * Creates a string representation of a registry entry.
	 * @author Lori White
	 * @return String - a string representation of a registry entry
	 */
	@Override
	public String toString() {
		return "Registration [id=" + id + ", registrationDate=" + registrationDate + ", hasWithdrawn=" + hasWithdrawn
				+ ", student=" + studentId + ", course=" + courseId + "]";
	}
}
