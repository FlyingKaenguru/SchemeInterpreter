package de.hdm.schemeinterpreter.symbols;

import java.util.Arrays;

public class Addition implements Symbol {
    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public String eval(String... params) throws RuntimeException {
        return String.valueOf(Arrays.stream(params).mapToDouble(Double::valueOf).sum());
    }
}
