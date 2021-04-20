package ep.nodes;

import ep.*;
import static ep.LexicalType.EQ;
import java.util.*;

public class AssignmentNode extends Node {
    List<Node> subNodes = new ArrayList<>();
    
    public AssignmentNode(Environment env) {
        super(env);
    }
    
    @Override
    public void parse() throws Exception {
        Node node = null;

        if ((node = peek_handle(Symbol.leftvar)) != null) subNodes.add(node);
        else return;
        
        if (see(EQ)) subNodes.add(new LexTypeWrapNode(EQ));
        else return;
        
        if ((node = peek_handle(Symbol.expr)) != null) subNodes.add(node);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AssignmentNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        VariableNode vNode = (VariableNode) subNodes.get(0);
        Variable variable = env.getVariable(vNode.getName());
        variable.setValue(subNodes.get(2).getValue());
        return null;
    }
}
