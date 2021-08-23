package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SchemeType;
import de.hdm.schemeinterpreter.Validator;

public class StringEqual implements Symbol {
    @Override
    public String getSymbol() {
        return "equal?";
    }

    @Override
    public String getParamDefinition() {
        return "(?:(?:" + Validator.Type.string + ")|(?:" + SchemeType.NULL + ") ){2,}";
    }

    @Override
    public String eval(String... validatedParams) {
        for (int i = 1; i < validatedParams.length; i++) {
            if (!validatedParams[i - 1].equals(validatedParams[i])) {
                return "#f";
            }
        }

        return "#t";
    }
}
