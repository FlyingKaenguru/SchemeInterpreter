package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.symbols.Symbol;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SymbolManager {

    public static String generateVarId() {
        return "$_" + UUID.randomUUID();
    }

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

    public boolean replaceSymbol(String symbol, String newValue) {
        final Optional<Symbol> oldSymbol = this.getSymbol(symbol);
        if (oldSymbol.isPresent()) {
            this.symbols.remove(oldSymbol.get());
            this.addSymbol(SymbolFactory.createVariable(symbol, newValue));
            return true;
        }
        return false;
    }


    public void addSymbol(Symbol symbol) {
        if (this.symbols.stream().anyMatch(e -> e.getSymbol().equals(symbol.getSymbol()))) {
            // TODO: Maybe handle more gracefully to keep the program running.
            throw new RuntimeException("Variable '" + symbol.getSymbol() + "' already exists.");
        }

        this.symbols.add(symbol);
    }

    public void addSymbols(List<? extends Symbol> symbols) {
        symbols.forEach(this::addSymbol);
    }

    public String resolveVar(String param) {
        if (!(Validator.isSchemeVar(param) || Validator.isInternalVar(param))) {
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
