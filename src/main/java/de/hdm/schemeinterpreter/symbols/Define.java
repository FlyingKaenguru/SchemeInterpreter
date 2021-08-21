package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;

public class Define implements Symbol {

    @Override
    public String getSymbol() {
        return "define";
    }

    @Override
    public String eval(String... params) {
        final String value = params[1];

        SymbolManager.getInstance().addSymbol(new Symbol() {
            @Override
            public String getSymbol() {
                return params[0];
            }

            @Override
            public String eval(String... params) {
                return value;
            }
        });
        return "";
    }
}