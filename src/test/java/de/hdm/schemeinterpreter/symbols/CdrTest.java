package de.hdm.schemeinterpreter.symbols;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CdrTest {
    private final Symbol cdr = new Cdr();

    @Test
    void eval() {
        assertEquals("2", cdr.eval("(1 . 2)"));
        assertEquals("\"World\"", cdr.eval("(\"Hello\" . \"World\")"));
        assertEquals("3", cdr.eval("((1 . 2) . 3)"));
        assertEquals("(3 . 4)", cdr.eval("((1 . 2) 3 . 4)"));
        assertEquals("(\"c\" . \"d\")", cdr.eval("((1 . \"b\") \"c\" . \"d\")"));
    }
}