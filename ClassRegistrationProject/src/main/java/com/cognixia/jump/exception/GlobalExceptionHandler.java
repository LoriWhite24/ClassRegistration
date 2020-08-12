package com.cognixia.jump.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The GlobalExceptionHandler for this application.
 * @author Lori White
 * @version v1 (08/12/2020)
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * The handler for the custom exception for a resource that was not found.
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}
	/**
	 * The handler for the custom exception for an invalid login.
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<?> invalidLoginExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetailsMoreInfo errorDetails = new ErrorDetailsMoreInfo(new Date(), ex.getLocalizedMessage(), request.getDescription(false), HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	}
	/**
	 * The handler for the custom exception for an invalid has withdrawn update.
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(InvalidHasWithdrawnUpdateException.class)
	public ResponseEntity<?> invalidHasWithdrawnUpdateExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetailsMoreInfo errorDetails = new ErrorDetailsMoreInfo(new Date(), ex.getLocalizedMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}
	/**
	 * The handler for the custom exception for a resource that already exists.
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> resourceAlreadyExistsExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	}
	/**
	 * The handler an exception.
	 * @param ex the exception being thrown
	 * @param request the current web request
	 * @return ResponseEntity - the error details 
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
