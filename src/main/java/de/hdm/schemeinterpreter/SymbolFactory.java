package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.Validator.Type;
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

    public static Symbol createLambdaVariable(String symbol, String[] args, String body){
        return new Symbol() {
            @Override
            public String getSymbol() {
                return symbol;
            }

            @Override
            public String getParamDefinition() {
                return Validator.enclosed("(?:" + Type.any + " ){" + args.length + "}");
            }

            @Override
            public String eval(String... validatedParams) {
                String result = body;
                for (int i = 0; i < args.length; i++) {
                    result = result.replace(args[i], validatedParams[i]);
                }

                return result;
            }
        };
    }

}
