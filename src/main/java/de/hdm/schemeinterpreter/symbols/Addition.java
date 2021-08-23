package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Validator;

import java.util.Arrays;

public class Addition implements Symbol {
    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public String getParamDefinition() {
        return Validator.enclosed("(?:" + Validator.Type.floatingPoint + " ){2,}");
    }

    @Override
    public String eval(String... validatedParams) throws RuntimeException {
        return String.valueOf(Arrays.stream(validatedParams).mapToDouble(Double::valueOf).sum());
    }
}
