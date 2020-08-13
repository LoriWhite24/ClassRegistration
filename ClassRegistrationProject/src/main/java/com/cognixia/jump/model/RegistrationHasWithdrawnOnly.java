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

	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param studentId the student id of the student that registered for the course
	 * @param courseId the course id of the course the student registered for
	 * @param hasWithdrawn whether the student has withdrawn from the course that they have registered for
	 */
	public RegistrationHasWithdrawnOnly(Long studentId, Long courseId, Boolean hasWithdrawn) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.hasWithdrawn = hasWithdrawn;
	}
	/**
	 * Retrieves the student's id that registered for the course.
	 * @author Lori White
	 * @return Long - the student's id that registered for the course
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * Updates the student's id that registered for the course.
	 * @author Lori White
	 * @param studentId the student's id that registered for the course
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * Retrieves the course's id the student registered for.
	 * @author Lori White
	 * @return Long - the course's id the student registered for
	 */
	public Long getCourseId() {
		return courseId;
	}
	/**
	 * Updates the course's id the student registered for.
	 * @author Lori White
	 * @param courseId the course's id the student registered for
	 */
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
	 * Retrieves the serial version UID for this class.
	 * @author Lori White
	 * @return long - the serial version UID for this class
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
}
