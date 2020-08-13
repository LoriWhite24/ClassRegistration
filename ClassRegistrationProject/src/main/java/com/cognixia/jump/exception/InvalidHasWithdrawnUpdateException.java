package com.cognixia.jump.exception;

/**
 * The custom exception for an invalid has withdrawn update.
 * @author Lori White
 * @version v2 (08/13/2020)
 */
public class InvalidHasWithdrawnUpdateException extends Exception{
	private static final long serialVersionUID = 50358924047509790L;
	/**
	 * The default constructor.
	 * @author Lori White
	 * @param hasWithdrawn whether the student is choosing to withdraw or re-enroll
	 */
	public InvalidHasWithdrawnUpdateException(Boolean hasWithdrawn) {
		super(generateErrorMessage(hasWithdrawn));
	}
	/**
	 * Generates an error message based on whether the student is choosing to withdraw or re-enroll
	 * @author Lori White
	 * @param withdrawn whether the student is choosing to withdraw or re-enroll
	 * @return String - an error message
	 */
	private static String generateErrorMessage(Boolean withdrawn) {
		if(withdrawn) {
			return "Can not withdraw when not currently registered.";
		} 
		return "Can not re-enroll when currently registered.";
	}
}
