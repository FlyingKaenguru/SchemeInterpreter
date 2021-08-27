package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolFactory;
import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.utils.ParamUtils;

public class List implements Symbol {
    @Override
    public String getSymbol() {
        return "list";
    }

    @Override
    public String getParamDefinition() {
        return "(?:" + Validator.Type.any + " )+";
    }

    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final ValidationResult<String[]> validationResult = ParamUtils.validateParams(getParamDefinition(), params);
        validationResult.validationSubject = ParamUtils.resolveAllParams(params);

        return validationResult;
    }

    @Override
    public String eval(String... validatedParams) {
        final String uuid = SymbolManager.generateVarId();
        final String value = "(" + String.join(" ", validatedParams) + ")";

        SymbolManager.getInstance().addSymbol(SymbolFactory.createVariable(uuid, value));

        return uuid;
    }
}
