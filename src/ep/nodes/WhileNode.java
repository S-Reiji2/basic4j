package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import static ep.Symbol.*;
import java.util.*;

public class WhileNode extends Node {
    List<Node> subNodes = new ArrayList<>();

    public WhileNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        if (!see(WHILE)) return;
        subNodes.add(new LexTypeWrapNode(WHILE));
        
        Node node = peek_handle(cond);
        if (node == null) return;
        subNodes.add(node);
            
        if (!see(NL)) return;
        while (true) if (!see(NL)) break;
        
        node = peek_handle(stmt_list);
        if (node == null) return;
        subNodes.add(node);
        
        if (!see(WEND)) return;
        subNodes.add(new LexTypeWrapNode(WEND));
        
        if (!see(NL)) return;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("WhileNode");
        
        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode.toString());
        }
        
        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        CondNode condition = null;
        StatementListNode stmtList = null;
        
        for (Node subNode : subNodes) {
            if (subNode instanceof CondNode)
                condition = (CondNode) subNode;
            else if (subNode instanceof StatementListNode) 
                stmtList = (StatementListNode) subNode;
        }
        
        if (condition == null || stmtList == null) return null;
        
        while (condition.getValue().getBValue()) stmtList.getValue();
        
        return null;
    }
}
