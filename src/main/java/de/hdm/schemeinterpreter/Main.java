package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.symbols.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static List<Symbol> symbols = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        symbols.addAll(Arrays.asList(new Addition(), new Subtraction(), new Multiplication(), new Display(), new Division(),
                new Define((key, value) -> {
                    // TODO: Check for duplicates
                    symbols.add(new Symbol() {
                        @Override
                        public String getSymbol() {
                            return key;
                        }

                        @Override
                        public String eval(String... params) {
                            return value;
                        }
                    });
                })
        ));

        readEvalPrintLoop();
    }

    public static void readEvalPrintLoop() {
        final Scanner console = new Scanner(System.in);

        String input;
        do {
            System.out.print("> ");
            input = console.nextLine().trim();
            //final String input = "(+ 8 (/ (/ 8 1) (/ 2 3 7) ))";
            //final String input = "(define a ())";


            if (input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
                System.out.println(input);
                continue;
            }

            if (!isBracingValid(input)) {
                // TODO: throw exception
                continue;
            }
            System.out.println(parseInputString(input));
        } while (!input.equals("exit"));
    }

    public static boolean isBracingValid(String s) {
        int sum = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                sum++;
            } else if (c == ')') {
                sum--;
            }
        }

        return sum == 0;
    }

    public static String parseInputString(String s) {
        do {
            final String schemeFunction = findSchemeFunction(s);
            System.out.println(s);
            System.out.println(schemeFunction);

            SchemeFunction function = stringToSchemeFunction(schemeFunction);
            String value = parseSchemeFunction(function);

            if (value == null) {
                return "NOT VALID";
            } else {
                System.out.println(value);
                //TODO: return maybe null
                s = s.replace(function.original, value);
            }
        } while (s.contains("("));
        return s;
    }

    private static String findSchemeFunction(String s) {
        int openBraceIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c == '(') {
                if (isNullDescriptor(s, i)) {
                    i++;
                } else {
                    openBraceIndex = i;
                }
            } else if (c == ')' && openBraceIndex >= 0) {
                return s.substring(openBraceIndex, i + 1);
            }
        }

        return "";
    }

    private static boolean isNullDescriptor(String s, int i) {
        return i + 1 < s.length() && s.charAt(i + 1) == ')';
    }

    private static String parseSchemeFunction(SchemeFunction function) {
        Optional<Symbol> symbol = symbols.stream().filter(f -> f.getSymbol().equals(function.symbol)).findFirst();
        if (symbol.isPresent()) {
            return symbol.get().eval(function.params);
        } else {
            System.out.printf("'%s' not implemented\n", function.symbol);
            return null;
        }
    }

    private static SchemeFunction stringToSchemeFunction(String stringToBeValidate) {
        // TODO: Alter Regex to allow multiple params (more than two)
        final String pattern = "\\( ?([^\s]+)((?: [^\s]+)+) ?\\)";
        final Matcher m = Pattern.compile(pattern).matcher(stringToBeValidate);

        if (m.find()) {
            String[] params = m.group(2).trim().split(" ");

            for (int i = 0; i < params.length; i++) {
                params[i] = resolveVar(params[i]);
            }

            return new SchemeFunction(m.group(0), m.group(1), params);
        }

        return null;
    }

    private static String resolveVar(String param) {
        Optional<Symbol> symbol = symbols.stream().filter(f -> f.getSymbol().equals(param)).findFirst();
        if (symbol.isPresent()) {
            return symbol.get().eval(param);
        }
        return param;
    }
}
