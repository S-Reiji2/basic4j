package ep.nodes;

import ep.*;

public class VariableNode extends Node {
    private String name;
    
    public VariableNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        name = get().toString();
        env.getVariable(name);
    }

    @Override
    public String toString() {
        return ("VariableNode | " + name).replaceAll("\n", "\n  ");
    }

    public String getName() {
        return name;
    }

    @Override
    public Value getValue() throws Exception {
        return env.getVariable(name).getValue();
    }
}
