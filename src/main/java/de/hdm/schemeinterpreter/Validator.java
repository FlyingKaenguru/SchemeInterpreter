package de.hdm.schemeinterpreter;

import java.util.regex.Pattern;

public class Validator {

    public static class Type {
        public static final String any = "[^\s]+";
        public static final String bool = "#(?:f|t)";
        public static final String floatingPoint = "-?[0-9]+?(?:.[0-9]+)?(?:e\\^-?[0-9]+)?";
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

}
