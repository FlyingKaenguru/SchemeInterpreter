package de.hdm.schemeinterpreter.symbols;

public class Newline implements Symbol {
    @Override
    public String getSymbol() {
        return "newline";
    }

    @Override
    public String getParamDefinition() {
        return null;
    }

    @Override
    public String eval(String... validatedParams) {
        return "\n";
    }
}
