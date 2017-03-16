grammar XQuery;


/*
 * ====================
 * Query
 * ====================
 */


query
:
	xq EOF
;


/*
 * ====================
 * XQuery
 * ====================
 */


xq
    :    VAR                      #SingleVariable
    |    STRING                   #StringConstant
    |    ap                       #AbsolutePath
    |    '(' xq ')'               #ParentheseXq
    |    xq '/' rp                #XqSSlashRp
    |    xq '//' rp               #XqDSlashRp
    |    xq ',' xq                #CommaXq
    |    '<' TAGNAME '>'
         '{' xq '}'
         '</' TAGNAME '>'         #Constructor
    |    forClause
         (letClause)?
         (whereClause)?
         returnClause             #FLWRClause
    |    letClause xq             #SingleLetClause
    |    'join(' xq ',' xq ',' '['attlist']' ',' '['attlist']' ')'               #JoinClause
    |    'join(' xq ',' xq ',' '['']' ',' '['']' ')'                                   #JoinClause
    ;


/*
 * ====================
 * join Clause
 * ====================
 */
attlist
    :   TAGNAME (',' TAGNAME)*
    ;

/*
 * ====================
 * for Clause
 * ====================
 */


forClause
    :     'for' in (',' in)*
    ;


in
    :    VAR 'in' xq
    ;

/*
 * ====================
 * Let Clause
 * ====================
 */


letClause
    :     'let' VAR ':=' xq (','VAR ':=' xq )*
    ;


/*
 * ====================
 * Where Clause
 * ====================
 */


whereClause
    :     'where' cond
    ;


/*
 * ====================
 * Return Clause
 * ====================
 */


returnClause
    :     'return' xq
    ;


/*
 * ====================
 * Condition
 * ====================
 */


cond
    :    xq '=' xq               #ValueEqualCondition
    |    xq 'eq' xq              #ValueEqualCondition
    |    xq '==' xq              #IdEqualCondition
    |    xq 'is' xq              #IdEqualCondition
    |    'empty' '(' xq ')'         #EmptyCondition
    |    'some'
         (VAR 'in' xq ',')*
         VAR 'in' xq
         'satisfies' cond        #SomeCondition
    |    '(' cond ')'            #ParentheseCondition
    |    cond 'and' cond         #AndCondition
    |    cond 'or' cond          #OrCondition
    |    'not' cond              #NotCondition
    ;


/*
* ====================
* Absolute Path
* ====================
*/


ap
    :    FILENAME '/'  rp        #SSlashAp
    |    FILENAME '//' rp        #DSlashAp
    ;


/*
 * ====================
 * Relative Path
 * ====================
 */


rp
    :    TAGNAME                 #TagName
    |    '*'                     #WildCard
    |    '.'                     #SDot
    |    '..'                    #DDot
    |    'text()'                #Text
    |    ATTNAME                 #AttName
    |    '(' rp ')'              #ParentheseRp
    |    rp '/' rp               #SSlashRp
    |    rp '//' rp              #DSlashRp
    |    rp '[' f ']'            #RpWithFilter
    |    rp ',' rp               #CommaRp
    ;


/*
 * ====================
 * Path Filter
 * ====================
 */


f
    :    rp                      #RpFilter
    |    rp '=' rp               #ValueEqualFilter
    |    rp 'eq' rp              #ValueEqualFilter
    |    rp '==' rp              #IdEqualFilter
    |    rp 'is' rp              #IdEqualFilter
    |    '(' f ')'               #Filter
    |    f 'and' f               #FilterAndFilter
    |    f 'or' f                #FilterOrFilter
    |    'not' f                 #NotFilter
    ;


/*
 * ====================
 *	KeyWords and Symbols
 * ====================
 */


VAR
    :    '$' ID+
    ;

FILENAME
    :    'doc(' STRING ')'
    ;

ATTNAME
    :    '@' ID+
    ;

TAGNAME
    :    ID+
    ;
ATTLIST
    :    TAGNAME (',' TAGNAME)*
    ;

/*
 * ===========================
 * String/Integers Primitives
 * ===========================
 */


fragment ID
    :    [a-zA-Z0-9]
    ;


STRING
    :    '"' ( ESC | . )*? '"'
    ;


fragment ESC
    :    '\\'  [btnr"\\]
    ;



/*
* ===========================
* Skip Whitespace
* ===========================
*/


WHITESPACE
    :
        [ \n\r\t]+ -> skip
    ;