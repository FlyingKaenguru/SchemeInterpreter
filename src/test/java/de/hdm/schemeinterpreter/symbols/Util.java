package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;

public class Util {

    public static void addSymbols(Symbol... symbols) {
        final SymbolManager manager = SymbolManager.getInstance();

        for (Symbol symbol: symbols) {
            if (manager.getSymbol(symbol.getSymbol()).isEmpty()) {
                manager.addSymbol(symbol);
            }
        }
    }
}
