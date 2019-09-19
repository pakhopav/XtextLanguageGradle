package com.intellij.xtextLanguage.xtext;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;
import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*; // Note that is the class which is specified as `elementTypeHolderClass`
// in bnf grammar file. This will contain all other tokens which we will use.

%%

%public
%class _XtextLexer // Name of the lexer class which will be generated.
%implements FlexLexer
%function advance
%type IElementType
%unicode
%{
public _XtextLexer(){
   this((java.io.Reader)null);
 }
%}
ID=\^?[a-zA-Z$_][a-zA-Z0-9$_]*
STRING = (\"([^\"\\]|\\.)*\"|'([^'\\]|\\.)*')
INT=[0-9]+
WS=[ \t\n\x0B\f\r]+
SL_COMMENT = \/\/.*

COMMENT_TAIL=([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
ML_COMMENT=("/*"{COMMENT_TAIL})|"/*"
%%
<YYINITIAL> {

"grammar" {return GRAMMAR;}
"with" {return WITH;}
"generate" {return GENERATE;}
"import" {return IMPORT;}
"hidden" {return HIDDEN;}
"returns" {return RETURNS;}
"," {return COMMA;}
"(" {return L_BRACKET;}
")" {return R_BRACKET;}
"@" {return AT_SIGN;}
"fragment" {return FRAGMENT;}
";" {return SEMICOLON;}
"<" {return L_ANGLE_BRACKET;}
">" {return R_ANGLE_BRACKET;}
"{" {return L_BRACE;}
"}" {return R_BRACE;}
"[" {return L_SQUARE_BRACKET;}
"]" {return R_SQUARE_BRACKET;}
"&" {return AMPERSAND;}
"!" {return ACX_MARK;}
"as" {return AS;}
":" {return COLON;}
"*" {return ASTERISK;}
"true" {return TRUE;}
"false" {return FALCE;}
"=" {return EQUALS;}
"terminal" {return TERMINAL;}
"enum" {return ENUM;}
"=>" {return PRED;}
"->" {return WEAK_PRED;}
"|" {return PIPE;}
"+" {return PLUS;}
"?" {return QUES_MARK;}
".." {return RANGE;}
"." {return DOT;}
"+=" {return PLUS_EQUALS;}
"?=" {return QUES_EQUALS;}
"current" {return CURRENT;}
"EOF" {return EOF_KEY;}
"::" {return COLONS;}

{ID} {return ID; }
{STRING} {return STRING;}
{INT} {return INT;}
{WS} {return WHITE_SPACE;}
{SL_COMMENT} {return SL_COMMENT;}
{ML_COMMENT} {return ML_COMMENT;}
}


[^] { return BAD_CHARACTER; }