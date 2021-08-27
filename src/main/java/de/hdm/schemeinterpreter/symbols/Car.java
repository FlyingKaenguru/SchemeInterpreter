package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.*;
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
        final String rawCar = Main.findFirstParenthesesBlock(validatedParams[0]);

        if (parCount > 1) {
            final String uuid = SymbolManager.generateVarId();
            SymbolManager.getInstance().addSymbol(SymbolFactory.createVariable(uuid, rawCar));

            return uuid;
        }

        if (param.contains(".")) {
            final Matcher m = Pattern.compile("\\((" + Type.any + ") .*\\)").matcher(rawCar);
            m.find();
            return m.group(1);
        } else {
            return param.substring(1,2);
        }
    }
}