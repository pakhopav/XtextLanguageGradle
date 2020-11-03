package com.intellij.xtextLanguage.xtext.generator.generators


import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import java.io.FileOutputStream
import java.io.PrintWriter

class ElementFactoryGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {

    fun generateElementFactoryFile() {
        val file = createFile("${extensionCapitalized}ElementFactory.kt", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            |
            |import com.intellij.psi.impl.source.tree.LeafPsiElement
            |import com.intellij.psi.tree.IElementType
            |import com.intellij.psi.util.PsiTreeUtil
            |
            |abstract class ${extensionCapitalized}ElementFactory {
            |    companion object{
            |        fun createLeafFromString(text: String?, type: IElementType): LeafPsiElement? {
            |            val psiResult = LeafPsiElement(type , text)
            |            if (PsiTreeUtil.hasErrorElements(psiResult)) {
            |                return null
            |            }
            |            return psiResult
            |        }
            |        fun <T> parseFromString(text: String?, type: IElementType?, expectedClass: Class<T>): T? {
            |            val factory = PsiBuilderFactory.getInstance()
            |            val psiBuilder = factory.createBuilder(${extensionCapitalized}ParserDefinition(), ${extensionCapitalized}Lexer(), text!!)
            |            val parser = ${extensionCapitalized}Parser()
            |            parser.parseLight(type, psiBuilder)
            |            val astNode = ReadAction.compute<ASTNode, RuntimeException> { psiBuilder.treeBuilt }
            |            val psiResult = ${extensionCapitalized}Types.Factory.createElement(astNode)
            |            if (PsiTreeUtil.hasErrorElements(psiResult)) {
            |                return null
            |            }
            |            return if (expectedClass.isInstance(psiResult)) expectedClass.cast(psiResult) else null
            |        }
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}