package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.ValidationResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStatementTest {

    private static final IfStatement ifStatement = new IfStatement();

    @BeforeAll
    static void setup() {
        Util.addSymbols(ifStatement);
    }

    @Test
    void eval() {
        assertEquals("17", ifStatement.eval("#t", "17", "3"));
    }

    @Test
    void valid(){
        assertEquals(ValidationResult.Status.VALID, ifStatement.validateParams(new String[]{"#t", "17", "3"}).status);
    }
}