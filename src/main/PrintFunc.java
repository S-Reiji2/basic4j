package main;

import nodes.ExprListNode;

public class PrintFunc extends Function {

    @Override
    public Value invoke(ExprListNode arg) throws Exception {
        Value value;
        StringBuilder sb = new StringBuilder();
        
        while ((value = arg.getValue()) != null) {            
            sb.append(value.getSValue());
        }
        
        arg.resetCursol();
        
        System.out.println(sb.toString());
        
        return null;
    }
}
