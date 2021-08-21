package de.hdm.schemeinterpreter.symbols;

public class Display implements Symbol{

    public Display(){
    }

    @Override
    public String getSymbol() {
        return "display";
    }

    @Override
    public String eval(String... params) {
        return String.join(" ", params);
    }
}
