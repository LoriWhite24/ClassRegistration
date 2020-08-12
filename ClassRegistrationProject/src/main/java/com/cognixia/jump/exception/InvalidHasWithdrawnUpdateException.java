package com.cognixia.jump.exception;

public class InvalidHasWithdrawnUpdateException extends Exception{
	private static final long serialVersionUID = 50358924047509790L;
	
	public InvalidHasWithdrawnUpdateException() {
		super("Has Withdrawn value is Invalid!");
	}
}
