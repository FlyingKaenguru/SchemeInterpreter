package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolFactory;
import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.utils.ParamUtils;

import java.util.UUID;

public class Construct implements Symbol{
    @Override
    public String getSymbol() {
        return "cons";
    }

    @Override
    public String getParamDefinition() {
        return "(?:" + Validator.Type.any + " ){2}";
    }

    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final ValidationResult<String[]> validationResult = ParamUtils.validateParams(getParamDefinition(), params);
        validationResult.validationSubject = ParamUtils.resolveAllParams(params);

        return validationResult;
    }

    @Override
    public String eval(String... validatedParams) {
        final String uuid = "$_" + UUID.randomUUID().toString();
        SymbolManager.getInstance().addSymbol(SymbolFactory.createConstructReference(uuid, validatedParams));

        return uuid;
    }
}
