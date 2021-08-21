package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.DeclareCallback;

public class Define implements Symbol {

    private DeclareCallback callback;

    public Define(DeclareCallback callback) {
        this.callback = callback;
    }

    @Override
    public String getSymbol() {
        return "define";
    }

    @Override
    public String eval(String... params) throws RuntimeException {
        this.callback.declare(params[0], params[1]);
        return "";
    }
}
