package nodes;

import main.Environment;
import java.util.*;
import static main.LexicalType.NL;
import static main.Symbol.*;
import main.Value;

public class StatementListNode extends Node {
    List<Node> subNodes = new ArrayList<>();

    public StatementListNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Node node = peek_handle(stmt);
        if (node == null) node = peek_handle(block);
        if (node == null) return;
        subNodes.add(node);
        
        while (true) {
            if (see(NL)) {
                while (true) if (!see(NL)) break;
                
                node = peek_handle(stmt);
                if (node != null) subNodes.add(node);
            } else if ((node = peek_handle(block)) != null) {
                subNodes.add(node);
            } else break;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("StatementListNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        for (Node subNode : subNodes) subNode.getValue();
        return null;
    }
}
