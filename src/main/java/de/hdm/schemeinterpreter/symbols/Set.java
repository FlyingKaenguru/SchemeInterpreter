package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;

public class Set implements Symbol {
    @Override
    public String getSymbol() {
        return "set!";
    }

    @Override
    public String eval(String... params) {
        if(SymbolManager.getInstance().replaceSymbol(params[0], params[1])){
            return "";
        }else {
            //TODO - Symbole not definex - Exception
        }
        return "";
    }
}
