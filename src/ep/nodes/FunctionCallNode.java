package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import java.util.*;

public class FunctionCallNode extends Node {
    LexicalUnit lu;
    List<Node> subNodes = new ArrayList<>();
    
    public FunctionCallNode(Environment env) {
        super(env);
    }
    
    @Override
    public void parse() throws Exception {
        lu = get();
        if (lu == null) return;
        
        if (!see(LP)) return;
        subNodes.add(new LexTypeWrapNode(LP));
        
        Node node = peek_handle(Symbol.expr_list);
        if (node == null) return;
        subNodes.add(node);
        
        if (!see(RP)) return;
        subNodes.add(new LexTypeWrapNode(RP));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FunctionCallNode");
        sb.append("\n");
        sb.append("FunctionName | ");
        sb.append(lu.getValue().getSValue());
        
        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        if (lu.getType() != LexicalType.NAME) return null;
        
        Function func = env.getFunction(lu.getValue().getSValue());
        
        if (func == null) return null;
        
        for (Node subNode : subNodes) {
            if (!(subNode instanceof ExprListNode)) continue;
            return func.invoke((ExprListNode) subNode);
        }
        
        return null;
    }
}
