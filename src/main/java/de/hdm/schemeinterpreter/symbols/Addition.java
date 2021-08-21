package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.symbols.Symbol;

import java.util.Arrays;

public class Addition implements Symbol {

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public String eval(String... params) throws RuntimeException{
        return String.valueOf(Arrays.stream(params).mapToDouble(Double::valueOf).sum());
    }
}
