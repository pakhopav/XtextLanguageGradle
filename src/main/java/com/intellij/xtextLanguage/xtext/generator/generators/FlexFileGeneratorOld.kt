package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRuleElement
import java.io.FileOutputStream
import java.io.PrintWriter

class FlexFileGeneratorOld(extension: String, val context: MetaContext) : AbstractGenerator(extension) {
    fun generateFlexFile() {
        val file = createFile(extension.capitalize() + ".flex", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.lexer.FlexLexer;
            |import com.intellij.psi.tree.IElementType;
            |import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
            |import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
            |import static $packageDir.psi.${extension.capitalize()}Types.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
            |%%
            
            |%public
            |%class _${extension.capitalize()}Lexer // Name of the lexer class which will be generated.
            |%implements FlexLexer
            |%function advance
            |%type IElementType
            |%unicode
            |%{
            |    public _${extension.capitalize()}Lexer(){
            |        this((java.io.Reader)null);
            |    }
            |%}

        """.trimMargin("|"))
        context.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
            } else {
                out.print("${it.name.toUpperCase()} =")
                it.alternativeElements.map { it as TerminalRuleElement }.forEach {
                    out.print(it.getFlexName())
                }
                out.print("\n")
            }
            if (!context.terminalRules.any { it.name == "WS" }) out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
        }
        out.print("%%\n<YYINITIAL> {\n")
        context.keywordModel.keywords.forEach { out.print("\"${it.keyword}\" {return ${it.name.toUpperCase()};}\n") }
        context.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("{WS} {return WHITE_SPACE;}\n")
            } else {
                out.print("{${it.name.toUpperCase()}} {return ${it.name.toUpperCase()};}\n")
            }
            if (!context.terminalRules.any { it.name == "WS" }) out.print("{WS} {return WHITE_SPACE;}\n")

        }
        out.print("}\n[^] { return BAD_CHARACTER; }")
        out.close()

    }
}