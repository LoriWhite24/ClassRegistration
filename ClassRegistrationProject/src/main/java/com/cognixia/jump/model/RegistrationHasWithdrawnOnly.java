package com.cognixia.jump.model;

import java.io.Serializable;

/**
 * The model for RegistrationHasWithdrawnOnly.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
public class RegistrationHasWithdrawnOnly implements Serializable{
	private static final long serialVersionUID = 3201997868260758162L;
	
	private Long studentId;
	private Long courseId;
	private Boolean hasWithdrawn;

	public RegistrationHasWithdrawnOnly(Long studentId, Long courseId, Boolean hasWithdrawn) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.hasWithdrawn = hasWithdrawn;
	}
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Boolean getHasWithdrawn() {
		return hasWithdrawn;
	}

	public void setHasWithdrawn(Boolean hasWithdrawn) {
		this.hasWithdrawn = hasWithdrawn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
