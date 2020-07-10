package me.remil.notes.exception;

public class BadParameterException extends RuntimeException {

	private static final long serialVersionUID = 1347198905690096130L;

	public BadParameterException(String message) {
		super(message);
	}

	public BadParameterException(Throwable cause) {
		super(cause);
	}

	public BadParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
