package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolFactory;
import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.utils.ParamUtils;

public class Lambda implements Symbol {
    @Override
    public String getSymbol() {
        return "lambda";
    }

    @Override
    public String getParamDefinition() {
        return ".*";
    }

    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final ValidationResult<String[]> validationResult = ParamUtils.validateParams(getParamDefinition(), params);

        return validationResult;
    }

    @Override
    public String eval(String... validatedParams) {
        final String[] lambdaParams = validatedParams[0].split(" ");
        final String lambdaBody = validatedParams[1];
        final String uuid = SymbolManager.generateVarId();

        SymbolManager.getInstance().addSymbol(SymbolFactory.createLambdaVariable(uuid, lambdaParams, lambdaBody));

        return uuid;
    }
}
