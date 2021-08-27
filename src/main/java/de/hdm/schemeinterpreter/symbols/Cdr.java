package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.*;
import de.hdm.schemeinterpreter.Validator.Type;
import de.hdm.schemeinterpreter.utils.ParamUtils;
import java.util.ArrayList;
import java.util.List;
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


        final String rawCar = Main.findFirstParenthesesBlock(param);
        final long parCount = param.chars().filter(e -> e == '(').count();

        if (param.startsWith("((")) {
            final String escapedCar = escapeChar(rawCar, "()".toCharArray());
            var result = validatedParams[0].replaceFirst(escapedCar + " ?", "");

            if (result.chars().filter(c -> c == '.').count() == 1) { //cons
                final Matcher m = Pattern.compile("\\(\\. (" + Type.any + ")\\)").matcher(result);
                if (m.find()) {
                    return m.group(1);
                }
            }
            return this.saveAsSymbol(result);
        } else {
            if (param.contains(".")) { //cons
                final Matcher m = Pattern.compile("\\(.* . (" + Type.any + ")\\)").matcher(rawCar);
                m.find();
                return m.group(1);
            } else { //list
                final Matcher m = Pattern.compile(Type.string + "|" + Type.floatingPoint).matcher(param);
                final List<String> matches = new ArrayList<>();

                while (m.find()) {
                    matches.add(m.group());
                }
                if (matches.size() == 2) {
                    return matches.get(1);
                }
                return this.saveAsSymbol(param.replace(matches.get(0) + " ", ""));
            }
        }
    }

    private String saveAsSymbol(String s) {
        final String uuid = SymbolManager.generateVarId();
        SymbolManager.getInstance().addSymbol(SymbolFactory.createVariable(uuid, s));
        return uuid;
    }

    private String escapeChar(String s, char[] characters) {
        for (char c : characters) {
            s = s.replace("" + c, "\\" + c);
        }

        return s;
    }
}
