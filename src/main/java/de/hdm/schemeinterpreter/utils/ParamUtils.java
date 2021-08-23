package de.hdm.schemeinterpreter.utils;

import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;

import java.util.Arrays;
import java.util.regex.Pattern;

public class ParamUtils {

    public static String[] resolveAllParams(String[] params) {
        return Arrays.stream(params).map(e -> SymbolManager.getInstance().resolveVar(e)).toArray(String[]::new);
    }

    public static String[] resolveParams(String[] params, int... indexes) {
        final String[] resolvedParams = Arrays.copyOf(params, params.length);

        Arrays.stream(indexes).forEach(i -> {
            resolvedParams[i] = SymbolManager.getInstance().resolveVar(params[i]);
        });

        return resolvedParams;
    }

    public static ValidationResult<String[]> validateParams(String pattern, String[] params) {
        final String paramString = prepareRegexableParamString(params);
        ValidationResult.Status status =
                null == pattern && null == params || Pattern.compile(pattern).matcher(paramString).matches()
                        ? ValidationResult.Status.VALID
                        : ValidationResult.Status.INVALID;

        return new ValidationResult<>(params, status, "");
    }

    public static String prepareRegexableParamString(String[] params) {
        return String.join(" ", params) + " ";
    }

}
