package ep;

import static ep.LexicalType.*;
import ep.nodes.*;
import java.lang.reflect.Constructor;
import java.util.*;

public enum Symbol {
    program(EnumSet.of(NAME, FOR, END, IF, DO), ProgramNode.class),
    stmt_list(EnumSet.of(NAME, FOR, END, IF, DO), StatementListNode.class),
    block(EnumSet.of(IF, DO), BlockNode.class),
    stmt(EnumSet.of(NAME, FOR, END), StatementNode.class),
    expr_list(EnumSet.of(SUB, LP, NAME, INTVAL, DOUBLEVAL, LITERAL),ExprListNode.class),
    if_prefix(EnumSet.of(IF), IfNode.class),
    else_block(EnumSet.of(ELSE), ElseBlockNode.class),
    else_if_prefix(EnumSet.of(ELSEIF), ElseIfNode.class),
    subst(EnumSet.of(NAME), AssignmentNode.class),
    cond(EnumSet.of( SUB, LP, NAME, INTVAL, DOUBLEVAL, LITERAL ), CondNode.class),
    expr(EnumSet.of(SUB, LP, NAME, INTVAL, DOUBLEVAL, LITERAL),ExpressionNode.class),
    var(EnumSet.of(NAME), VariableNode.class),
    leftvar(EnumSet.of(NAME), VariableNode.class),
    call_func(EnumSet.of(NAME), FunctionCallNode.class),
    do_loop(EnumSet.of(DO), DoNode.class),
    for_loop(EnumSet.of(FOR), ForNode.class),
    while_loop(EnumSet.of(WHILE), WhileNode.class),
    constant(EnumSet.of(INTVAL, DOUBLEVAL, LITERAL), ConstantNode.class),;
    
    Set<LexicalType> first;
    Class<? extends Node> handler;
    Constructor<? extends Node> constructor;

    Symbol(Set<LexicalType> first, Class<? extends Node> handler) {
        this.first = first;
        this.handler = handler;
        try {
            constructor = null;
            if (handler != null) {
                constructor = handler.getConstructor(Environment.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFirst(LexicalType type) {
        return first.contains(type);
    }

    public Node handle(Environment env) throws Exception {
        Node instance = (Node) constructor.newInstance(env);
        instance.parse();
        return instance;
    }
}
