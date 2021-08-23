package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.utils.ParamUtils;

public interface Symbol {

    String getSymbol();

    /**
     * @return A regex string defining how a parameter string has to be assembled to be valid.
     * The parameter string looks like "^(\s )*$". Notice the ever trailing whitespace!
     */
    String getParamDefinition();

    default ValidationResult<String[]> validateParams(String[] params) {
        final String[] resolvedParams = ParamUtils.resolveAllParams(params);

        return ParamUtils.validateParams(getParamDefinition(), resolvedParams);
    }

    //TODO: String param to list
    String eval(String... validatedParams);

}
