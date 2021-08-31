package de.hdm.schemeinterpreter.exception;

public class SyntaxErrorException extends IllegalArgumentException{
    String message;

    public SyntaxErrorException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
