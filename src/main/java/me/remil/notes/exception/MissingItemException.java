package me.remil.notes.exception;

public class MissingItemException extends  RuntimeException{
    private static final long serialVersionUID = 2691780345007704156L;

    public MissingItemException(String message) {
        super(message);
    }
}
