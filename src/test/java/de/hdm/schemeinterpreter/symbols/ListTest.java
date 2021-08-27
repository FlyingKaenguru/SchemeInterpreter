package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new List(),
                new Display(),
                new Define());
    }

    @Test
    void nestedList() {
        var nestedList = "(define listCar_b (list (list 1 2) (list 3 4)))";
        Main.parseInputString(nestedList);
        assertEquals("((1 2) (3 4))", Main.parseInputString("(display listCar_b)"));
    }


}