package de.hdm.schemeinterpreter.symbols;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AdditionTest {

    private final Addition addition = new Addition();

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new Addition(),
                new Display(),
                new Define());
    }

    @Test
    void eval() {
        assertEquals("16.0", addition.eval("4", "5", "7"));
        assertEquals("14.34", addition.eval("5.34", "9"));
    }
}