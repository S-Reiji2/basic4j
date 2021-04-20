package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import static ep.Symbol.*;

public class StatementNode extends Node {
    Node body;
    
    public StatementNode(Environment env) {
        super(env);
    }
    
    @Override
    public void parse() throws Exception {
        if (see(END)) {
            body = new LexTypeWrapNode(END);
            return;
        }
        
        body = peek_handle(for_loop);
        if (body != null) return;
        
        
        if (peek().getType() == NAME) {
            if (peek2().getType() == EQ) {
                body = peek_handle(subst);
            } else if (peek2().getType() == LP) {
                body = peek_handle(call_func);
            }
        }
    }

    @Override
    public String toString() {
        return ("StatementNode\n" + body).replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        body.getValue();
        return null;
    }
}
