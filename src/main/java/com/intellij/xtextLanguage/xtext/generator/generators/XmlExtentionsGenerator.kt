package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class XmlExtentionsGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateXmlExtentions() {
        val file = createFile(extention.capitalize() + "Plugin.xml", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |<extensions defaultExtensionNs="com.intellij">
            |    <fileTypeFactory implementation="${packageDir}.${extention.capitalize()}FileTypeFactory"/>
            |    <lang.parserDefinition language="${extention.capitalize()}" implementationClass="${packageDir}.${extention.capitalize()}ParserDefinition"/>
            |    <lang.syntaxHighlighterFactory language="${extention.capitalize()}" implementationClass="${packageDir}.${extention.capitalize()}SyntaxHighlighterFactory"/>
            |    <completion.contributor language="${extention.capitalize()}" implementationClass="${packageDir}.${extention.capitalize()}CompletionContributor"/>
            |    <psi.referenceContributor language="${extention.capitalize()}" implementation="${packageDir}.${extention.capitalize()}ReferenceContributor"/>
            |  
            |</extensions>
        """.trimMargin("|"))
        out.close()

    }
}