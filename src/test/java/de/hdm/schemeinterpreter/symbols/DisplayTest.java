package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayTest {
    private static final Display display = new Display();

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new Display(),
                new Define());
    }

    @Test
    void eval() {
        assertEquals("Hello World", display.eval("\"Hello World\""));
        assertEquals("Hello \"World\"", display.eval("\"Hello \\\"World\\\"\""));
        assertEquals("Hello World", display.eval("'Hello World'"));
        assertEquals("Hello 'World'", display.eval("'Hello \\'World\\''"));
    }

    @Test
    void parseInput() {
        Main.parseInputString("(define x 5)");
        assertEquals("5", Main.parseInputString("(display x)"));
    }
}
