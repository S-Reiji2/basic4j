package ep.nodes;

import ep.Environment;
import ep.Symbol;
import ep.Value;

public class ProgramNode extends Node {
    Node body;

    public ProgramNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        body = Symbol.stmt_list.handle(env);
    }

    @Override
    public String toString() {
        return ("ProgramNode\n" + body.toString()).replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        return body.getValue();
    }
}
