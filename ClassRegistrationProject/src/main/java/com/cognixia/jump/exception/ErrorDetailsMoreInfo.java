package com.cognixia.jump.exception;

import java.util.Date;

/**
 * The model for ErrorDetailsMoreInfo.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
public class ErrorDetailsMoreInfo extends ErrorDetails {
	private Integer status;
	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param timestamp the current date the error occurred
	 * @param message the error message
	 * @param details the details of the error
	 * @param status the HTTP status code numeric value
	 */
	public ErrorDetailsMoreInfo(Date timestamp, String message, String details, Integer status) {
		super(timestamp, message, details);
		this.status = status;
	}
	/**
	 * Retrieves the HTTP status code numeric value.
	 * @author Lori White
	 * @return Integer - the HTTP status code numeric value
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * Updates the HTTP status code numeric value.
	 * @author Lori White
	 * @param status the HTTP status code numeric value
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * Creates a string representation of an error details more info.
	 * @author Lori White
	 * @return String - a string representation of an error details more info
	 */
	@Override
	public String toString() {
		return super.toString() + "ErrorDetailsMoreInfo [status=" + status + "]";
	}

}
