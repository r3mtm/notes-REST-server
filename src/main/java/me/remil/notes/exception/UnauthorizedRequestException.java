package me.remil.notes.exception;

public class UnauthorizedRequestException extends RuntimeException {

	private static final long serialVersionUID = 5283977272994364302L;

	public UnauthorizedRequestException(String message) {
		super(message);
	}

}
