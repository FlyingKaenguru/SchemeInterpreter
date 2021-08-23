package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.symbols.Symbol;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static SymbolManager symbolManager = SymbolManager.getInstance();

    public static void main(String[] args) throws Exception {
        symbolManager.addSymbols(Arrays.stream(ClassFinder.getImplementations(Symbol.class, "de.hdm.schemeinterpreter.symbols"))
                .map(ClassFinder::getClassInstance)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        readEvalPrintLoop();
    }

    public static void readEvalPrintLoop() {
        final Scanner console = new Scanner(System.in);

        String input;
        do {
            System.out.print("> ");
            input = console.nextLine().trim();

            if (!isBracingValid(input)) {
                System.out.println("Syntax error number of closing braces do not match number of opening braces.");
                continue;
            }

            //Input --> Result
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

            SchemeFunction function = stringToSchemeFunction(schemeFunction);
            String value = parseSchemeFunction(function);

            if (value == null) {
                return "NOT VALID";
            } else {
                s = s.replace(function.original, value);
            }
        } while (Pattern.compile(Validator.Type.function).matcher(s).find());
        return s;
    }

    /**
     * Search inner function
     * eg (+ (+ 6 7) 9) --> (+ 6 7)
     * @param
     * @return
     */
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

    /**
     * Checks if symbol is implements, calls executionSchemeFunction
     * @param function SchemeFunction
     * @return eval result
     */
    private static String parseSchemeFunction(SchemeFunction function) {
        Optional<Symbol> symbol = symbolManager.getSymbol(function.symbol);
        if (symbol.isPresent()) {
            return executeSchemeFunction(symbol.get(), function.params);
        } else {
            System.out.printf("'%s' not implemented\n", function.symbol);
            return null;
        }
    }


    private static SchemeFunction stringToSchemeFunction(String s) {
        final String[] groups = extractSchemeFunctionParts(s);

        //(+ 7 8) --> [(+ 7 8), + , 7 8]
        if (groups.length == 3) {
            final String[] params = extractParams(groups[2].trim());

            return new SchemeFunction(groups[0], groups[1], params);
        }

        return null;
    }

    /**
     * Returns groups
     * eg. groups (+ 7 8) --> [(+ 7 8), + , 7 8]
     * @param s
     * @return String Array with found match groups
     */
    private static String[] extractSchemeFunctionParts(String s) {
        final Matcher m = Pattern.compile(Validator.Type.function).matcher(s);

        if (m.find()) {
            return IntStream.range(0, m.groupCount() + 1).boxed().map(m::group).toArray(String[]::new);
        }

        return new String[]{};
    }

    /**
     * Returns matches and removes all whitespaces outside ""
     * eg. "7 "Hello    World"       9"  -> [7, "Hello    World", 9]
     * @param s
     * @return String Array
     */
    private static String[] extractParams(String s) {
        final Matcher m = Pattern.compile(Validator.Type.string + "|[^\s]+").matcher(s);
        final List<String> matches = new ArrayList<>();

        while(m.find()) {
            matches.add(m.group());
        }

        return matches.toArray(String[]::new);
    }

    private static String executeSchemeFunction(Symbol symbol, String[] params) {
        final ValidationResult<String[]> validationResult = symbol.validateParams(params);

        if (validationResult.status == ValidationResult.Status.INVALID) {
            throw new IllegalArgumentException(validationResult.message);
        }

        return symbol.eval(params);
    }
}
