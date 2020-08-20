package me.remil.notes.exception.handler;

import java.sql.Timestamp;

import me.remil.notes.exception.IdAlreadyExistsException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import me.remil.notes.entity.ErrorResponse;
import me.remil.notes.exception.BadParameterException;
import me.remil.notes.exception.NotFoundException;
import me.remil.notes.exception.UnauthorizedRequestException;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
		
		ErrorResponse error = new ErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(e.getCause().getMessage());
		error.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(NotFoundException e) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleException(BadCredentialsException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleException(ExpiredJwtException e) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage("Session Expired! Login again");
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UnauthorizedRequestException.class)
	public ResponseEntity<ErrorResponse> handleException(UnauthorizedRequestException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadParameterException.class)
	public ResponseEntity<ErrorResponse> handleException(BadParameterException e) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<ErrorResponse> handleException(SignatureException e) {
		
		ErrorResponse errorResponse = new ErrorResponse();
		
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage("Invalid or Malformed Token");
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IdAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(IdAlreadyExistsException e) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(e.getMessage());
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ErrorResponse> handleException(EmptyResultDataAccessException e) {
		ErrorResponse errorResponse = new ErrorResponse();

		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage("No record exists");
		errorResponse.setTimeStamp(new Timestamp(System.currentTimeMillis()));

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
