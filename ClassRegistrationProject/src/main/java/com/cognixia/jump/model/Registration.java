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

@Entity
@Table(name = "registration")
public class Registration implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "registration_id", unique = true, nullable = false)
	private Long id;
	private Date registrationDate;
	private Boolean hasWithdrawn;
	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Student.class)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	@ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Course.class)
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	
	public Registration() {
		this(-1L, new Date(), false, new Student(), new Course());
	}
	
	public Registration(Long id, Date registrationDate, Boolean hasWithdrawn, Student student, Course course) {
		super();
		this.id = id;
		this.registrationDate = registrationDate;
		this.hasWithdrawn = hasWithdrawn;
		this.student = student;
		this.course = course;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Boolean getHasWithdrawn() {
		return hasWithdrawn;
	}

	public void setHasWithdrawn(Boolean hasWithdrawn) {
		this.hasWithdrawn = hasWithdrawn;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Registration [id=" + id + ", registrationDate=" + registrationDate + ", hasWithdrawn=" + hasWithdrawn
				+ ", student=" + student + ", course=" + course + "]";
	}
}
