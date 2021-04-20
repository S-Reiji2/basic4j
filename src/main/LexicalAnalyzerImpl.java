package main;

import static main.LexicalType.*;
import java.io.*;
import java.util.*;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    private final PushbackReader pr;
    private List<LexicalUnit> list = new ArrayList<>();

    public LexicalAnalyzerImpl(InputStream in) {
        pr = new PushbackReader(new InputStreamReader(in));
        list.add(null);
    }

    @Override
    public LexicalUnit get() throws Exception {
        int c;
        String first = null, s;
        StringBuilder sb = new StringBuilder();
        LexicalUnit lu;
        if (list.size() > 1) list.remove(0);

        while (true) {
            c = pr.read();
            s = String.valueOf((char) c);
            
            
            if (first == null && (s.matches("[\t ]"))) {
                continue;
            } else if (first == null && c < 0) {
                lu = new LexicalUnit(EOF);
            } else {
                if (first == null) {
                    first = s;
                    c = pr.read();
                    s = String.valueOf((char) c);
                    sb.append(first);
                }

                lu = first.equals("\n") ? getNewLine(s, sb)
                        : first.equals("\"") ? getLiteral(s, sb)
                        : first.matches("[0-9]") ? getNumeric(s, sb)
                        : first.matches("[a-zA-Z]") ? getName(s, sb)
                        : getSign(s, sb);
                
                if (lu != null)  pr.unread(c);
            }
            
            if (lu == null && c < 0) lu = new LexicalUnit(EOF);
            
            if (lu != null) {
                list.add(lu);
                if (list.size() == 3) break;
                first = null;
                sb = new StringBuilder();
            }
        }

        return list.get(0);
    }
    
    private LexicalUnit getNewLine(String s, StringBuilder sb) {
        if (sb.lastIndexOf("\n") == -1) {
            sb.append(s);
            return null;
        } else {
            return new LexicalUnit(NL);
        }
    }

    private LexicalUnit getLiteral(String s, StringBuilder sb) {
        if (sb.lastIndexOf("\"") == 0) {
            sb.append(s);
            return null;
        } else {
            String literal = sb.toString().replaceAll("\"", "");
            return new LexicalUnit(LITERAL, new ValueImpl(literal));
        }
    }

    private LexicalUnit getNumeric(String s, StringBuilder sb) {
        if (s.matches("[0-9]") || (s.matches("\\.") && sb.indexOf(".") == -1)) {
            sb.append(s);
            return null;
        } else {
            String v = sb.toString();
            LexicalType lt;
            ValueImpl vi;

            if (sb.indexOf(".") == -1) {
                lt = INTVAL;
                vi = new ValueImpl(v, ValueType.INTEGER);
            } else {
                lt = DOUBLEVAL;
                vi = new ValueImpl(v, ValueType.DOUBLE);
            }

            return new LexicalUnit(lt, vi);
        }
    }

    private LexicalUnit getName(String s, StringBuilder sb) {
        if (s.matches("[0-9a-zA-Z]")) {
            sb.append(s);
            return null;
        } else {
            LexicalUnit lu = getReserved(sb.toString());
            if (lu != null) return lu;
            return new LexicalUnit(NAME, new ValueImpl(sb.toString()));
        }
    }

    private LexicalUnit getSign(String s, StringBuilder sb) {
        if (sb.length() == 0) {
            sb.append(s);
            return null;
        } else {
            LexicalUnit lu = getReserved(sb.toString() + s);

            if (lu == null) return getReserved(sb.toString());
            
            sb.append(s);
            return null;
        }
    }

    private LexicalUnit getReserved(String s) {
        for (LexicalType t : values()) {
            if (s.toUpperCase().equals(t.getNotation())
                    && !s.equals("\n")) {
                return new LexicalUnit(t);
            }
        }
        
        return null;
    }

    @Override
    public LexicalUnit peek() throws Exception {
        if (list.size() < 2) get();
        return list.get(1);
    }

    @Override
    public LexicalUnit peek2() throws Exception {
        if (list.size() < 3) get();
        return list.get(2);
    }
}
