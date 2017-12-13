package com.cvent.sqlbuilder;

import java.util.*;
import java.util.BitSet;

import org.antlr.runtime.*;
import org.antlr.runtime.DFA;
import org.antlr.runtime.tree.*;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.*;


/**
 * Created by a.srivastava on 11/12/16.
 */
public class ExpressionParser {
    private final ANTLRErrorListener _listener = createErrorListener();

    public static void main(String[] args) {
        ExpressionParser exParse = new ExpressionParser();
        //exParse.testExpression();
        //exParse.testTableParse();
        exParse.testHelloWorld();
    }

    private void testHelloWorld() {
        //compile("4B66049D-6E1A-4CE6-8FBF-B31CD8B9E6AF");
        compile();
        parseOperator();
        parseColumnValues();
        parseWhereClause();
        parseMultiWhereClause();
    }

    private void parseMultiWhereClause() {
        String expression = "((acct_id = (2000636)))";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.ExpressionContext context = parser.expression();
        System.out.println(context.toStringTree());
    }

    private void parseWhereClause() {
        String expression = "survey_stub = (\"4B66049D-6E1A-4CE6-8FBF-B31CD8B9E6AF\")";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.WhereclausesContext context = parser.whereclauses();
        SampleParser.ClauseContext cc =   context.clause();
        System.out.println(context.toStringTree());
    }

    private void parseColumnValues() {
        String expression = "\"CDCC4747-5E42-4360-AF49-539456A71244\"";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.Column_valuesContext context = parser.column_values();
        System.out.println(context.toStringTree());
    }

    private void compile() {
        String expression = "survey_stub";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.Column_nameContext context = parser.column_name();
        System.out.println(context.toStringTree());
    }


    private void parseOperator() {
        String expression = "NoT like";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.OperationContext context = parser.operation();
        System.out.println(context.toStringTree());
    }

    /*public void compile() {
        String expression = "survey_stub LIKE (\"4B66049D-6E1A-4CE6-8FBF-B31CD8B9E6AF\")";
        ANTLRInputStream input = new ANTLRInputStream(expression);
        SampleLexer lexer = new SampleLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        SampleParser parser = new SampleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);
        SampleParser.WhereclauseContext context = parser.whereclause();
        System.out.println(context.toStringTree());
    }*/
  
    private void testTableParse() { 
        String expression = "create table SURVEY (id integer NOT NULL, city character varying(50))";
        parseTable(expression);
    }

    private  void testExpression() {
        int res = parse("(20 * (8 / 2 - (1 + 1)))");
        System.out.printf("%d%c", res, '\n');
    }

    /**
     * Parses an expression into an integer result.
     * @param expression the expression to part
     * @return and integer result
     */
    public int parse(final String expression) {
        /*
         * Create a lexer that reads from our expression string
         */
        final SimpleLexer lexer = new SimpleLexer(new ANTLRInputStream(expression));

        /*
         * By default Antlr4 lexers / parsers have an attached error listener
         * that prints errors to stderr. I prefer them to throw an exception
         * instead so I implemented my own error listener which is attached
         * here. I also remove any existing error listeners.
         */
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);

        /*
         * The lexer reads characters and lexes them into token stream. The
         * tokens are consumed by the parser that then builds an Abstract
         * Syntax Tree.
         */
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final SimpleParser parser = new SimpleParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(_listener);

        /*
         * The ExprContext is the root of our Abstract Syntax Tree
         */
        final SimpleParser.ExprContext context = parser.expr();

        /*
         * 'Visit' all the branches of the tree to get our expression result.
         */
        return visit(context);
    }

    /*
     * Visits the branches in the expression tree recursively until we hit a
     * leaf.
     */
    private int visit(final SimpleParser.ExprContext context) {
        if (context.number() != null) { //Just a number
            return Integer.parseInt(context.number().getText());
        }
        else if (context.BR_CLOSE() != null) { //Expression between brackets
            return visit(context.expr(0));
        }
        else if (context.TIMES() != null) { //Expression * expression
            return visit(context.expr(0)) * visit(context.expr(1));
        }
        else if (context.DIV() != null) { //Expression / expression
            return visit(context.expr(0)) / visit(context.expr(1));
        }
        else if (context.PLUS() != null) { //Expression + expression
            return visit(context.expr(0)) + visit(context.expr(1));
        }
        else if (context.MINUS() != null) { //Expression - expression
            return visit(context.expr(0)) - visit(context.expr(1));
        }
        else {
            throw new IllegalStateException();
        }
    }

    /*
     * Helper method to create an ANTLRErrorListener. We're only interested in
     * the syntaxError method.
     */
    private static ANTLRErrorListener createErrorListener() {
        return new ANTLRErrorListener() {
            public void syntaxError(final Recognizer<?, ?> arg0, final Object obj, final int line, final int position, final String message, final RecognitionException ex) {
                throw new IllegalArgumentException(String.format(Locale.ROOT, "Exception parsing expression: '%s' on line %s, position %s", message, line, position));
            }

            @Override
            public void reportAmbiguity(Parser parser, org.antlr.v4.runtime.dfa.DFA dfa, int i, int i1, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
                
            }

            @Override
            public void reportAttemptingFullContext(Parser parser, org.antlr.v4.runtime.dfa.DFA dfa, int i, int i1, BitSet bitSet, ATNConfigSet atnConfigSet) {

            }

            @Override
            public void reportContextSensitivity(Parser parser, org.antlr.v4.runtime.dfa.DFA dfa, int i, int i1, int i2, ATNConfigSet atnConfigSet) {

            }

            public void reportContextSensitivity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final int arg4, final ATNConfigSet arg5) {
            }

            public void reportAttemptingFullContext(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final BitSet arg4, final ATNConfigSet arg5) {
            }

            public void reportAmbiguity(final Parser arg0, final DFA arg1, final int arg2, final int arg3, final boolean arg4, final BitSet arg5, final ATNConfigSet arg6) {
            }
        };
    }


    public void parseTable(final String expression) {
        final WhereExpressionLexer lexer = new WhereExpressionLexer((new ANTLRInputStream(expression)));
        lexer.removeErrorListeners();
        lexer.addErrorListener(_listener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final WhereExpressionParser parser = new WhereExpressionParser(tokens);

        WhereExpressionParser.Table_listContext context = parser.table_list();
        
        List<WhereExpressionParser.Table_defContext> defList = context.table_def();
        for (WhereExpressionParser.Table_defContext td : defList) {
            System.out.println(td.tbl.getName());
            for (Column col :td.tbl.getColumns()) {
                System.out.println(col.getName());
            }
        }
    }
}
