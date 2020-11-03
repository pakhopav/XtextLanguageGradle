package com.intellij.smalljavaLanguage.smalljava;
            
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
%%
            
%public
%class _SmalljavaLexer // Name of the lexer class which will be generated.
%implements FlexLexer
%function advance
%type IElementType
%unicode
%{
    public _SmalljavaLexer(){
        this((java.io.Reader)null);
    }
%}
ID =\^?(([a-z]|[A-Z]|_))(([a-z]|[A-Z]|_|[0-9]))*
INT =([0-9])+
STRING =(\"((\\.|[^\\\"]))*\"|'((\\.|[^\\']))*')
ML_COMMENT =\/\*([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?\*\/
SL_COMMENT =\/\/([^\n\r])*(\r?\n)?
WS =((" "|\t|\r|\n))+
ANY_OTHER =.
%%
<YYINITIAL> {
"package" {return PACKAGE_KEYWORD;}
";" {return SEMICOLON_KEYWORD;}
"import" {return IMPORT_KEYWORD;}
".*" {return KEYWORD_2;}
"." {return DOT_KEYWORD;}
"class" {return CLASS_KEYWORD;}
"extends" {return EXTENDS_KEYWORD;}
"{" {return L_BRACE_KEYWORD;}
"}" {return R_BRACE_KEYWORD;}
"(" {return L_BRACKET_KEYWORD;}
"," {return COMMA_KEYWORD;}
")" {return R_BRACKET_KEYWORD;}
"private" {return PRIVATE_KEYWORD;}
"public" {return PUBLIC_KEYWORD;}
"protected" {return PROTECTED_KEYWORD;}
"return" {return RETURN_KEYWORD;}
"=" {return EQUALS_KEYWORD;}
"if" {return IF_KEYWORD;}
"else" {return ELSE_KEYWORD;}
"true" {return TRUE_KEYWORD;}
"false" {return FALSE_KEYWORD;}
"this" {return THIS_KEYWORD;}
"super" {return SUPER_KEYWORD;}
"null" {return NULL_KEYWORD;}
"new" {return NEW_KEYWORD;}
{ID} {return ID;}
{INT} {return INT;}
{STRING} {return STRING;}
{ML_COMMENT} {return ML_COMMENT;}
{SL_COMMENT} {return SL_COMMENT;}
{WS} {return WHITE_SPACE;}
{ANY_OTHER} {return ANY_OTHER;}
}
[^] { return BAD_CHARACTER; }