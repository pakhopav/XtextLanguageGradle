package com.intellij.smalljavaLanguage.smalljava.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilderFactory
import com.intellij.openapi.application.ReadAction
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.smalljavaLanguage.smalljava.SmalljavaLexerAdapter
import com.intellij.smalljavaLanguage.smalljava.SmalljavaParserDefinition
import com.intellij.xtextLanguage.xtext.parser.XtextParser
import com.intellij.xtextLanguage.xtext.psi.XtextTypes


abstract class SmalljavaElementFactory {
    companion object {
        open fun createLeafFromString(text: String?, type: IElementType): LeafPsiElement? {
            val psiResult = LeafPsiElement(type, text)
            if (PsiTreeUtil.hasErrorElements(psiResult)) {
                return null
            }
            return psiResult
        }

        fun <T> parseFromString(text: String?, type: IElementType, expectedClass: Class<T>): T? {
            val factory = PsiBuilderFactory.getInstance()
            val psiBuilder = factory.createBuilder(SmalljavaParserDefinition(), SmalljavaLexerAdapter(), text!!)
            val parser = XtextParser()
            parser.parseLight(type, psiBuilder)
            val astNode = ReadAction.compute<ASTNode, RuntimeException> { psiBuilder.treeBuilt }
            val psiResult = XtextTypes.Factory.createElement(astNode)
            if (PsiTreeUtil.hasErrorElements(psiResult)) {
                return null
            }
            return if (expectedClass.isInstance(psiResult)) expectedClass.cast(psiResult) else null
        }
    }

}