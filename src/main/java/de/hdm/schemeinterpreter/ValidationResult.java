package de.hdm.schemeinterpreter;

public class ValidationResult<T> {
    public enum Status {
        VALID,
        INVALID
    }

    public final T validationSubject;
    public final Status status;
    public final String message;

    public ValidationResult(T validationSubject, Status status, String message) {
        this.validationSubject = validationSubject;
        this.status = status;
        this.message = message;
    }
}
