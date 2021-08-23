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
        return "^(?:" + Validator.Type.bool + " )+$";
    }

    @Override
    public String eval(String... validatedParams) {
        for (String param : validatedParams) {
            if (!param.equals(SchemeType.TRUE)) {
                return SchemeType.FALSE;
            }
        }

        return SchemeType.TRUE;
    }
}
