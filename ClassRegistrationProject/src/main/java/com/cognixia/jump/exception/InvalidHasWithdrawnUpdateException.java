package com.cognixia.jump.exception;

/**
 * The custom exception for an invalid has withdrawn update.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
public class InvalidHasWithdrawnUpdateException extends Exception{
	private static final long serialVersionUID = 50358924047509790L;
	/**
	 * The default constructor.
	 */
	public InvalidHasWithdrawnUpdateException() {
		super("Has Withdrawn value is Invalid!");
	}
}
