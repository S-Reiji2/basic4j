package main;

import nodes.ExprListNode;

public abstract class Function {
    public abstract Value invoke(ExprListNode arg) throws Exception;
}
