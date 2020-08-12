package com.cognixia.jump.exception;

public class ResourceAlreadyExistsException extends Exception{
	private static final long serialVersionUID = 5777514691224236672L;
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}
}
