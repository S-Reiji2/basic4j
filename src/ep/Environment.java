package ep;

import java.util.*;

public class Environment {
    private final LexicalAnalyzer input;
    private final Map<String, Function> funcLib;
    private final Map<String, Variable> varTable;

    public Environment(LexicalAnalyzer input) {
        this.input = input;
        funcLib = new HashMap<>();
        funcLib.put("PRINT", new PrintFunc());
        varTable = new HashMap<>();
    }

    public LexicalAnalyzer getInput() {
        return input;
    }

    public Function getFunction(String name) {
        return funcLib.get(name);
    }

    public Environment() {
        this.input = null;
        this.funcLib = null;
        this.varTable = null;
    }

    public Variable getVariable(String name) {
        Variable v = varTable.get(name);

        if (v == null) {
            v = new Variable(name);
            varTable.put(name, v);
        }

        return v;
    }
}
