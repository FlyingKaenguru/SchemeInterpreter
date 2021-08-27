package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CdrTest {
    private static final Symbol cdr = new Cdr();

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new Construct(),
                new List(),
                new Display(),
                new Define(),
                new Cdr());
    }

    @Test
    void singleConstructNumber() {
        assertEquals("2", Main.parseInputString("(display (cdr (cons 1 2)))"));
    }

    @Test
    void singleConstructString() {
        assertEquals("\"World\"", Main.parseInputString("(display (cdr (cons '\"Hello\"' '\"World\"')))"));
    }

    @Test
    void singleListString() {
        assertEquals("\"World\"", Main.parseInputString("(display (cdr (list \"\\\"Hello\\\"\" \"\\\"World\\\"\")))"));
    }

    @Test
    void singleListWithMultiString() {
        assertEquals("(\"Hello2\" \"World\")", Main.parseInputString("(display (cdr (list \"Hello\" \"Hello2\" \"World\")))"));
    }


    @Test
    void ConstructInConstruct() {
        //((1 . 2) . 3)
        assertEquals("3", Main.parseInputString("(display (cdr (cons (cons 1 2) 3)))"));
    }

    @Test
    void doubleConstructInConstruct() {
        //((1 . 2) 3 . 4)
        assertEquals("(3 . 4)", Main.parseInputString("(display (cdr (cons (cons 1 2) (cons 3 4))))"));
    }

        @Test
    void multiConstruct() {
        //((3 1 . 2) (3 1 . 2) . 4)
        //((3 1 . 2) (3 1 . 2) . 4)
        var multicons = "(define conCdr_a (cons 1 2))(define conCdr_b (cons 3 conCdr_a))(define conCdr_c (cons conCdr_b 4))(define conCdr_d (cons conCdr_b conCdr_c))";
        Main.parseInputString(multicons);
        assertEquals("((3 1 . 2) . 4)", Main.parseInputString("(display (cdr conCdr_d)) "));
    }

    @Test
    void singleList() {
        var list = "(define listCdr_a (list 1 2 3))";
        Main.parseInputString(list);
        assertEquals("(2 3)", Main.parseInputString("(display (cdr listCdr_a))"));
    }

    @Test
    void nestedList() {
        var nestedList = "(define listCdr_b (list (list 1 2) (list 3 4)))";
        Main.parseInputString(nestedList);
        assertEquals("((3 4))", Main.parseInputString("(display (cdr listCdr_b))"));
    }

    @Test
    void middleList() {
        var nestedList = "(define listCdr_c (list  1 (list 2 3) 4))";
        Main.parseInputString(nestedList);
        assertEquals("((2 3) 4)", Main.parseInputString("(display (cdr listCdr_c))"));
    }
}