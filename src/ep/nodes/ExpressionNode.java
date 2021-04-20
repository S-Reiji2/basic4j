package ep.nodes;

import ep.*;
import static ep.LexicalType.*;
import static ep.Symbol.*;
import static ep.ValueType.*;
import java.util.*;

public class ExpressionNode extends Node {
    List<Node> rpnBuff = new ArrayList<>();
    List<LexTypeWrapNode> queue = new ArrayList<>();
    
    static final LexTypeWrapNode[] OPERATORS = {
        new LexTypeWrapNode(ADD),
        new LexTypeWrapNode(SUB),
        new LexTypeWrapNode(MUL),
        new LexTypeWrapNode(DIV)
    };
    
    static final LexTypeWrapNode LP_NODE = new LexTypeWrapNode(LP);
    static final LexTypeWrapNode RP_NODE = new LexTypeWrapNode(RP);
    
    static final Node ZERO = 
            new ConstantNode(
                    new LexicalUnit(INTVAL, new ValueImpl(0)));

    public ExpressionNode(Environment env) {
        super(env);
    }
    
    @Override
    public void parse() throws Exception {
        Node node = peek_handle(constant);
        
        if (node != null) {
            rpnBuff.add(node);
        }
        
        while (true) {
            if (isNextValue()) continue;

            LexicalType lt = peek().getType();
            
            if (lt == RP) {
                if (popBraketArea()) {
                    see(RP);
                    continue;
                }
            } else if (lt == LP) {
                see(LP);
                queue.add(LP_NODE);
                
                if (see(SUB)) {
                    rpnBuff.add(ZERO);
                    queue.add(OPERATORS[1]);
                }

                continue;
            }

            LexTypeWrapNode ltNode = getNextOperand();
            if (ltNode == null) break;
            addQueue(ltNode);
        }
        
        if (queue.isEmpty()) return;
        
        for (int i = queue.size() - 1; i >= 0; i--) rpnBuff.add(queue.get(i));
        
        queue.clear();
    }
    
    private boolean popBraketArea() {
        int LastLpIndex = -1;
        
        for (int i = queue.size() - 1; i >= 0; i--) {
            if (queue.get(i) == LP_NODE) {
                LastLpIndex = i;
                queue.remove(i);
                break;
            }
        }
        
        if (LastLpIndex == -1) return false;
        
        for (int i = LastLpIndex; i < queue.size(); i++) {
            rpnBuff.add(queue.get(i));
            queue.remove(i);
        }
        
        return true;
    }
    
    private void addQueue(LexTypeWrapNode ltNode) {
        while (true) {
            if (queue.isEmpty()) {
                queue.add(ltNode);
                break;
            }
            
            LexTypeWrapNode queueTop = queue.get(queue.size() - 1);
            
            if (getRank(queueTop) > getRank(ltNode)) {
                rpnBuff.add(queueTop);
                queue.remove(queue.size() - 1);
            } else {
                queue.add(ltNode);
                break;
            }
        }
    }
    
    private int getRank(LexTypeWrapNode ltNode) {
        for (int i = 0; i < OPERATORS.length; i++)
            if (OPERATORS[i] == ltNode)
                return i % 2;
        return -1;
    }
    
    private boolean isNextValue() throws Exception {
        Node n;
        
        if (peek().getType() == NAME) {
            if (peek2().getType() == LP) n = peek_handle(Symbol.call_func);
            else n = peek_handle(Symbol.var);
        } else n = peek_handle(Symbol.constant);
        
        if (n != null) {
            rpnBuff.add(n);
            return true;
        }
        
        else return false;
    }
    
    private LexTypeWrapNode getNextOperand() throws Exception {
        for (LexTypeWrapNode ltNode : OPERATORS) 
            if (see(ltNode.getLexicalType())) return ltNode;
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ExpressionNode");

        for (Node node : rpnBuff) {
            sb.append("\n");
            sb.append(node);
        }

        return sb.toString().replaceAll("\n", "\n  ");
    }

    @Override
    public Value getValue() throws Exception {
        List<Value> list = new ArrayList<>();
        
        for (Node subNode : rpnBuff) {
            if (subNode instanceof LexTypeWrapNode) {
                Value right = list.get(list.size() - 1);
                list.remove(right);
                
                Value left = list.get(list.size() - 1);
                list.remove(left);
                
                LexTypeWrapNode ltNode = (LexTypeWrapNode) subNode;
                list.add(calcFormula(left, right, ltNode.getLexicalType()));
            } else {
                list.add(subNode.getValue());
            }
        }
        
        if (list.isEmpty()) return ZERO.getValue();
        return list.get(0);
    }
    
    private Value calcFormula(Value left, Value right, LexicalType lt) {
        ValueType outVt;
        ValueType lvt = left.getType();
        ValueType rvt = right.getType();
        
        if (lvt == rvt) outVt = lvt;
        else if ((lvt == ValueType.INTEGER && rvt == ValueType.DOUBLE)
            || (lvt == ValueType.DOUBLE && rvt == ValueType.INTEGER)) {
            outVt = ValueType.DOUBLE;
        }
        else outVt = ValueType.STRING;
        
        if (null != lt) switch (lt) {
            case ADD:
                if (outVt == INTEGER) return new ValueImpl(left.getIValue() + right.getIValue());
                if (outVt == DOUBLE) return new ValueImpl(left.getDValue() + right.getDValue());
                if (outVt == STRING) return new ValueImpl(left.getSValue() + right.getSValue());
                break;
            case SUB:
                if (outVt == INTEGER) return new ValueImpl(left.getIValue() - right.getIValue());
                if (outVt == DOUBLE) return new ValueImpl(left.getDValue() - right.getDValue());
                break;
            case MUL:
                if (outVt == INTEGER) return new ValueImpl(left.getIValue() * right.getIValue());
                if (outVt == DOUBLE) return new ValueImpl(left.getDValue() * right.getDValue());
                break;
            case DIV:
                if (outVt == INTEGER) return new ValueImpl(left.getIValue() / right.getIValue());
                if (outVt == DOUBLE) return new ValueImpl(left.getDValue() / right.getDValue());
                break;
            default:
                break;
        }
        
        return null;
    }
}
