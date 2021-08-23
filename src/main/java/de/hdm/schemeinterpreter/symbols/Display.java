package de.hdm.schemeinterpreter.symbols;

import de.hdm.schemeinterpreter.SymbolManager;
import de.hdm.schemeinterpreter.Validator;
import de.hdm.schemeinterpreter.utils.ParamUtils;

import java.util.Arrays;

public class Display implements Symbol {

    public Display() {
    }

    @Override
    public String getSymbol() {
        return "display";
    }

    @Override
    public String getParamDefinition() {
        return Validator.enclosed("(?:" + Validator.Type.any + " )+");
    }

    @Override
    public String eval(String... validatedParams) {
        return String.join(" ", Arrays.stream(validatedParams)
                .map(e -> ParamUtils.unwrapString(SymbolManager.getInstance().resolveVar(e))).toArray(String[]::new));
    }
}
