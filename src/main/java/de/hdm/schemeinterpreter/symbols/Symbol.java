package de.hdm.schemeinterpreter.symbols;

public interface Symbol {

    String getSymbol();

    //TODO: String param to list
    String eval(String... params);

}
