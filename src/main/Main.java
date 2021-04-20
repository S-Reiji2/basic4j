package main;

import nodes.Node;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //InputStream in = new FileInputStream("test1.bas");
        InputStream in = new FileInputStream("test2.bas");
        LexicalAnalyzer lex = new LexicalAnalyzerImpl(in);
        Environment env = new Environment(lex);
        
        Node p = Symbol.program.handle(env);
        //System.out.println(p);
        p.getValue();
    }
}
