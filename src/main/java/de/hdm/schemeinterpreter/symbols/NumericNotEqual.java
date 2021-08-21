package de.hdm.schemeinterpreter.symbols;

import java.util.Arrays;

public class NumericNotEqual implements Symbol {
    @Override
    public String getSymbol() {
        return "!=";
    }

    @Override
    public String eval(String... params) {
        double[] numbers = Arrays.stream(params).mapToDouble(Double::valueOf).toArray();

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i - 1] == numbers[i]) {
                return "#f";
            }
        }

        return "#t";
    }
}
