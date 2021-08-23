package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.utils.ParamUtils;

public class Define implements Symbol {

    @Override
    public String getSymbol() {
        return "define";
    }

    @Override
    public String getParamDefinition() {
        // TODO: Define second argument more properly.
        return "^" + Validator.Type.schemeVar + " (?:[^\s]+ )+$";
    }

    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final String[] resolvedParams = ParamUtils.resolveParams(params, 1);

        return ParamUtils.validateParams(getParamDefinition(), resolvedParams);
    }

    @Override
    public String eval(String... validatedParams) {
        final String value = validatedParams[1];

        SymbolManager.getInstance().addSymbol(new Symbol() {
            @Override
            public String getSymbol() {
                return validatedParams[0];
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
        });
        return "";
    }
}