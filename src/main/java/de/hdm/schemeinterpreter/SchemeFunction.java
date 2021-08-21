package de.hdm.schemeinterpreter;

public class SchemeFunction {

    public final String original;
    public final String symbol;
    public final String[] params;

    SchemeFunction(String original, String symbol, String[] params) {
        this.original = original;
        this.symbol = symbol;
        this.params = params;
    }

    @Override
    public String toString() {
        return "{symbol: \"" + this.symbol + "\", params: [" + String.join(", ", this.params) + "]}";
    }

}
