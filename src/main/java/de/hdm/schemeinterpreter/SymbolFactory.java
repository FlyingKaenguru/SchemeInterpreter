package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.symbols.Symbol;

public class SymbolFactory {

    public static Symbol createVariable(String symbol, String value){
        return new Symbol() {
            @Override
            public String getSymbol() {
                return symbol;
            }

            @Override
            public ValidationResult<String[]> validateParams(String[] params) {
                final ValidationResult.Status status = params.length == 0
                        ? ValidationResult.Status.VALID
                        : ValidationResult.Status.INVALID;

                return new ValidationResult<>(params, status, "");
            }

            @Override
            public String getParamDefinition() {
                return null;
            }

            @Override
            public String eval(String... validatedParams) {
                return value;
            }
        };
    }

    public static Symbol createConstructReference(String symbol, String[] value){
        return new Symbol() {
            @Override
            public String getSymbol() {
                return symbol;
            }

            @Override
            public String getParamDefinition() {
                return null;
            }

            @Override
            public String eval(String... validatedParams) {
                return "(" + String.join(" . ", value) + ")";
            }
        };
    }
}
