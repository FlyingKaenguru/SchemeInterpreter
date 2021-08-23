package de.hdm.schemeinterpreter.symbols;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTest {
    private final Display display = new Display();

    @Test
    void eval() {
        assertEquals("Hello World", display.eval("\"Hello World\""));
        assertEquals("Hello \"World\"", display.eval("\"Hello \\\"World\\\"\""));
        assertEquals("Hello World", display.eval("'Hello World'"));
        assertEquals("Hello 'World'", display.eval("'Hello \\'World\\''"));
    }
}
