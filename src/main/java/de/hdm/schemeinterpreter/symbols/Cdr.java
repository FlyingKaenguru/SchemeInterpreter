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
        final Matcher m = Pattern.compile(Validator.enclosed(".*(" + Type.any + " \\. " + Type.any + ")\\)")).matcher(validatedParams[0]);

        m.find();

        return m.group(1).replace(") . ", "");
    }

}
