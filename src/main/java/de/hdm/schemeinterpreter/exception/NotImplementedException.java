package de.hdm.schemeinterpreter.exception;

public class NotImplementedException extends IllegalArgumentException{
    String message;

    public NotImplementedException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
