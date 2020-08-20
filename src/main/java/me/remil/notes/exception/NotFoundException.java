package me.remil.notes.exception;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = -3162510756860636755L;

	public NotFoundException(String message) {
		super(message);
	}
	
}
