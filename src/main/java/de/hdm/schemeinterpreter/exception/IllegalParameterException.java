package de.hdm.schemeinterpreter.exception;

public class IllegalParameterException extends IllegalArgumentException{

    public IllegalParameterException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Unfortunately, the parameters entered are not valid.\n" +
                "\n" +
                "Possible error sources:\n" +
                "- Too many or too few parameters were passed \n" +
                "- One or more parameter for symbol are not valid";
    }
}
