package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.symbols.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SymbolManager {

    protected static SymbolManager instance;
    protected List<Symbol> symbols;

    public static SymbolManager getInstance() {
        if (null == instance) {
            instance = new SymbolManager();
        }

        return instance;
    }

    public SymbolManager() {
        this.symbols = new ArrayList<>();
    }

    public Optional<Symbol> getSymbol(String symbol) {
        return symbols.stream().filter(f -> f.getSymbol().equals(symbol)).findFirst();
    }

    public boolean replaceSymbol(String symbol, String newValue){
        //TODO - duplicate Code
        Optional<Symbol> oldSymbol = this.getSymbol(symbol);
        if (oldSymbol.isPresent()){
            this.symbols.remove(oldSymbol.get());
            this.addSymbol(new Symbol() {
                @Override
                public String getSymbol() {
                    return symbol;
                }

                @Override
                public String getParamDefinition() {
                    return null;
                }

                @Override
                public String eval(String... validatedParams) {
                    return newValue;
                }
            });
            return true;
        }
        return false;
    }

    public void addSymbol(Symbol symbol) {
        this.symbols.add(symbol);
    }

    public void addSymbols(List<? extends Symbol> symbols) {
        this.symbols.addAll(symbols);
    }

    public String resolveVar(String param) {
        if (!Validator.isSchemeVar(param)) {
            return param;
        }

        Optional<Symbol> symbol = getSymbol(param);

        if (symbol.isPresent()) {
            return symbol.get().eval(param);
        } else {
            // TODO: Maybe handle more gracefully to keep the program running.
            throw new NullPointerException("Variable '" + param + "' is undefined.");
        }
    }
}
