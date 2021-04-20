package ep;

public class ValueImpl extends Value {
    private final ValueType t;
    private final String v;

    public ValueImpl(String s) {
        super(s);
        t = ValueType.STRING;
        v = s;
    }

    public ValueImpl(int i) {
        super(i);
        t = ValueType.INTEGER;
        v = String.valueOf(i);
    }

    public ValueImpl(double d) {
        super(d);
        t = ValueType.DOUBLE;
        v = String.valueOf(d);
    }

    public ValueImpl(boolean b) {
        super(b);
        t = ValueType.BOOL;
        v = String.valueOf(b);
    }

    public ValueImpl(String s, ValueType t) {
        super(s, t);
        this.t = t;
        v = s;
    }

    @Override
    public String getSValue() {
        return v;
    }

    @Override
    public int getIValue() {
        return Integer.parseInt(v);
    }

    @Override
    public double getDValue() {
        return Double.parseDouble(v);
    }

    @Override
    public boolean getBValue() {
        return Boolean.parseBoolean(v);
    }

    @Override
    public ValueType getType() {
        return t;
    }
}
