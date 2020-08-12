package com.cognixia.jump.exception;

public class InvalidLoginException extends Exception{
	
	private static final long serialVersionUID = 5035892024047509790L;
	
	public InvalidLoginException() {
		super("Password is Invalid!");
	}
}
