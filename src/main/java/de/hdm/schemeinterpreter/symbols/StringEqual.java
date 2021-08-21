package de.hdm.schemeinterpreter.symbols;

public class StringEqual implements Symbol {
    @Override
    public String getSymbol() {
        return "equal?";
    }

    @Override
    public String eval(String... params) {
        for (int i = 1; i < params.length; i++) {
            if (!params[i - 1].equals(params[i])) {
                return "#f";
            }
        }

        return "#t";
    }
}
