grammar SQL;

parse
 : create EOF | index EOF | insert EOF | delete EOF | update EOF | select EOF
 ;

create
 : 'CREATE TABLE' name OPAREN attribute 'PRIMARY KEY' (COMMA attribute)* closercreate
 ;

closercreate
 : CPAREN SCOL
 ;

index
 : 'CREATE INDEX' indexname 'ON' name OPAREN attrname closerindex
 ;

closerindex
 : CPAREN SCOL
 ;

insert
 : 'INSERT INTO' name '(' attrname (',' attrname)* ')' 'VALUES''(' wrapper')' (',' '('wrapper')')* closerinsert
 ;

wrapper
: value (',' value)*
;

closerinsert
 : SCOL
 ;

delete
 : 'DELETE FROM' name ('WHERE' updateinput ('AND' updateinput)*)? closerdelete
 ;

closerdelete
 : SCOL
 ;

update
 : 'UPDATE' name 'SET' updateinput (COMMA updateinput)* 'WHERE' cattrname '=' value closerupdate
 ;

closerupdate
 : SCOL
 ;

cattrname
 : STRING
 ;

updateinput
 : attrname '=' updatevalue
 ;

updatevalue
 : INT | STRING | DOUBLE
 ;

select
 : 'SELECT * FROM' name( 'WHERE' columns (inbetweenoperand columns)* )?closerselect
 ;

closerselect
 : SCOL
 ;

name
 : STRING
 ;

indexname
 : STRING
 ;

type
 : 'INT'|'DOUBLE'|'STRING'
 ;

attribute
 : attrname type
 ;

attrname
 : STRING
 ;

columns
 : attrname oper value
 ;

oper
 : '='|'!='|'>'|'>='|'<'|'<='
 ;

value
 : INT|DOUBLE|STRING
 ;

inbetweenoperand
 : 'AND'|'And'|'and'
 |'OR'|'Or'|'or'
 |'XOR'|'Xor'|'xor'
 ;

SCOL : ';';
COMMA : ',';
OPAREN : '(';
CPAREN : ')';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

DOUBLE
 : [0-9]+ '.' [0-9]*
 | '.' [0-9]+
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;

SPACE
 : [ \t\r\n] -> skip
 ;
