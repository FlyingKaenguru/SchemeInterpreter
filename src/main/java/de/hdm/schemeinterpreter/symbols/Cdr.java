package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.Validator.Type;
import de.hdm.schemeinterpreter.utils.ParamUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cdr implements Symbol {
    @Override
    public String getSymbol() {
        return "cdr";
    }

    public String getParamDefinition() {
        return "(?:" + Validator.Type.any + " ){1}";
    }


    @Override
    public ValidationResult<String[]> validateParams(String[] params) {
        final ValidationResult<String[]> validationResult = ParamUtils.validateParams(getParamDefinition(), params);
        validationResult.validationSubject = ParamUtils.resolveAllParams(params);

        return validationResult;
    }

    @Override
    public String eval(String... validatedParams) {
        final String param = validatedParams[0];
        final Matcher m = Pattern.compile("(\\(?" + Type.any + " \\. " + Type.any + ")\\)$").matcher(param);

        m.find();
        final String result = m.group(1);

        if (result.startsWith("(") || result.contains(") . ")) {
            return result.replaceAll("^" + Type.any + " \\. ", "");
        }

        if (result.contains(" . ")) {
            return "(" + result + ")";
        }

        return result;
    }
}