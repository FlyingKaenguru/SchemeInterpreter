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


//        final String rawCar = Main.findFirstParenthesesBlock(param);
//        final long parCount = param.chars().filter(e -> e == '(').count();
//
//        if (parCount > 1) {
//            var result = validatedParams[0].replace(rawCar, "");
//            final String uuid = SymbolManager.generateVarId();
//            SymbolManager.getInstance().addSymbol(SymbolFactory.createVariable(uuid, rawCar));
//
//            return uuid;
//        }else {
//            if (param.contains(".")) {
//                final Matcher m = Pattern.compile(Validator.enclosed("\\(" + Type.any + " . (" + Type.any + ")\\)")).matcher(param);
//                m.find();
//                return m.group(1);
//            } else {
//                return param.substring(2);
//            }
//        }

        final Matcher m = Pattern.compile("(\\(?" + Type.any + " \\. " + Type.any + ")\\)$").matcher(param);

        if (m.find()) {
            final String result = m.group(1);

            if (result.startsWith("(") || result.contains(") . ")) {
                return result.replaceAll("^" + Type.any + " \\. ", "");
            }

            if (result.contains(" . ")) {
                return "(" + result + ")";
            }
        }

        return param.substring(1);
    }
}
