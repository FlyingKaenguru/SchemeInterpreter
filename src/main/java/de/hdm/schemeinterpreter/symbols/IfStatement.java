package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SchemeType;
import de.hdm.schemeinterpreter.Validator;

public class IfStatement implements Symbol {
    @Override
    public String getSymbol() {
        return "if";
    }

    @Override
    public String getParamDefinition() {
        return Validator.enclosed(Validator.Type.bool + " (?:" + Validator.Type.any + " ){2}");
    }

    @Override
    public String eval(String... validatedParams) {
            if (validatedParams[0].equals(SchemeType.TRUE)) {
                return validatedParams[1];
            }
            return validatedParams[2];
    }
}
