package de.hdm.schemeinterpreter.symbols;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class AdditionTest {

    private final Addition addition = new Addition();

    @Test
    void eval() {
        assertEquals("16.0", addition.eval("4", "5", "7"));
        assertEquals("14,34", addition.eval("5.34", "9"));
    }
}