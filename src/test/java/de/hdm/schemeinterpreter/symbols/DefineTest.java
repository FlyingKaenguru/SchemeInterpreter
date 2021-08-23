package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DefineTest {
    private static Define define = new Define();

    @BeforeAll
    static void define(){
        define.eval("test", "4");
    }

    @Test
    @Order(1)
    void symbolAdded() {
        Optional<Symbol> symbol = SymbolManager.getInstance().getSymbol("test");
        assertTrue(symbol.isPresent());
    }

    @Test
    @Order(2)
    void valueCheck(){
        Optional<Symbol> symbol = SymbolManager.getInstance().getSymbol("test");
        assertTrue(symbol.get().eval().equals("4"));
    }
}