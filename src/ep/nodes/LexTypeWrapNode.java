package ep.nodes;

import ep.LexicalType;
import ep.Value;

public class LexTypeWrapNode extends Node {
    LexicalType lt;

    public LexTypeWrapNode(LexicalType lt) {
        this.lt = lt;
    }

    @Override
    public String toString() {        
        return ("LexicalTypeNode | " + lt.toString()).replaceAll("\n", "\n  ");
    }

    public LexicalType getLexicalType() {
        return lt;
    }

    @Override
    public Value getValue() throws Exception {
        return null;
    }
}
