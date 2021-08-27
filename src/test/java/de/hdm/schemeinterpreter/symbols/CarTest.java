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
    void singleConstructNumber() {
        assertEquals("1", Main.parseInputString("(display (car (cons 1 2)))"));
    }

    @Test
    void singleConstructString() {
        assertEquals("\"Hello\"", Main.parseInputString("(display (car (cons '\"Hello\"' '\"World\"')))"));
    }

    @Test
    void singleListString() {
        assertEquals("\"Hello\"", Main.parseInputString("(display (car (list '\"Hello\"' '\"World\"')))"));
    }

    @Test
    void ConstructInConstruct() {
        assertEquals("(1 . 2)", Main.parseInputString("(display (car (cons (cons 1 2) 3)))"));
    }

    @Test
    void doubleConstructInConstruct() {
        //((1 . 2) 3 . 4)
        assertEquals("(1 . 2)", Main.parseInputString("(display (car (cons (cons 1 2) (cons 3 4))))"));
    }

    @Test
    void multiConstruct() {
        //((3 1 . 2) (3 1 . 2) . 4)
        var multicons = "(define conCar_a (cons 1 2))(define conCar_b (cons 3 conCar_a))(define conCar_c (cons conCar_b 4))(define conCar_d (cons conCar_b conCar_c))";
        Main.parseInputString(multicons);
        assertEquals("(3 1 . 2)", Main.parseInputString("(display (car conCar_d)) "));
    }

    @Test
    void singleList() {
        var list = "(define listCar_a (list 1 2 3))";
        Main.parseInputString(list);
        assertEquals("1", Main.parseInputString("(display (car listCar_a))"));
    }

    @Test
    void nestedList() {
        var nestedList = "(define listNested (list (list 1 2) (list 3 4)))";
        Main.parseInputString(nestedList);
        assertEquals("(1 2)", Main.parseInputString("(display (car listNested))"));
    }
}