package de.hdm.schemeinterpreter.symbols;

import java.util.Arrays;

public class Greater implements Symbol {
    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public String eval(String... params) throws RuntimeException {
        double[] numbers =  Arrays.stream(params).mapToDouble(Double::valueOf).toArray();
        double result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result -= numbers[i];
        }
        return String.valueOf(result);
    }
}