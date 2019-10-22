package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class XmlExtentionsGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateXmlExtentions() {
        val file = createFile(extention + "Plugin.xml", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |<extensions defaultExtensionNs="com.intellij">
            |    <fileTypeFactory implementation="${packageDir}.${extention}FileTypeFactory"/>
            |    <lang.parserDefinition language="${extention}" implementationClass="${packageDir}.${extention}ParserDefinition"/>
            |    <lang.syntaxHighlighterFactory language="${extention}" implementationClass="${packageDir}.${extention}SyntaxHighlighterFactory"/>
            |    <completion.contributor language="${extention}" implementationClass="${packageDir}.${extention}CompletionContributor"/>
            |    <psi.referenceContributor language="${extention}" implementation="${packageDir}.${extention}ReferenceContributor"/>
            |  
            |</extensions>
        """.trimMargin("|"))
        out.close()

    }
}