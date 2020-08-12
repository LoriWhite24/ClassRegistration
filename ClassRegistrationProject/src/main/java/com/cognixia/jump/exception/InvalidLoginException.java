package com.cognixia.jump.exception;

/**
 * The custom exception for an invalid login.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
public class InvalidLoginException extends Exception{
	
	private static final long serialVersionUID = 5035892024047509790L;
	/**
	 * The default constructor.
	 */
	public InvalidLoginException() {
		super("Password is Invalid!");
	}
}
