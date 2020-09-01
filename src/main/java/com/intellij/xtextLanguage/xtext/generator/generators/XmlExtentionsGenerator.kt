package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class XmlExtentionsGenerator(extention: String) : AbstractGenerator(extention) {
    fun generateXmlExtentions() {
        val file = createFile(extension.capitalize() + "Plugin.xml", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |<extensions defaultExtensionNs="com.intellij">
            |    <fileTypeFactory implementation="${packageDir}.${extension.capitalize()}FileTypeFactory"/>
            |    <lang.parserDefinition language="${extension.capitalize()}" implementationClass="${packageDir}.${extension.capitalize()}ParserDefinition"/>
            |    <lang.syntaxHighlighterFactory language="${extension.capitalize()}" implementationClass="${packageDir}.${extension.capitalize()}SyntaxHighlighterFactory"/>
            |    <completion.contributor language="${extension.capitalize()}" implementationClass="${packageDir}.${extension.capitalize()}CompletionContributor"/>
            |    <psi.referenceContributor language="${extension.capitalize()}" implementation="${packageDir}.${extension.capitalize()}ReferenceContributor"/>
            |  
            |</extensions>
        """.trimMargin("|"))
        out.close()

    }
}