package ep;

import ep.nodes.ExprListNode;

public abstract class Function {
    public abstract Value invoke(ExprListNode arg) throws Exception;
}
