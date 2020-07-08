package com.intellij.entityLanguage.entity;
            
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
import static com.intellij.entityLanguage.entity.psi.EntityTypes.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
%%
            
%public
%class _EntityLexer // Name of the lexer class which will be generated.
%implements FlexLexer
%function advance
%type IElementType
%unicode
%{
    public _EntityLexer(){
        this((java.io.Reader)null);
    }
%}
ID =\^?([a-z]|[A-Z]|_)([a-z]|[A-Z]|_|[0-9])*
INT =([0-9])+
STRING =\"(\\.|[^\\\"])*\"|'(\\.|[^\\'])*'
ML_COMMENT =\/\*([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?\*\/
SL_COMMENT =\/\/[^\n\r]*(\r?\n)?
WS=[ \t\n\x0B\f\r]+
ANY_OTHER =.
%%
<YYINITIAL> {
"datatype" {return DATATYPE_KEYWORD;}
"entity" {return ENTITY_KEYWORD;}
"extends" {return EXTENDS_KEYWORD;}
"{" {return L_BRACE_KEYWORD;}
"}" {return R_BRACE_KEYWORD;}
"many" {return MANY_KEYWORD;}
":" {return COLON_KEYWORD;}
{ID} {return ID;}
{INT} {return INT;}
{STRING} {return STRING;}
{ML_COMMENT} {return ML_COMMENT;}
{SL_COMMENT} {return SL_COMMENT;}
{WS} {return WHITE_SPACE;}
{ANY_OTHER} {return ANY_OTHER;}
}
[^] { return BAD_CHARACTER; }