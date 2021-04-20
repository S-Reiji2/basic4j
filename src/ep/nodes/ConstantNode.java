package ep.nodes;

import ep.Environment;
import ep.LexicalUnit;
import ep.Value;

public class ConstantNode extends Node {
    LexicalUnit value;

    public ConstantNode(LexicalUnit value) {
        this.value = value;
    }
    
    public ConstantNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        value = get();
    }

    @Override
    public String toString() {
        return ("ConstantNode | " + value).replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        return value.getValue();
    }
}
