package com.cognixia.jump.exception;

import java.util.Date;

/**
 * The model for ErrorDetails.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;
	/**
	 * The overloaded constructor.
	 * @author Lori White
	 * @param timestamp the current date the error occurred
	 * @param message the error message
	 * @param details the details of the error
	 */
	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	/**
	 * Retrieves the current date the error occurred.
	 * @author Lori White
	 * @return Date - the current date the error occurred
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * Updates the current date the error occurred.
	 * @author Lori White
	 * @param timestamp the current date the error occurred
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * Retrieves the error message.
	 * @author Lori White
	 * @return String - the error message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Updates the error message.
	 * @author Lori White
	 * @param message the error message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Retrieves the details of the error.
	 * @author Lori White
	 * @return String - the details of the error
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * Updates the details of the error.
	 * @author Lori White
	 * @param details the details of the error
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	/**
	 * Creates a string representation of an error details.
	 * @author Lori White
	 * @return String - a string representation of an error details
	 */
	@Override
	public String toString() {
		return "ErrorDetails [timestamp=" + timestamp + ", message=" + message + ", description=" + details + "]";
	}

}
