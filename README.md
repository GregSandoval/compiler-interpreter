# A5 Programming Language Lexer

### Team
Gregory A. Sandoval

## Introduction 
The objective of this assignment is to write a lexer for the A5 language
lexicon: its tokens (i.e., legal ''words''). The lexer transforms an A5
high-level program sequence of characters into a list of tokens for that
program (in a special format). For convenience, this lexer will take
input from standard-input (stdin) and send output to standard-output
(stdout).

A lexer is a deterministic finite automata. This is generally hidden
behind layers of character manipulation and large switch statements. 
Instead, I wanted to focus on the DFA itself, and let the low level 
details fade in background. This led to modeling the graph, then modeling
a lexer, which uses the graph. The core of the code revolved around the 
idea that a lexer shouldn't have to know about the language it's lexing, it should 
just take in the graph that represents the language, and the tokens to 
produce, and it should just work. Furthermore, the tokens that are parsed 
should be distinguishable, with little to no 'special cases'. This involved a
heavy use of polymorphism and generics. This allows you to quickly find type
information using the instanceof operator, rather than carrying around a type
field that you use to determine what type the value is, which is not ideal. 
Although the code hasn't reached the full goal, it's enough to 
complete this assignment. 
 
## The Graph
To model the DFA, I needed a way to model a graph. Rather than taking the 
traditional approach of an adjacency list, I opted for just nodes. There is
no 'Graph' class, rather a node holds a reference to a list of
other nodes, making edges. Each of these 'edges' has a corresponding
predicate function, which takes some parameter and returns whether we
should `walk' to the other node. Walking the graph can be done by
repeatedly finding the next node to walk to giving a particular object.

## The Lexer
Given the graph abstraction, we can create a deterministic finite
automata using a graph. Each graph node represents a state in the DFA;
Each graph edge has a predicate function that determines if a letter
would cause a transition. The lexer _extends_ graph
nodes, adding relevant information, i.e, if the current state is a
*final* or *non-final* state. Accepting tokens would be
modeled by iteratively passing a character to the lexer, which defers to
the graph, until no viable transition can be made, accepting the token
on a final state.

The code comes with several built-in predicates for detection of
letters, numbers, white space, etc. I could've used a regular expression for
detecting character classes, but that's cheating! 
You wouldn't write a hash table using a hash table right? :)

### Example Code
Although the code for parsing is somewhat readable, it's a far cry from 
perfect. From a high level perspective, I wanted the code to read like the
grammar itself. Unfortunately, we must define states before they're used,
which makes the code not so declarative, but the alternative was using pure
strings, which cannot be checked at compile time, risking runtime errors.
So, apologies for the sheer number of classes! I considered them a hard
requirement, as it allows the comparison of token types directly, as opposed
to deferring to some internal variable of the token class, and writing
several special cases.

```java
import compiler.lexer.FinalState;
import compiler.lexer.LexerBuilder;
import compiler.lexer.NonFinalState;
import compiler.lexer.token.IntegerToken;
import compiler.lexer.token.WhitespaceToken;

import static compiler.a5.lexicon.A5EdgePredicates.*;

class MyHeavyHitterClass {
  // Lexer for integers, skipping white spaces
  public static void main(String[] args) {
    // States
    var START = new NonFinalState("START");
    var INTEGER = new FinalState("INTEGER", IntegerToken::new);
    var WHITESPACE = new FinalState("WHITESPACE", WhitespaceToken::new);

    // Transitions
    START.ON(A_WHITESPACE).OR(A_LINE_SEPARATOR).GOTO(WHITESPACE);
    WHITESPACE.ON(A_WHITESPACE).OR(A_NEWLINE).GOTO(WHITESPACE);
    START.ON(A_DIGIT).GOTO(INTEGER);
    INTEGER.ON(A_DIGIT).GOTO(INTEGER);

    var lexer = new LexerBuilder().setStartState(START).createLexer();

    // Convert text to tokens, filter out whitespace, accept only integers
    lexer.analyze("123 43 23 34")
      .stream()
      .filter(token -> !(token instanceof WhitespaceToken))
      .forEach(System.out::println);
  }
}
```

## A5 Grammar
The grammar for the A5 programming language is defined using the normal
regular expression syntax found in most programming languages. The numbers 
next to production rule is the token ID. The token ID is part of the produced 
output.

The grammar corresponds to the DFA depicted in Figure 1, like in most texts,
consider any missing transitions to exist and lead to a shared error
state. The diagram doesn't include terminals, since it would be far
too large to display! Please excuse the use of a regular expression for
edges, including every edge would be a horrible mess.

```
01. comment = '//' .*  
02. id = LU LUD *  
    LU = '_' | [a-zA-Z]  
    LUD = LU | [0-9]  
03. int = SIGN ? DIGITS 
04. float = int [ '.' DIGITS ] ? 
05. string = '"' .* '"' 
    SIGN = plus | minus
    DIGITS = [0-9] +
  
// Unpaired delimiters
06. comma = ','
07. semi = ';'
 
// Keywords
10. kprog = "prog"
11. kmain = "main"
12. kfcn = "fcn"
13. kclass = "class"
15. kfloat = "float"
16. kint = "int"
17. kstring = "string"
18. kif = "if"
19. kelseif = "elseif"
20. kelse = "else"
21. kwhile = "while"
22. kinput = "input"
23. kprint = "print"
24. knew = "new"
25. kreturn = "return"
26. kvar = "var"

// Paired delimeters
31. angle1 = '<'
32. angle2 = '>'
33. brace1 = '{'
34. brace2 = '}'
35. bracket1 = '['
36. bracket2 = ']'
37. parens1 = '('
38. parens2 = ')'

// Other punctuation tokens
41. aster = '*'
42. caret = '^'
43. colon = ':'
44. dot = '.'
45. equal = '='
46. minus = '-'
47. plus = '+'
48. slash = '/'
49. ampersand = '&'

// Multi-char operators
51. oparrow = "->"
52. opeq = "=="
53. opne = "!="
54. ople = "<="
55. opge = ">="
56. opshl = "<<"
57. opshr = ">>"

// Miscellaeous
99. error // Unknown token.
00. eof // End-of-Input.\
 ```

![image](LexerDFADiagram.png)

## Dependencies
Ant is an optional dependency, I've included a prebuilt jar file, so you
can skip that step.

```shell script
brew install java
brew install ant
```


## How to run
In the root directory, run the following commands. 


### Building it from source
The first command builds the java code. The second passes a text 
file to the lexer. The lexer outputs the results to standard out, 
in `.alex` format.

```
ant
java -cp ./out/production/Lexer compiler.Main < TestInput.txt
```


### Running the prebuilt jar
```
java -jar Lexer.jar < TestInput.txt
```


I've included a sample test file, the expected result after running the
java code should be as follows.

```
(Tok: 10 lin= 1,1 str = "prog")
(Tok: 11 lin= 1,6 str = "main")
(Tok: 33 lin= 1,11 str = "{")
(Tok: 23 lin= 2,5 str = "print")
(Tok: 37 lin= 2,10 str = "(")
(Tok: 5 lin= 2,12 str = "Input legs> ")
(Tok: 38 lin= 2,27 str = ")")
(Tok: 7 lin= 2,28 str = ";")
(Tok: 2 lin= 3,5 str = "var")
(Tok: 2 lin= 3,9 str = "a")
(Tok: 45 lin= 3,11 str = "=")
(Tok: 22 lin= 3,13 str = "input")
(Tok: 37 lin= 3,18 str = "(")
(Tok: 16 lin= 3,20 str = "int")
(Tok: 38 lin= 3,24 str = ")")
(Tok: 7 lin= 3,25 str = ";")
(Tok: 2 lin= 4,5 str = "var")
(Tok: 2 lin= 4,9 str = "b")
(Tok: 45 lin= 4,11 str = "=")
(Tok: 22 lin= 4,13 str = "input")
(Tok: 37 lin= 4,18 str = "(")
(Tok: 16 lin= 4,20 str = "int")
(Tok: 38 lin= 4,24 str = ")")
(Tok: 7 lin= 4,25 str = ";")
(Tok: 23 lin= 5,5 str = "print")
(Tok: 37 lin= 5,10 str = "(")
(Tok: 5 lin= 5,12 str = "Hypotenuse= ")
(Tok: 6 lin= 5,26 str = ",")
(Tok: 37 lin= 5,28 str = "(")
(Tok: 2 lin= 5,30 str = "a")
(Tok: 41 lin= 5,32 str = "*")
(Tok: 2 lin= 5,34 str = "a")
(Tok: 47 lin= 5,36 str = "+")
(Tok: 2 lin= 5,38 str = "b")
(Tok: 41 lin= 5,40 str = "*")
(Tok: 2 lin= 5,42 str = "b")
(Tok: 38 lin= 5,44 str = ")")
(Tok: 42 lin= 5,46 str = "^")
(Tok: 4 lin= 5,48 str = "0.5" flo= 0.5)
(Tok: 38 lin= 5,52 str = ")")
(Tok: 7 lin= 5,53 str = ";")
(Tok: 34 lin= 6,1 str = "}")
(Tok: 0 lin= 6,2 str = "")
```

To print every transition performed in the DFA, pass the cli option of `-verbose`,
like so

```
java -cp ./out/production/Lexer compiler.Main -verbose < TestInput.txt 
```

Using the jar:
```
java -jar Lexer.jar -verbose < TestInput.txt 
```

The command outputs some informative transitions, helps with debugging grammar :)

```
START           = 'p' => IDENTIFIER     
IDENTIFIER      = 'r' => IDENTIFIER     
IDENTIFIER      = 'o' => IDENTIFIER     
IDENTIFIER      = 'g' => IDENTIFIER     
Accepted token value: "prog"

START           = ' ' => WHITESPACE     
Accepted token value: " "

START           = 'm' => IDENTIFIER     
IDENTIFIER      = 'a' => IDENTIFIER     
IDENTIFIER      = 'i' => IDENTIFIER     
IDENTIFIER      = 'n' => IDENTIFIER     
Accepted token value: "main"

START           = ' ' => WHITESPACE     
Accepted token value: " "

START           = '{' => LEFT_BRACE     
Accepted token value: "{"

[ The rest has been removed ]
```


## Features
- Ability to log every transition in the DFA.
- Tracks line number and position.
- On error, logs problem line with bad character highlighted
- Extendable, should work with any regular language.
- Declarative, just build the states, and add the corresponding tokens, done!
- Easy to debug DFA, turn on the transition logger and just worry about transitions!
- Hides all character manipulations from the client.

# A7 Programming Language Parser

### Team
Gregory A. Sandoval

## Introduction 
The objective of this assignment is to write a parser for the A7 language.
The parser transforms an A7 source file into a parse tree, then an abstract syntax
tree. The application then serializes the trees into images. The application
accepts source code, or a token stream (in serialized alex format).

## The Parser
The parser is a LL(1) non-recursive predictive parser. The parser is hand
built, I've written every grammar rule and transition within the deterministic push
down automata by hand. This is not a compiler compiler (I wish!). I represent 
every grammar symbol and token with a class. These classes are nodes
within the PST or AST. Using classes was an important architectural decision, as
it allowed me to use the visitor pattern to build out better compiler phases.
The benefits aren't so clear now, but later compiler phases would benefit greatly
from this decision. This application is not performant, optimizations have not been
made to make things _dead_ simple.

## A7 Grammar
Below is the unedited context free grammar for the A7 programming language. In order
to be processed by the parser I had to remove all the left recursion, and the left common
prefixes. 
```
Pgm = kwdprog Vargroup Fcndefs Main 
Main = kwdmain BBlock
BBlock = brace1 Vargroup Stmts brace2
Vargroup = kwdvars PPvarlist | eps 
PPvarlist = parens1 Varlist parens2 
Varlist = Varitem semi Varlist | eps 
Varitem = Vardecl | Vardecl equal Varinit 
Varitem = Classdecl | Classdef 
Vardecl = Simplekind Varspec 
Simplekind = Basekind | Classid 
Basekind = kint | kfloat | kstring
Classid = id
Varspec = Varid | Arrspec | Deref_id 
Varid = id
Arrspec = Varid KKint
KKint = bracket1 int bracket2 
Deref_id = Deref id
Deref = aster
Varinit = Expr | BBexprs
BBexprs = brace1 Exprlist brace2 | brace1 brace2 
Exprlist = Expr Moreexprs
Moreexprs = comma Exprlist | eps
Classdecl = kwdclass Classid
Classdef = Classheader BBclassitems
Classdef = Classheader kif BBclassitems 
BBClassitems = brace1 Classitems brace2 
Classheader = Classdecl Classmom Interfaces 
Classmom = colon Classid | eps
Classitems = Classgroup Classitems | eps
Classgroup = Class_ctrl | Vargroup | Mddecls 
Class_ctrl = colon id // in {public, protected, private} 
Interfaces = plus Classid Interfaces | eps
Mddecls = Mdheader Mddecls | eps 
Mdheader = kwdfcn Md_id PParmlist Retkind 
Md_id = Classid colon Fcnid
Fcndefs = Fcndef Fcndefs | eps 
Fcndef = Fcnheader BBlock
Fcnheader = kwdfcn Fcnid PParmlist Retkind 
Fcnid = id
Retkind = Basekind
PParmlist = parens1 Varspecs parens2 | PPonly 
Varspecs = Varspec More_varspecs 
More_varspecs = comma Varspecs | eps 
PPonly = parens1 parens2
Stmts = Stmt semi Stmts | eps 
Stmt = Stasgn | Fcall | Stif 
Stmt = Stwhile | Stprint | Strtn
Stasgn = Lval equal Expr
Lval = Varid | Aref | Deref_id 
Aref = Varid KKexpr
KKexpr = bracket1 Expr bracket2
Fcall = Fcnid PPexprs
PPexprs = parens1 Exprlist parens2 | PPonly
Stif = kwdif PPexpr BBlock Elsepart 
Elsepart = kwdelseif PPexpr BBlock Elsepart 
Elsepart = kwdelse BBlock | eps
Stwhile = kwdwhile PPexpr BBlock 
Stprint = kprint PPexprs
Strtn = kwdreturn Expr | kwdreturn
PPexpr = parens1 Expr parens2
Expr = Expr Oprel Rterm | Rterm
Rterm = Rterm Opadd Term | Term
Term = Term Opmul Fact | Fact
Fact = BaseLiteral | Lval | Addrof_id | Fcall | Ppexpr 
BaseLiteral = int | float | string
Addrof_id = ampersand id
Oprel = opeq | opne | Lthan | ople | opge | Gthan 
Lthan = angle1
Gthan = angle2
Opadd = plus | minus
Opmul = aster | slash | caret
```

I modified the grammar to be LL(1) parsable, the below grammar includes the full first 
and follow sets, along with any extra rules after processing. In general a `_Suffix` rule
is the result from Left Factoring, and `_Tail` is the result form Left recursion elimination.
Any rule with a prefix of `!` is a result of marking rules that can transition directly to 
epsilon.

```
First(Pgm)                  = {kwdprog}
Follow(Pgm)                 = {EOF}
Pgm                         = kwdprog       !Vargroup        !Fcndefs         Main

First(Main)                 = {kwdmain}
Follow(Main)                = {EOF}
Main                        = kwdmain       BBlock

First(BBlock)               = {brace1}
Follow(BBlock)              = {EOF} U {kwdfcn, kwdmain} U ({kwdelseif, kwdelse}) U {semi} U {semi} U {semi}
BBlock                      = brace1        !Vargroup        !Stmts           brace2



First(!Vargroup)            = {kwdvars}
Follow(!Vargroup)           = {kwdfcn, kwdmain, aster, id, kwdif, kwdwhile, kprint, kwdreturn, brace2, colon, kwdvars}
!Vargroup                   = kwdvars       PPvarlist
!Vargroup                   = !eps

First(PPvarlist)            = {parens1}
Follow(PPvarlist)           = {kwdfcn, kwdmain, aster, id, kwdif, kwdwhile, kprint, kwdreturn, brace2, colon, kwdvars}
PPvarlist                   = parens1       !Varlist         parens2

First(!Varlist)             = {kint, kfloat, kstring, id, kwdclass}
Follow(!Varlist)            = {parens2}
!Varlist                    = Varitem       semi            !Varlist
!Varlist                    = !eps

First(Varitem)              = {kint, kfloat, kstring, id} U {kwdclass}
Follow(Varitem)             = {semi}
Varitem                     = Vardecl       !Varitem_Suffix
Varitem                     = Classdef

First(!Varitem_Suffix)      = {equal}
Follow(!Varitem_Suffix)     = {semi}
!Varitem_Suffix             = equal           Varinit
!Varitem_Suffix             = !eps

First(Vardecl)              = {kint, kfloat, kstring, id}
Follow(Vardecl)             = {equal, semi}
Vardecl                     = Simplekind    Varspec

First(Simplekind)           = {kint, kfloat, kstring} U {id}
Follow(Simplekind)          = {aster, id}
Simplekind                  = Basekind
Simplekind                  = Classid

First(Basekind)             = {kint, kfloat, kstring}
Follow(Basekind)            = {aster, id} U {brace1, kwdfcn, colon, kwdvars, brace2}
Basekind                    = kint
Basekind                    = kfloat
Basekind                    = kstring

First(Classid)              = {id}
Follow(Classid)             = {aster, id, colon, plus} U {brace1, kif} U {plus, brace1, kif} U {brace1, kif} U {colon}
Classid                     = id

First(Varspec)              = {aster} U {id}
Follow(Varspec)             = {equal, semi, comma, parens2}
Varspec                     = Varid         !Arrspec
Varspec                     = Deref_id

First(Varid)                = {id}
Follow(Varid)               = {bracket1, equal, semi, comma, parens2}
Varid                       = id

First(!Arrspec)             = {bracket1}
Follow(!Arrspec)            = {equal, semi, comma, parens2}
!Arrspec                    = KKint
!Arrspec                    = !eps

First(KKint)                = {bracket1}
Follow(KKint)               = {equal, semi, comma, parens2}
KKint                       = bracket1      int             bracket2

First(Deref_id)             = {aster}
Follow(Deref_id)            = {equal, semi, comma, parens2} U {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
Deref_id                    = Deref         id

First(Deref)                = {aster}
Follow(Deref)               = {id}
Deref                       = aster



First(Varinit)              = {int, float, string, aster, id, ampersand, parens1} U {brace1}
Follow(Varinit)             = {semi}
Varinit                     = Expr
Varinit                     = BBexprs

First(BBexprs)              = {brace1}
Follow(BBexprs)             = {semi}
BBexprs                     = brace1        !Exprlist        brace2

First(!Exprlist)            = {int, float, string, aster, id, ampersand, parens1}
Follow(!Exprlist)           = {brace2, parens2}
!Exprlist                   = Expr          !Moreexprs
!Exprlist                   = !eps

First(!Moreexprs)           = {comma}
Follow(!Moreexprs)          = {brace2, parens2}
!Moreexprs                  = comma         Expr            !Moreexprs
!Moreexprs                  = !eps



First(Classdef)             = {kwdclass}
Follow(Classdef)            = {semi}
Classdef                    = Classheader   Classdef_Suffix

First(Classdef_Suffix)      = {brace1} U {kif}
Follow(Classdef_Suffix)     = {semi}
Classdef_Suffix             = BBClassitems
Classdef_Suffix             = kif             BBClassitems

First(BBClassitems)         = {brace1}
Follow(BBClassitems)        = {semi}
BBClassitems                = brace1        !Classitems      brace2

First(Classheader)          = {kwdclass}
Follow(Classheader)         = {brace1, kif}
Classheader                 = kwdclass      Classid     !Classmom        !Interfaces

First(!Classmom)            = {colon}
Follow(!Classmom)           = {plus, brace1, kif}
!Classmom                   = colon         !Classid
!Classmom                   = !eps

First(!Classitems)          = {colon, kwdvars, kwdfcn}
Follow(!Classitems)         = {brace2}
!Classitems                 = !Classgroup    !Classitems
!Classitems                 = !eps

First(!Classgroup)          = {colon} U {kwdvars} U {kwdfcn}
Follow(!Classgroup)         = {colon, kwdvars, kwdfcn, brace2}
!Classgroup                 = Class_ctrl
!Classgroup                 = !Vargroup
!Classgroup                 = !Mddecls

First(Class_ctrl)           = {colon}
Follow(Class_ctrl)          = {colon, kwdvars, kwdfcn, brace2}
Class_ctrl                  = colon         id

First(!Interfaces)          = {plus}
Follow(!Interfaces)         = {brace1, kif}
!Interfaces                 = plus          Classid         !Interfaces
!Interfaces                 = !eps



// Mistake :(
First(!Mddecls)             = {kwdfcn}
Follow(!Mddecls)            = {colon, kwdvars, kwdfcn, brace2}
!Mddecls                    = Mdheader      !Mddecls
!Mddecls                    = !eps

First(Mdheader)             = {kwdfcn}
Follow(Mdheader)            = {kwdfcn, colon, kwdvars, brace2}
Mdheader                    = kwdfcn        Md_id           PParmlist       Retkind

First(Md_id)                = {id}
Follow(Md_id)               = {parens1}
Md_id                       = Classid       colon           Fcnid


First(!Fcndefs)             = {kwdfcn}
Follow(!Fcndefs)            = {kwdmain}
!Fcndefs                    = Fcndef        !Fcndefs
!Fcndefs                    = !eps

First(Fcndef)               = {kwdfcn}
Follow(Fcndef)              = {kwdfcn, kwdmain}
Fcndef                      = Fcnheader     BBlock

First(Fcnheader)            = {kwdfcn}
Follow(Fcnheader)           = {brace1}
Fcnheader                   = kwdfcn        Fcnid           PParmlist       Retkind

First(Fcnid)                = {id}
Follow(Fcnid)               = {parens1}
Fcnid                       = id

First(Retkind)              = {kint, kfloat, kstring}
Follow(Retkind)             = {brace1, kwdfcn, colon, kwdvars, brace2}
Retkind                     = Basekind

First(PParmlist)            = {parens1}
Follow(PParmlist)           = {kint, kfloat, kstring}
PParmlist                   = parens1       !Varspecs        parens2

First(!Varspecs)            = {aster, id}
Follow(!Varspecs)           = {parens2}
!Varspecs                   = Varspec       !More_varspecs
!Varspecs                   = !eps

First(!More_varspecs)       = {comma}
Follow(!More_varspecs)      = {parens2}
!More_varspecs              = comma         Varspec         !More_varspecs
!More_varspecs              = !eps



First(!Stmts)               = {aster, id, kwdif, kwdwhile, kprint, kwdreturn}
Follow(!Stmts)              = {brace2}
!Stmts                      = Stmt            semi            !Stmts
!Stmts                      = !eps

First(Stmt)                 = {aster, id} U {kwdif} U {kwdwhile} U {kprint} U {kwdreturn}
Follow(Stmt)                = {semi}
Stmt                        = StasgnOrFcall
Stmt                        = Stif
Stmt                        = Stwhile
Stmt                        = Stprint
Stmt                        = Strtn

First(StasgnOrFcall)        = {aster} U {id}
Follow(StasgnOrFcall)       = {semi}
StasgnOrFcall               = Deref_id        Stasgn_Suffix                     // It's  Stasgn
StasgnOrFcall               = id              StasgnOrFcall_Suffix              // It's  Either one

First(StasgnOrFcall_Suffix) = {bracket1, equal} U {parens1}
Follow(StasgnOrFcall_Suffix)= {semi}
StasgnOrFcall_Suffix        = !Lval_Suffix    Stasgn_Suffix                     // It's  Stasgn
StasgnOrFcall_Suffix        = PPexprs                                           // It's  Fcall

First(Stasgn_Suffix)        = {equal}
Follow(Stasgn_Suffix)       = {semi}
Stasgn_Suffix               = equal           Expr

First(!Lval_Suffix)         = {bracket1}
Follow(!Lval_Suffix)        = {equal, aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
!Lval_Suffix                = KKexpr
!Lval_Suffix                = !eps

First(KKexpr)               = {bracket1}
Follow(KKexpr)              = Follow(!Lval_Suffix)
KKexpr                      = bracket1        Expr            bracket2


First(PPexprs)              = {parens1}
Follow(PPexprs)             = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma} U Follow(Fcall)
PPexprs                     = parens1         !Exprlist        parens2


First(Stif)                 = {kwdif}
Follow(Stif)                = {semi}
Stif                        = kwdif           PPexpr          BBlock          !Elsepart

First(!Elsepart)            = {kwdelseif, kwdelse}
Follow(!Elsepart)           = {semi}
!Elsepart                   = kwdelseif       PPexpr          BBlock          !Elsepart
!Elsepart                   = kwdelse         BBlock
!Elsepart                   = !eps


First(Stwhile)              = {kwdwhile}
Follow(Stwhile)             = {semi}
Stwhile                     = kwdwhile        PPexpr          BBlock

First(Stprint)              = {kprint}
Follow(Stprint)             = {semi}
Stprint                     = kprint          PPexprs


First(Strtn)                = {kwdreturn}
Follow(Strtn)               = {semi}
Strtn                       = kwdreturn       !Strtn_Suffix

First(!Strtn_Suffix)        = {int, float, string, aster, id, ampersand, parens1}
Follow(!Strtn_Suffix)       = {semi}
!Strtn_Suffix               = Expr
!Strtn_Suffix               = !eps


First(PPexpr)               = {parens1}
Follow(PPexpr)              = {brace1}
PPexpr                      = parens1         Expr            parens2

First(Expr)                 = {int, float, string, aster, id, ampersand, parens1}
Follow(Expr)                = {brace2, bracket2, semi, parens2, comma}
Expr                        = Rterm           !Expr_Tail

First(!Expr_Tail)           = {opeq, opne, angle1, ople, opge, angle2}
Follow(!Expr_Tail)          = {brace2, bracket2, semi, parens2, comma}
!Expr_Tail                  = Oprel           Rterm         !Expr_Tail
!Expr_Tail                  = !eps

First(Rterm)                = {int, float, string, aster, id, ampersand, parens1}
Follow(Rterm)               = {opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma, parens2}
Rterm                       = Term            !Rterm_Tail

First(!Rterm_Tail)          = {plus, minus}
Follow(!Rterm_Tail)         = {opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma, parens2}
!Rterm_Tail                 = Opadd           Term          !Rterm_Tail
!Rterm_Tail                 = !eps

First(Term)                 = {int, float, string, aster, id, ampersand, parens1}
Follow(Term)                = {plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
Term                        = Fact            !Term_Tail

First(!Term_Tail)           = {aster, slash, caret}
Follow(!Term_Tail)          = {plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
!Term_Tail                  = Opmul           Fact          !Term_Tail
!Term_Tail                  = !eps

First(Fact)                 = {int, float, string} U {aster, id} U {ampersand} U {parens1}
Follow(Fact)                = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
Fact                        = BaseLiteral
Fact                        = LvalOrFcall
Fact                        = Addrof_id
Fact                        = PPexpr

First(LvalOrFcall)          = {aster} U {id}
Follow(LvalOrFcall)         = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
LvalOrFcall                 = Deref_id                      // Is Lval
LvalOrFcall                 = id         !LvalOrFcall_Suffix // Either one

First(!LvalOrFcall_Suffix)  = {bracket1} U {parens1}
Follow(!LvalOrFcall_Suffix) = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
!LvalOrFcall_Suffix         = !Lval_Suffix                  // It's an Lval
!LvalOrFcall_Suffix         = PPexprs                       // It's a Fcall

First(BaseLiteral)          = {int, float, string}
Follow(BaseLiteral)         = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
BaseLiteral                 = int
BaseLiteral                 = float
BaseLiteral                 = string

First(Addrof_id)            = {ampersand}
Follow(Addrof_id)           = {aster, slash, caret, plus, minus, opeq, opne, angle1, ople, opge, angle2, brace2, bracket2, semi, parens2, comma}
Addrof_id                   = ampersand       id

First(Oprel)                = {opeq, opne, angle1, ople, opge, angle2}
Follow(Oprel)               = {int, float, string, aster, id, ampersand, parens1}
Oprel                       = opeq
Oprel                       = opne
Oprel                       = angle1
Oprel                       = ople
Oprel                       = opge
Oprel                       = angle2

First(Opadd)                = {plus, minus}
Follow(Opadd)               = {int, float, string, aster, id, ampersand, parens1}
Opadd                       = plus
Opadd                       = minus

First(Opmul)                = {aster, slash, caret}
Follow(Opmul)               = {int, float, string, aster, id, ampersand, parens1}
Opmul                       = aster
Opmul                       = slash
Opmul                       = caret
```

## Dependencies
Ant is an optional dependency, I've included a prebuilt jar file, so you
can skip that step. Java 13 is *_required_*. The A5 Lexer is also a requirement,
but i've included a prebuilt jar as well. 

```shell script
brew install java
brew install ant
```


## How to run
In the root directory, run the following commands. 


### Building it from source
The first command builds the java code. The second passes a text 
file to the parser. The parser outputs the results images named
`pst.png` and `ast.png`. These are MacOS build instructions only.

```
ant
java --enable-preview -cp ./Lexer.jar:./out/production/Parser Main --file=sample.a7 
```


### Running the prebuilt jar
I've included a sample test file, the expected result after running the
java code should be as follows.

```
java --enable-preview -jar Parser.jar --file=sample.a7
```

Contents of `sample.a7`:
``` 
prog

fcn needsGenerics(language) string {
  if(language == "golang"){
      return 1;
  };
  return 0; // 99% accuracy
}

main {
  var (
      string person = "john";
      int age = 40;
  )

  while(needsGenerics("golang")) {
      print("lol, golang still doesn't have generics?");
  };

  if("java" < "golang"){
      print("Did hell freeze over?");
  };

  age = age * 100 / (2 * 8) ^ 8.0 + 12;
}
```

Parse Tree:
![image](./pst.png)

Abstract Syntax Tree:
![image](./ast.png)

Using the jar emits the same output, but requires no build steps:

```
java -jar Parser.jar --file=sample.a7
```

Reading input from previously tokenized code is done as follows:
```
java --enable-preview -jar Parser.jar --alex < sample-alex-stream.txt
```

Parse Tree:
![image](./pst-alex-stream.png)

Abstract Syntax Tree:
![image](./ast-alex-stream.png)

## Sample app with all language features 
The below command runs a fully featured sample program:

```
java --enable-preview -jar Parser.jar --file=full-language-features.a7
```

Parse Tree:
![image](./pst-full.png)

Abstract Syntax Tree:
![image](./ast-full.png)

## Features
- Converts source to full PST and AST representations
- Logs error with line numbers and expected output (requires the use
of a file).
- Covers the entire A7 language (bonus points?).

