package com.intellij.calcLanguage.calc;
            
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
import static com.intellij.calcLanguage.calc.psi.CalcTypes.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
%%
            
%public
%class _CalcLexer // Name of the lexer class which will be generated.
%implements FlexLexer
%function advance
%type IElementType
%unicode
%{
    public _CalcLexer(){
        this((java.io.Reader)null);
    }
%}
NUMBER =([0-9])*(\.([0-9])+)?
INT =this" "one" "has" "been" "deactivated
ID =\^?([a-z]|[A-Z]|_)([a-z]|[A-Z]|_|[0-9])*
INT =([0-9])+
STRING =\"(\\.|[^\\\"])*\"|'(\\.|[^\\'])*'
ML_COMMENT =\/\*([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?\*\/
SL_COMMENT =\/\/[^\n\r]*(\r?\n)?
WS=[ \t\n\x0B\f\r]+
ANY_OTHER =.
%%
<YYINITIAL> {
"module" {return MODULE_KEYWORD;}
"import" {return IMPORT_KEYWORD;}
"def" {return DEF_KEYWORD;}
"(" {return L_BRACKET_KEYWORD;}
"," {return COMMA_KEYWORD;}
")" {return R_BRACKET_KEYWORD;}
":" {return COLON_KEYWORD;}
";" {return SEMICOLON_KEYWORD;}
"+" {return PLUS_KEYWORD;}
"-" {return MINUS_KEYWORD;}
"*" {return ASTERISK_KEYWORD;}
"/" {return SLASH_KEYWORD;}
{NUMBER} {return NUMBER;}
{INT} {return INT;}
{ID} {return ID;}
{INT} {return INT;}
{STRING} {return STRING;}
{ML_COMMENT} {return ML_COMMENT;}
{SL_COMMENT} {return SL_COMMENT;}
{WS} {return WHITE_SPACE;}
{ANY_OTHER} {return ANY_OTHER;}
}
[^] { return BAD_CHARACTER; }