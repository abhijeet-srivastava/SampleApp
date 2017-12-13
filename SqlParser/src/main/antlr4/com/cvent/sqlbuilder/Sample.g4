grammar Sample;

fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];

fragment DIGIT:
        ('0'..'9');

fragment BLOCK:
        [A-Za-z0-9] [A-Za-z0-9] [A-Za-z0-9] [A-Za-z0-9]
 ;

GT : '>' ;
GE : '>=' ;
LT : '<' ;
LE : '<=' ;
EQ : '=' ;
NEQ : '!=';

US : '_';
COMMA : ',';
QOUTES : '"';


LPAREN : '(' ;
RPAREN : ')' ;

STARTSYMBOL:'((';
ENDSYMBOL:'))';


K_AND: A N D;
K_OR: O R;
K_IN: I N;
K_NOT: N O T;
K_NOT_IN: K_NOT WS K_IN;
K_LIKE: L I K E;
K_NOT_LIKE: K_NOT WS K_LIKE;
K_NOTNULL : N O T N U L L;
K_NULL : N U L L;
K_CURRENT_DATE : C U R R E N T '_' D A T E;
K_CURRENT_TIME : C U R R E N T '_' T I M E;
K_CURRENT_TIMESTAMP : C U R R E N T '_' T I M E S T A M P;

WS  :   ( ' '
        |'\u000B'
        | '\t'
        | '\r'
        | '\n'
        )+ {skip();}
    ;
WSOPT  :   ( ' '
        |'\u000B'
        | '\t'
        | '\r'
        | '\n'
        )* {skip();}
    ;
            
STRING_LITERAL 
           : '"' (~[\r\n"] | '""')* '"' 
              {
                String s = getText();
                s = s.replace("\"\"", "\""); // replace all double quotes with single quotes
                setText(s);
              }
            ;
NUMERIC_LITERAL : 
         DIGIT+ ( '.' DIGIT* )? ( E [-+]? DIGIT+ )?
         | '.' DIGIT+ ( E [-+]? DIGIT+ )?
 ;
           
IDENTIFIER
 : '"' (~'"' | '""')* '"'
 | '`' (~'`' | '``')* '`'
 | '[' ~']'* ']'
 | [a-zA-Z_] [a-zA-Z_0-9]* // TODO check: needs more chars in set
 ;
 
UID
 : BLOCK BLOCK '-' BLOCK '-' BLOCK '-' BLOCK '-' BLOCK BLOCK BLOCK
 ;

operation:
           EQ
           | NEQ
           | GT
           | LT
           | GE
           | LE
           | K_IN
           | K_NOT_IN
           | K_LIKE
           | K_NOT_LIKE EOF;
           
expr:
      literal_value
      | QOUTES literal_value QOUTES
       | COMMA expr
       ;

literal_value : 
                NUMERIC_LITERAL
              | STRING_LITERAL
              | K_NULL
              | K_NOTNULL
              | K_CURRENT_TIME
              | K_CURRENT_DATE
              | K_CURRENT_TIMESTAMP
              | UID
              ;
           
column_values:
              expr
              | LPAREN expr RPAREN
              ;
              
column_name:
            IDENTIFIER
            | STRING_LITERAL
            | STRING_LITERAL US STRING_LITERAL;       
 
conjuction:
            AND | OR;
            
clause:
        whereclause
        | '(' whereclause ')';
            
whereclause: 
            column_name WS? operation WS? column_values;
            
whereclauses:
            clause | clause WS? conjuction WS? whereclauses;

expression: 
                      '((' whereclauses '))'
                        EOF;