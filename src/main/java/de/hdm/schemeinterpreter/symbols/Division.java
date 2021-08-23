package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Validator;

import java.util.Arrays;

public class Division implements Symbol {
    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public String getParamDefinition() {
        return "^(?:" + Validator.Type.floatingPoint + " ){2,}$";
    }

    @Override
    public String eval(String... validatedParams) throws RuntimeException {
        double[] numbers = Arrays.stream(validatedParams).mapToDouble(Double::valueOf).toArray();
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result /= numbers[i];
        }
        return String.valueOf(result);
    }
}

