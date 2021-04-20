package ep.nodes;

import ep.*;
import static ep.Symbol.*;

public class BlockNode extends Node {
    Node body;
    
    static Symbol[] firstSym = {
        if_prefix,
        while_loop,
        do_loop
    };

    public BlockNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Node node;
        
        for (Symbol symbol : firstSym) {
            node = peek_handle(symbol);

            if (node != null) {
                body = node;
                break;
            }
        }
    }

    @Override
    public String toString() {
        return ("BlockNode\n" + body).replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        body.getValue();
        return null;
    }
}
