package nodes;

import main.Value;
import main.Environment;
import static main.LexicalType.COMMA;
import static main.Symbol.expr;
import java.util.*;

public class ExprListNode extends Node {
    List<Node> subNodes = new ArrayList<>();
    int readCursol = 0;
    
    public ExprListNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Node node = null;
        
        while (true) {
            node = peek_handle(expr);
            
            if (node != null) {
                subNodes.add(node);
                
                if (see(COMMA)) {
                    subNodes.add(new LexTypeWrapNode(COMMA));
                    continue;
                }
            }
            
            break;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExpressionListNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }
        
        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        if (readCursol >= subNodes.size()) return null;
        int cursol = readCursol;
        readCursol += 2;
        return subNodes.get(cursol).getValue();
    }
    
    public void resetCursol() {readCursol = 0;}
}
