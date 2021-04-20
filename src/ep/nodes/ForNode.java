package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import static ep.Symbol.*;
import static ep.ValueType.*;
import java.util.*;

public class ForNode extends Node {
    List<Node> subNodes = new ArrayList<>();
    
    public ForNode(Environment env) {
        super(env);
    }

    @Override
    public void parse() throws Exception {
        if (!see(FOR)) return;
        subNodes.add(new LexTypeWrapNode(FOR));
        
        Node node = peek_handle(subst);
        if (node == null) return;
        subNodes.add(node);
        
        if (!see(TO)) return;
        subNodes.add(new LexTypeWrapNode(TO));

        node = peek_handle(constant);
        if (node == null) return;
        if (node.getValue().getType() != INTEGER) return;
        subNodes.add(node);
        
        if (!see(NL)) return;
        while (true) if(!see(NL)) break;
        
        node = peek_handle(stmt_list);
        if (node == null) return;
        subNodes.add(node);
        
        if (!see(NEXT)) return;
        while (true) if(!see(NEXT)) break;
        
        node = peek_handle(var);
        if (node == null) return;
        subNodes.add(node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ForNode");
        
        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode.toString());
        }
        
        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        AssignmentNode assign = null;
        StatementListNode stmtList = null;
        Integer loopLimit = null;
        Variable var = null;
        
        for (Node subNode : subNodes) {
            if (subNode instanceof AssignmentNode)
                assign = (AssignmentNode) subNode;
            else if (subNode instanceof StatementListNode)
                stmtList = (StatementListNode) subNode;
            else if (subNode instanceof ConstantNode)
                loopLimit = ((ConstantNode) subNode).getValue().getIValue();
            else if (subNode instanceof VariableNode)
                var = env.getVariable(((VariableNode) subNode).getName());
        }
        
        if (assign == null || stmtList == null
                || loopLimit == null || var == null) return null;
        
        assign.getValue();
        
        while (true) {            
            int i = var.getValue().getIValue();
            if (i > loopLimit) return null;
            stmtList.getValue();
            var.setValue(new ValueImpl(++i));
        }
    }
}
