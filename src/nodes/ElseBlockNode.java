package nodes;

import main.Value;
import main.Environment;
import static main.LexicalType.*;
import static main.Symbol.*;
import java.util.*;

public class ElseBlockNode extends Node {
    List<Node> subNodes = new ArrayList<>();

    public ElseBlockNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        while (true) if (!see(NL)) break;
        
        Node node;
        
        if (!see(ELSE)) {
            node = peek_handle(else_if_prefix);
            if (node == null) return;
            subNodes.add(node);
            if (!see(ELSE)) return;
        }
        
        subNodes.add(new LexTypeWrapNode(ELSE));
        
        if (!see(NL)) return;
        while (true) if (!see(NL)) break;
        
        node = peek_handle(stmt_list);
        if (node == null) return;
        subNodes.add(node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ElseBlockNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        if (subNodes.get(0) instanceof ElseIfNode) {
            Value v = subNodes.get(0).getValue();
            if (v != null) return v;
        }
        
        for (Node subNode : subNodes) {
            if (subNode instanceof StatementListNode) {
                return subNode.getValue();
            }
        }
        
        return null;
    }
}
