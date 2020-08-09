package com.cognixia.jump.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The model for Registration.
 * @author Lori White
 * @version v1 (08/08/2020)
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Student.class)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Course.class)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	/**
	 * The default constructor.
	 * @author Lori White
	 */
	public Registration() {
		this(new Date(), false, new Student(), new Course());
	}
	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param registrationDate the date the student has signed up for the course
	 * @param hasWithdrawn whether the student has withdrawn from the course that they have registered for
	 * @param student the student that registered for the course
	 * @param course the course the student registered for
	 */
	public Registration(@NotNull Date registrationDate, Boolean hasWithdrawn, Student student, Course course) {
		super();
		this.registrationDate = registrationDate;
		this.hasWithdrawn = hasWithdrawn;
		this.student = student;
		this.course = course;
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
	 * @return Student - the student that registered for the course
	 */
	public Student getStudent() {
		return student;
	}
	/**
	 * Updates the student that registered for the course.
	 * @author Lori White
	 * @param student the student that registered for the course
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	/**
	 * Retrieves the course the student registered for.
	 * @author Lori White
	 * @return Course - the course the student registered for
	 */
	public Course getCourse() {
		return course;
	}
	/**
	 * Updates the course the student registered for.
	 * @author Lori White
	 * @param course the course the student registered for
	 */
	public void setCourse(Course course) {
		this.course = course;
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
				+ ", student=" + student + ", course=" + course + "]";
	}
}
