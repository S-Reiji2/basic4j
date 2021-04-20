package ep;

public abstract class Value {
    public Value(String s) {};
    public Value(int i) {};
    public Value(double d) {};
    public Value(boolean b) {};
    public Value(String s, ValueType t) {};
    public abstract String getSValue();
    public abstract int getIValue();
    public abstract double getDValue();
    public abstract boolean getBValue();
    public abstract ValueType getType();
}
