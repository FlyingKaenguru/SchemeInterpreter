package de.hdm.schemeinterpreter;

import de.hdm.schemeinterpreter.exception.IllegalParameterException;
import de.hdm.schemeinterpreter.exception.NoValidFunctionException;
import de.hdm.schemeinterpreter.exception.NotImplementedException;
import de.hdm.schemeinterpreter.exception.SyntaxErrorException;
import de.hdm.schemeinterpreter.symbols.*;
import de.hdm.schemeinterpreter.symbols.Set;

import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main {
    static SymbolManager symbolManager = SymbolManager.getInstance();

    public static void main(String[] args) {
        try {
            collectSymbols();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        readEvalPrintLoop();
    }

    /**
     * Automatic loading of all symbol instances
     * NOT WORKING AFTER COMPILING ... ONLY IN IDE - TODO
     */
    public static void collectSymbols() {
        List<Symbol> symbols = Arrays.asList(
                new Addition(),
                new Car(),
                new Cdr(),
                new Construct(),
                new Define(),
                new Display(),
                new Division(),
                new Greater(),
                new GreaterEquals(),
                new IfStatement(),
                new Lambda(),
                new de.hdm.schemeinterpreter.symbols.List(),
                new Lower(),
                new LowerEquals(),
                new Multiplication(),
                new NumericEqual(),
                new NumericNotEqual(),
                new Set(),
                new StringEqual(),
                new Subtraction()
                );
        symbolManager.addSymbols(symbols);

/*
        final List<Symbol> symbols = Arrays.stream(ClassFinder.getImplementations(Symbol.class, "de.hdm.schemeinterpreter.symbols"))
                .map(ClassFinder::getClassInstance)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        symbolManager.addSymbols(symbols);

        System.out.println(symbols.size());

        for (Symbol symbol : symbols) {
            System.out.println(symbol.getClass().getName());
        }
*/
    }

    public static void readEvalPrintLoop() {
        final Scanner console = new Scanner(System.in);

        String input;
        do {
            System.out.print("> ");
            input = console.nextLine().trim();

            try {
                if (input.equals("(exit)")) {
                    break;
                } else if (!input.startsWith("(")) {
                    throw new SyntaxErrorException("Syntax error Input does not have opening parenthesis");
                } else if (!isParenthesesValid(input)) {
                    throw new SyntaxErrorException("Syntax error number of closing parentheses do not match number of opening parentheses.");
                } else {
                    //'( --> (list
                    input = replaceSchemeShorts(input);
                    // "(h" -> $_*
                    input = replaceStrings(input);
                    // remove whitespaces
                    input = input.replaceAll("\s{2,}", " ");

                    //Input --> Result
                    final String result = parseInputString(input);
                    if (result.length() > 0) {
                        System.out.println(result);
                    }

                }
            } catch (SyntaxErrorException | NoValidFunctionException | NotImplementedException e) {
                System.out.println(e.getMessage());
            }

        } while (!input.equals("(exit)"));
    }

    public static boolean isParenthesesValid(String s) {
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

    /**
     * Processes all inner functions and replaces the original string with their result until all functions have been resolved.
     */
    public static String parseInputString(String s) throws NoValidFunctionException, NotImplementedException {
        do {
            SchemeFunction function;

            if (s.contains("lambda")) {
                function = stringToLambdaFunction(findLambdaFunctionBlock(s));
            } else {
                function = stringToSchemeFunction(findFirstParenthesesBlock(s));
            }

            if (null == function) {
                throw new NoValidFunctionException();
            }

            var resultInnerString = parseSchemeFunction(function);

            s = s.replace(function.original, resultInnerString);
        } while (Validator.containsSchemeFunction(s));

        return s.stripTrailing();
    }

    /**
     * Returns first lambda-block from input string
     */
    public static String findLambdaFunctionBlock(String s) {
        final int lambdaIndex = (s.contains("(lambda")) ? s.indexOf("(lambda") : s.indexOf("( lambda");
        int pars = 0;

        for (int i = lambdaIndex; i < s.length(); i++) {
            final char c = s.charAt(i);

            if (c == '(') {
                pars++;
            } else if (c == ')') {
                pars--;
            }

            if (pars == 0) {
                return s.substring(lambdaIndex, i + 1);
            }
        }

        return "";
    }

    /**
     * Search inner function
     * eg (+ (+ 6 7) 9) --> (+ 6 7)
     */
    public static String findFirstParenthesesBlock(String s) {
        int openParIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            final char c = s.charAt(i);
            if (c == '(') {
                if (isNullDescriptor(s, i)) {
                    i++;
                } else {
                    openParIndex = i;
                }
            } else if (c == ')' && openParIndex >= 0) {
                return s.substring(openParIndex, i + 1);
            }
        }

        return "";
    }

    private static boolean isNullDescriptor(String s, int i) {
        return i + 1 < s.length() && s.charAt(i + 1) == ')';
    }

    /**
     * Checks if symbol is implements, calls executionSchemeFunction
     */
    private static String parseSchemeFunction(SchemeFunction function) throws NotImplementedException {
        Optional<Symbol> symbol = symbolManager.getSymbol(function.symbol);
        if (symbol.isPresent()) {
            return executeSchemeFunction(symbol.get(), function.params);
        } else {
            throw new NotImplementedException("'" + function.symbol + "' not implemented");
        }
    }

    /**
     * Transfers string to lambda function
     */
    private static SchemeFunction stringToSchemeFunction(String s) {
        final String[] groups = extractSchemeFunctionParts(s);

        //(+ 7 8) --> ["(+ 7 8)", "+", "7 8"]
        if (groups.length == 3 && Validator.isSchemeFunction(s)) {
            String[] params = new String[]{};

            if (null != groups[2]) {
                params = extractParams(groups[2].trim());
            }

            return new SchemeFunction(groups[0], groups[1], params);
        }

        return null;
    }

    /**
     * Transfers lambda-block to lambda function
     */
    private static SchemeFunction stringToLambdaFunction(String s) {
        final String[] groups = extractSchemeFunctionParts(s);

        if (groups.length == 3 && Validator.isSchemeFunction(s)) {
            final Matcher m = Pattern.compile("\\(([^()]+)\\) (\\(?.+\\)?)").matcher(groups[2].trim());

            if (m.find()) {
                return new SchemeFunction(groups[0], groups[1], new String[]{m.group(1), m.group(2)});
            }
        }

        return null;
    }

    /**
     * Returns groups
     * eg. groups (+ 7 8) --> [(+ 7 8), + , 7 8]
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
     */
    private static String[] extractParams(String s) {
        final Matcher m = Pattern.compile(Validator.Type.string + "|[^\s]+").matcher(s);
        final List<String> matches = new ArrayList<>();

        while (m.find()) {
            matches.add(m.group());
        }

        return matches.toArray(String[]::new);
    }

    /**
     * If params for Symbol are valid Symbol eval is called
     */
    private static String executeSchemeFunction(Symbol symbol, String[] params) throws IllegalParameterException {
        final ValidationResult<String[]> validationResult = symbol.validateParams(params);

        if (validationResult.status == ValidationResult.Status.INVALID) {
            throw new IllegalParameterException();
        }

        return symbol.eval(validationResult.validationSubject);
    }

    /**
     * '(6 5) --> (list 6 5)
     */
    private static String replaceSchemeShorts(String s) {
        return s.replace("'(", "(list ");
    }

    /**
     * Strings saved as Symbole --> UUID (Symbole) returned
     */
    private static String replaceStrings(String s) {
        final Matcher m = Pattern.compile(Validator.Type.string).matcher(s);
        final List<String> matches = new ArrayList<>();

        while (m.find()) {
            matches.add(m.group());
        }

        String result = s;
        if (matches.size() >= 1) {
            for (String match : matches) {
                final String uuid = SymbolManager.generateVarId();
                SymbolManager.getInstance().addSymbol(SymbolFactory.createVariable(uuid, match));

                result = (s.replace(match, uuid));
            }
            return result;
        }
        return s;
    }
}
