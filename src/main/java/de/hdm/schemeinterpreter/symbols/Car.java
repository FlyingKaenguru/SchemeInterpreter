package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.Main;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.Validator.Type;
import de.hdm.schemeinterpreter.utils.ParamUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Car implements Symbol {
    @Override
    public String getSymbol() {
        return "car";
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
        final long parCount = param.chars().filter(e -> e == '(').count();

        // Using parentheses pair find algorithm
        final String rawCar = Main.findFirstParenthesesBlock(validatedParams[0]);

        if (parCount > 1) {
            return rawCar;
        }

        final Matcher m = Pattern.compile("\\((" + Type.any + ") .*\\)").matcher(rawCar);
        m.find();
        return m.group(1);
    }
}
