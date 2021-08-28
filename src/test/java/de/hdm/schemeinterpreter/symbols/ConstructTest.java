package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstructTest {

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new Construct(),
                new Display(),
                new List(),
                new Define());
    }

    @Test
    void multiCons() {
        var multicons = "(define construct_a (cons 1 2))(define construct_b (cons 3 construct_a))(define construct_c (cons construct_b 4))(define construct_d (cons construct_b construct_c))))";
        Main.parseInputString(multicons);
        assertEquals("((3 1 . 2) (3 1 . 2) . 4)", Main.parseInputString("(display construct_d)"));
    }

    @Test
    void constructWithList() {
        var multicons = "(define consList (cons 1 (list 2 3)))";
        Main.parseInputString(multicons);
        assertEquals("(1 2 3)", Main.parseInputString("(display consList)"));
    }
}