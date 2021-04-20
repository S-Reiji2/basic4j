package ep;

public class LexicalUnit {
    LexicalType t;
    Value v;

    public LexicalUnit(LexicalType t) {
        this.t = t;
    }

    public LexicalUnit(LexicalType t, Value v) {
        this.t = t;
        this.v = v;
    }

    public Value getValue() {
        return v;
    }

    public LexicalType getType() {
        return t;
    }

    @Override
    public String toString() {
        switch (t) {
            case LITERAL:
                return "LITERAL: " + v.getSValue();
            case NAME:
                return "NAME: " + v.getSValue();
            case DOUBLEVAL:
                return "DOUBLEVAL: " + v.getSValue();
            case INTVAL:
                return "INTVAL: " + v.getSValue();
            default:
                return t.toString();
        }
    }
}
