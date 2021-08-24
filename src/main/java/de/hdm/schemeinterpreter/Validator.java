package de.hdm.schemeinterpreter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static class Type {
        public static final String any = "[^\s]+";
        public static final String bool = "#(?:f|t)";
        public static final String floatingPoint = "[-+]?[0-9]+?(?:.[0-9]+)?(?:e\\^-?[0-9]+)?";
        public static final String function = "\\(\s*([^\s]+)((?:\s*[^\s]+)+)\s*\\)";
        public static final String schemeVar = "[a-zA-Z_]+[a-zA-Z0-9_]*";
        public static final String string = "\"((?:[^\"\\\\]|\\\\.)*)\"|'((?:[^'\\\\]|\\\\.)*)'";
    }

    public static String enclosed(String regex) {
        return "^" + regex + "$";
    }

    public static boolean isSchemeVar(String s) {
        return Pattern.compile(enclosed((Type.schemeVar))).matcher(s).matches();
    }

    public static boolean isInternalVar(String s) {
        return Pattern.compile(enclosed("\\$_[a-f-0-9-]+")).matcher(s).matches();
    }

    public static boolean isSchemeFunction(String s) {
        final Matcher m = Pattern.compile(enclosed(Validator.Type.function)).matcher(s.trim());

        if (m.find()) {
            return !Pattern.compile(enclosed(Type.floatingPoint)).matcher(m.group(1).trim()).matches()
                    && !m.group(1).startsWith("(") && !m.group(1).startsWith("\"") && !m.group(1).startsWith("'");
        }

        return false;
    }

    public static boolean containsSchemeFunction(String s) {
        final Matcher m = Pattern.compile(Type.function).matcher(s);

        while (m.find()) {
            if (isSchemeFunction(m.group())) {
                return true;
            }
        }

        return false;
    }
}
