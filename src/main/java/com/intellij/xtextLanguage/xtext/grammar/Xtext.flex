package com.intellij.xtextLanguage.xtext;
            
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
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
ID =\^?(([a-z]|[A-Z]|_))(([a-z]|[A-Z]|_|[0-9]))*
INT =([0-9])+
STRING =(\"((\\.|[^\\\"]))*\"|'((\\.|[^\\']))*')
ML_COMMENT =\/\*([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?\*\/
SL_COMMENT =\/\/([^\n\r])*(\r?\n)?
WS =((" "|\t|\r|\n))+
ANY_OTHER =.
%%
<YYINITIAL> {
"grammar" {return GRAMMAR_KEYWORD;}
"with" {return WITH_KEYWORD;}
"," {return COMMA_KEYWORD;}
"hidden" {return HIDDEN_KEYWORD;}
"(" {return L_BRACKET_KEYWORD;}
")" {return R_BRACKET_KEYWORD;}
"." {return DOT_KEYWORD;}
"generate" {return GENERATE_KEYWORD;}
"as" {return AS_KEYWORD;}
"import" {return IMPORT_KEYWORD;}
"@" {return AT_SIGN_KEYWORD;}
"fragment" {return FRAGMENT_KEYWORD;}
"*" {return ASTERISK_KEYWORD;}
"returns" {return RETURNS_KEYWORD;}
":" {return COLON_KEYWORD;}
";" {return SEMICOLON_KEYWORD;}
"<" {return L_ANGLE_BRACKET_KEYWORD;}
">" {return R_ANGLE_BRACKET_KEYWORD;}
"::" {return COLONS_KEYWORD;}
"|" {return PIPE_KEYWORD;}
"&" {return AMPERSAND_KEYWORD;}
"?" {return QUES_MARK_KEYWORD;}
"+" {return PLUS_KEYWORD;}
"{" {return L_BRACE_KEYWORD;}
"=" {return EQUALS_KEYWORD;}
"+=" {return PLUS_EQUALS_KEYWORD;}
"current" {return CURRENT_KEYWORD;}
"}" {return R_BRACE_KEYWORD;}
"true" {return TRUE_KEYWORD;}
"false" {return FALSE_KEYWORD;}
"!" {return ACX_MARK_KEYWORD;}
"=>" {return PRED_KEYWORD;}
"->" {return WEAK_PRED_KEYWORD;}
"?=" {return QUES_EQUALS_KEYWORD;}
"[" {return L_SQUARE_BRACKET_KEYWORD;}
"]" {return R_SQUARE_BRACKET_KEYWORD;}
"terminal" {return TERMINAL_KEYWORD;}
"EOF" {return EOF_KEYWORD;}
".." {return RANGE_KEYWORD;}
"enum" {return ENUM_KEYWORD;}
{ID} {return ID;}
{INT} {return INT;}
{STRING} {return STRING;}
{ML_COMMENT} {return ML_COMMENT;}
{SL_COMMENT} {return SL_COMMENT;}
{WS} {return WHITE_SPACE;}
{ANY_OTHER} {return ANY_OTHER;}
}
[^] { return BAD_CHARACTER; }