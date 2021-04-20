package nodes;

import main.LexicalType;
import main.Value;
import main.Environment;
import java.util.*;
import static main.LexicalType.*;
import static main.Symbol.*;

public class IfNode extends Node {
    boolean isNotOneLine;
    List<Node> subNodes = new ArrayList<>();

    public IfNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        Node node;
        
        if (!see(IF)) return;
        subNodes.add(new LexTypeWrapNode(IF));
        
        node = peek_handle(cond);
        if (node == null) return;
        subNodes.add(node);
        
        if (!see(THEN)) return;
        subNodes.add(new LexTypeWrapNode(THEN));
        
        isNotOneLine = see(NL);
        
        if (isNotOneLine) {
            while (true) if (!see(NL)) break;
            
            node = peek_handle(stmt_list);
            if (node == null) return;
            subNodes.add(node);
            
            node = peek_handle(else_block);
            if (node == null) return;
            subNodes.add(node);
            
            see(LexicalType.ENDIF);
            subNodes.add(new LexTypeWrapNode(ENDIF));
        } else {
            node = peek_handle(stmt);
            if (node == null) return;
            subNodes.add(node);
            
            if (see(ELSE)) {
                subNodes.add(new LexTypeWrapNode(ELSE));

                node = peek_handle(stmt);
                if (node == null) return;
                subNodes.add(node);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IfNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        CondNode condition = null;
        
        for (Node subNode : subNodes) {
            if (subNode instanceof CondNode) condition = (CondNode) subNode;
            else if (subNode instanceof LexTypeWrapNode) {
                LexicalType lt = ((LexTypeWrapNode) subNode).getLexicalType();
                if (lt == ELSE) return doIfWithElse(condition);
                if (lt == ELSEIF) return doIfWithElseIf(condition);
            }
        }
        
        return doIf(condition);
    }
    
    private Value doIf(CondNode cond) throws Exception {
        if (cond.getValue().getBValue()) return subNodes.get(3).getValue();
        else return null;
    }
    
    private Value doIfWithElse(CondNode cond) throws Exception {
        if (cond.getValue().getBValue()) return subNodes.get(3).getValue();
        else return subNodes.get(5).getValue();
    }
    
    private Value doIfWithElseIf(CondNode cond) throws Exception {
        if (cond.getValue().getBValue()) return subNodes.get(3).getValue();
        else return subNodes.get(4).getValue();
    }
}
