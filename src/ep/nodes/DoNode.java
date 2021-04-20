package ep.nodes;

import ep.*;
import java.util.*;
import static ep.LexicalType.*;
import static ep.Symbol.*;

public class DoNode extends Node {
    List<Node> subNodes = new ArrayList<>();

    public DoNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Node node;
        
        if (!see(DO)) return;
        subNodes.add(new LexTypeWrapNode(DO));
        
        if (see(NL)) {
            while (true) if (!see(NL)) break;

            node = peek_handle(stmt_list);
            if (node == null) return;
            subNodes.add(node);
            
            if (!see(LOOP)) return;
            subNodes.add(new LexTypeWrapNode(LOOP));

            if (see(WHILE)) subNodes.add(new LexTypeWrapNode(WHILE));
            else if (see(UNTIL)) subNodes.add(new LexTypeWrapNode(UNTIL));
            else return;

            node = peek_handle(cond);
            if (node == null) return;
            subNodes.add(node);
        } else {
            if (see(WHILE)) subNodes.add(new LexTypeWrapNode(WHILE));
            else if (see(UNTIL)) subNodes.add(new LexTypeWrapNode(UNTIL));
            else return;

            node = peek_handle(cond);
            if (node == null) return;
            subNodes.add(node);
            
            if (!see(NL)) return;
            while (true) if (!see(NL)) break;
            
            node = peek_handle(stmt_list);
            if (node == null) return;
            subNodes.add(node);
            
            if (!see(LOOP)) return;
            subNodes.add(new LexTypeWrapNode(LOOP));
        }
        
        if (!see(NL)) return;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DoNode");
        
        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode.toString());
        }
        
        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        LexicalType loopType = null;
        CondNode condition = null;
        StatementListNode stmtList = null;
        
        for (Node subNode : subNodes) {
            if (subNode instanceof CondNode) {
                condition = (CondNode) subNode;
            } else if (subNode instanceof StatementListNode) {
                stmtList = (StatementListNode) subNode;
            } else if (subNode instanceof LexTypeWrapNode) {
                if (((LexTypeWrapNode) subNode).getLexicalType() == LOOP) continue;
                loopType = ((LexTypeWrapNode) subNode).getLexicalType();
            }
        }
        
        return doLoop(loopType, condition, stmtList);
    }
    
    private Value doLoop(LexicalType lt, CondNode cond, StatementListNode stmtList) throws Exception {
        if (lt == null || cond == null) return null;
        
        boolean isWhile = (lt == WHILE);
        
        while ((cond.getValue().getBValue() == isWhile)) {            
            stmtList.getValue();
        }
        
        return null;
    }
}
