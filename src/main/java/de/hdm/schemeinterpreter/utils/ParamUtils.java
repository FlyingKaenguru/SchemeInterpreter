package de.hdm.schemeinterpreter.utils;

import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.ValidationResult;
import de.hdm.schemeinterpreter.Validator;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtils {

    public static String[] resolveAllParams(String[] params) {
        return Arrays.stream(params).map(e -> SymbolManager.getInstance().resolveVar(e)).toArray(String[]::new);
    }

    public static String[] resolveParams(String[] params, int... indexes) {
        final String[] resolvedParams = Arrays.copyOf(params, params.length);

        Arrays.stream(indexes).forEach(i -> resolvedParams[i] = SymbolManager.getInstance().resolveVar(params[i]));

        return resolvedParams;
    }

    public static ValidationResult<String[]> validateParams(String pattern, String[] params) {
        final String paramString = prepareForValidityCheck(params);
        ValidationResult.Status status =
                null == pattern && params.length <= 0 || null != pattern && Pattern.compile(pattern).matcher(paramString).matches()
                        ? ValidationResult.Status.VALID
                        : ValidationResult.Status.INVALID;

        return new ValidationResult<>(params, status, "");
    }

    public static String prepareForValidityCheck(String[] params) {
        return String.join(" ", params) + " ";
    }

    /**
     * eg. "Hello \"World\"" --> Hello "World"
     * eg. 'Hello \'you\'' --> Hello 'you'
     *
     * @param s
     * @return
     */
    public static String unwrapString(String s) {
        final Matcher m = Pattern.compile(Validator.enclosed(Validator.Type.string)).matcher(s);

        if (!m.find()) {
            return s;
        }

        if (null != m.group(1)) {
            return m.group(1).replace("\\\"", "\"");
        } else {
            return m.group(2).replace("\\'", "'");
        }
    }
}
