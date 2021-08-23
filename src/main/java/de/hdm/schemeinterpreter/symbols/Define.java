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
        return Validator.enclosed(Validator.Type.schemeVar + " (?:[^\s]+ )+");
    }

    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final String[] resolvedParams = ParamUtils.resolveParams(params, 1);

        return ParamUtils.validateParams(getParamDefinition(), resolvedParams);
    }

    @Override
    public String eval(String... validatedParams) {
        final String value = validatedParams[1];

        SymbolManager.getInstance().addSymbol(SymbolManager.getInstance().createSymbol(validatedParams[0], validatedParams[1]));
        return "";
    }
}