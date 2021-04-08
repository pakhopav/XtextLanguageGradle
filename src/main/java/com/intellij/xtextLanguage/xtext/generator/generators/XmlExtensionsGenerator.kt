package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class XmlExtensionsGenerator(extension: String, val context: MetaContext, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    fun generateXmlExtensions() {
        val file = createFile(extensionCapitalized + "extensions.txt", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.println(
            """
            <fileTypeFactory implementation="${packageDir}.${extensionCapitalized}FileTypeFactory"/>
            <lang.parserDefinition language="$extensionCapitalized" implementationClass="${packageDir}.${extensionCapitalized}ParserDefinition"/>
            <lang.syntaxHighlighterFactory language="$extensionCapitalized" implementationClass="${packageDir}.${extensionCapitalized}SyntaxHighlighterFactory"/>
            <completion.contributor language="$extensionCapitalized" implementationClass="${packageDir}.${extensionCapitalized}CompletionContributor"/>
            <psi.referenceContributor language="$extensionCapitalized" implementation="${packageDir}.${extensionCapitalized}ReferenceContributor"/>
            <lang.refactoringSupport language="$extensionCapitalized" implementationClass="${packageDir}.${extensionCapitalized}RefactoringSupportProvider"/>
            <lang.findUsagesProvider language="$extensionCapitalized" implementationClass="${packageDir}.${extensionCapitalized}FindUsagesProvider"/>
        """.trimIndent()
        )
        val crossReferences = context.rules.filterIsInstance<TreeParserRule>()
            .flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }.distinctBy { it.getBnfName() }
        crossReferences.forEach {
            val referenceName = NameGenerator.toGKitClassName(it.getBnfName())
            out.println(
                """
                <lang.elementManipulator forClass="${packageDir}.impl.${extensionCapitalized}${referenceName}Impl"
                             implementationClass="${packageDir}.psi.${extensionCapitalized}${referenceName}Manipulator"/>
            """.trimIndent()
            )
        }
        out.println(
            """
            <localInspection groupName="$extensionCapitalized" language="$extensionCapitalized" shortName="$extensionCapitalized" displayName="$extensionCapitalized inspection" enabledByDefault="true"
            implementationClass="${packageDir}.inspection.${extensionCapitalized}Inspection"/>
            <localInspection groupName="$extensionCapitalized" language="$extensionCapitalized" shortName="$extensionCapitalized" displayName="$extensionCapitalized reference inspection" enabledByDefault="true"
            level="ERROR" implementationClass="${packageDir}.inspection.${extensionCapitalized}ReferencesInspection"/>    
        """.trimIndent()
        )
        out.close()
    }
}