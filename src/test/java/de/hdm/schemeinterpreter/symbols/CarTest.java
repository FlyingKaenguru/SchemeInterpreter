package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @BeforeAll
    static void setup() {
        Util.addSymbols(
                new Construct(),
                new List(),
                new Display(),
                new Define(),
                new Car());
    }

    @Test
    void multiCons() {
        var multicons = "(define conCar_a (cons 1 2))(define conCar_b (cons 3 conCar_a))(define conCar_c (cons conCar_b 4))(define conCar_d (cons conCar_b conCar_c))";
        Main.parseInputString(multicons);
        assertEquals("(3 1 . 2)", Main.parseInputString("(display (car conCar_d)) "));
    }

    @Test
    void singelList() {
        var list = "(define listCar_a (list 1 2 3))";
        Main.parseInputString(list);
        assertEquals("1", Main.parseInputString("(display (car listCar_a))"));
    }

    @Test
    void nestedList() {
        var nestedList = "(define listCar_b (list '(1 2) '(3 4)))";
        Main.parseInputString(nestedList);
        assertEquals("(1 2)", Main.parseInputString("(display (car listCar_b))"));
    }
}