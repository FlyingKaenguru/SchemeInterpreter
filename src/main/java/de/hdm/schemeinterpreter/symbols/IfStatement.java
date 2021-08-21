package de.hdm.schemeinterpreter.symbols;

public class IfStatement implements Symbol {
    @Override
    public String getSymbol() {
        return "if";
    }

    @Override
    public String eval(String... params) {
        for (String param : params) {
            if (!param.equals("#t")) {
                return "#f";
            }
        }

        return "#t";
    }
}
