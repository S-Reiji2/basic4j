package nodes;

import main.Value;
import main.Environment;
import java.util.*;
import static main.LexicalType.*;
import static main.Symbol.*;

public class ElseIfNode extends Node {
    List<Node> subNodes = new ArrayList<>();

    public ElseIfNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        while (true) if (!see(NL)) break;
        
        while (see(ELSEIF)) {
            subNodes.add(new LexTypeWrapNode(ELSEIF));
            
            Node node = peek_handle(cond);
            if (node == null) return;
            subNodes.add(node);
            
            if (!see(THEN)) return;
            subNodes.add(new LexTypeWrapNode(THEN));
            
            if (!see(NL)) return;
            
            node = peek_handle(stmt_list);
            if (node == null) return;
            subNodes.add(node);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ElseIfNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        for (int i = 0; i < subNodes.size(); i++) {
            if (!(subNodes.get(i) instanceof CondNode)) continue;
            if (subNodes.get(i).getValue().getBValue()) {
                return subNodes.get(i + 2).getValue();
            }
        }
        
        return null;
    }
}
