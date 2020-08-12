package com.cognixia.jump.exception;

public class ResourceNotFoundException extends Exception{
	private static final long serialVersionUID = 5777514695224236672L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
