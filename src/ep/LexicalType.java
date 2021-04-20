package ep;

public enum LexicalType {
    LITERAL(""),
    INTVAL(""),
    DOUBLEVAL(""),
    NAME(""),
    IF("IF"),
    THEN("THEN"),
    ELSE("ELSE"),
    ELSEIF("ELSEIF"),
    ENDIF("ENDIF"),
    FOR("FOR"),
    FORALL("FORALL"),
    NEXT("NEXT"),
    EQ("="),
    LT("<"),
    GT(">"),
    LE("<="),
    GE(">="),
    NE("<>"),
    FUNC("FUNCTION"),
    DIM("DIM"),
    AS("AS"),
    END("END"),
    NL("\n"),
    DOT("."),
    WHILE("WHILE"),
    DO("DO"),
    UNTIL("UNTIL"),
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    LP("("),
    RP(")"),
    COMMA(","),
    LOOP("LOOP"),
    TO("TO"),
    WEND("WEND"),
    EOF("");

    String notation;

    public String getNotation() {
        return notation;
    }

    LexicalType(String notation) {
        this.notation = notation;
    }
}
