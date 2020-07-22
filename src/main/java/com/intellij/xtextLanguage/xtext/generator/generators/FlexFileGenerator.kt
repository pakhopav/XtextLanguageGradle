package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class FlexFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateFlexFile() {
        val file = createFile(extention.capitalize() + ".flex", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.lexer.FlexLexer;
            |import com.intellij.psi.tree.IElementType;
            |import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
            |import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
            |import static $packageDir.psi.${extention.capitalize()}Types.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
            |%%
            
            |%public
            |%class _${extention.capitalize()}Lexer // Name of the lexer class which will be generated.
            |%implements FlexLexer
            |%function advance
            |%type IElementType
            |%unicode
            |%{
            |    public _${extention.capitalize()}Lexer(){
            |        this((java.io.Reader)null);
            |    }
            |%}

        """.trimMargin("|"))
        fileModel.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
            } else {
                out.print("${it.name} =")
                it.alterntiveElements.forEach {
                    out.print(it.getFlexName())
                }
                out.print("\n")
            }
            if (fileModel.ruleResolver.getTerminalRuleByName("WS") == null) out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
        }
        out.print("%%\n<YYINITIAL> {\n")
        fileModel.keywordModel.keywords.forEach { out.print("\"${it.keyword.substring(1, it.keyword.length - 1)}\" {return ${it.name};}\n") }
        fileModel.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("{WS} {return WHITE_SPACE;}\n")
            } else {
                out.print("{${it.name}} {return ${it.name};}\n")
            }
            if (fileModel.ruleResolver.getTerminalRuleByName("WS") == null) out.print("{WS} {return WHITE_SPACE;}\n")

        }
        out.print("}\n[^] { return BAD_CHARACTER; }")
        out.close()

    }
}