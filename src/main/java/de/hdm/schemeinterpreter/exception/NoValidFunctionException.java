package de.hdm.schemeinterpreter.exception;

public class NoValidFunctionException extends IllegalArgumentException{

    public NoValidFunctionException() {
        super();
    }

    @Override
    public String getMessage() {
        return "No Scheme function could be determined.";
    }
}
