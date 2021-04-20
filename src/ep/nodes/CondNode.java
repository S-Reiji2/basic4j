package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import static ep.ValueType.*;
import java.util.*;

public class CondNode extends Node {
    List<Node> subNodes = new ArrayList<>();
    LexicalType compOpr = null;

    static LexicalType[] lexTypes = {
        EQ, GT, LT, GE, LE, NE
    };

    public CondNode(Environment env) {
        super(env);
    }
    
    @Override
    public void parse() throws Exception {
        Node node = null;
        
        if ((node = peek_handle(Symbol.expr)) != null) subNodes.add(node);
        else return;
        
        for (LexicalType lexType : lexTypes) {
            if (see(lexType)) {
                subNodes.add(new LexTypeWrapNode(lexType));
                compOpr = lexType;
                if ((node = peek_handle(Symbol.expr)) != null) subNodes.add(node);
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConditionNode");

        for (Node subNode : subNodes) {
            sb.append("\n");
            sb.append(subNode);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        ExpressionNode left = (ExpressionNode) subNodes.get(0);
        ExpressionNode right = (ExpressionNode) subNodes.get(subNodes.size() - 1);
        Value leftValue = left.getValue();
        Value rightValue = right.getValue();
        ValueType leftVt = leftValue.getType();
        ValueType rightVt = rightValue.getType();

        boolean isNumber = !(leftVt == STRING || leftVt == VOID)
                && !(rightVt == STRING || rightVt == VOID);
        
        if (compOpr == EQ) return new ValueImpl(leftValue.equals(rightValue));
        if (!isNumber) return null;

        double ld = leftValue.getDValue();
        double rd = rightValue.getDValue();
        
        switch (compOpr) {
            case GT:
                return new ValueImpl(ld > rd);
            case LT:
                return new ValueImpl(ld < rd);
            case GE:
                return new ValueImpl(ld >= rd);
            case LE:
                return new ValueImpl(ld <= rd);
            case NE:
                return new ValueImpl(ld != rd);
            default:
                return null;
        }
    }
}

