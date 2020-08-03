package com.intellij.calcLanguage.calc

import com.intellij.calcLanguage.calc.parser.CalcParser
import com.intellij.calcLanguage.calc.psi.CalcFile
import com.intellij.calcLanguage.calc.psi.CalcTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.DefaultASTFactoryImpl
import com.intellij.lang.ParserDefinition
import com.intellij.lang.ParserDefinition.SpaceRequirements
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.impl.source.tree.CompositeElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class CalcParserDefinition2 : DefaultASTFactoryImpl(), ParserDefinition {

    companion object {
        val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
        val KEYWORDS = TokenSet.create()
        val COMMENTS = TokenSet.create(CalcTypes.SL_COMMENT, CalcTypes.ML_COMMENT)
        val KEY_FEATURE: Key<String> = Key("Feature")
        val FILE = IFileElementType(CalcLanguage.INSTANCE)
    }

    val KEY_FEATURE_NAME: Key<String> = Key("Feature")

    override fun createLexer(project: Project?): Lexer {
        return CalcLexerAdapter()
    }

    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    override fun getCommentTokens(): TokenSet {
        return COMMENTS
    }


    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    override fun createParser(project: Project?): PsiParser {
        return CalcParser()
    }

    override fun getFileNodeType(): IFileElementType? {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider?): PsiFile? {
        return CalcFile(viewProvider!!)
    }

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): SpaceRequirements? {
        return SpaceRequirements.MAY
    }

    override fun createElement(node: ASTNode?): PsiElement {
        return CalcTypes.Factory.createElement(node)
    }

    override fun createComposite(type: IElementType): CompositeElement {
        var composite: CompositeElement
        if (type == CalcTypes.MULTIPLICATION_ASSIGNED_RIGHT) {
            composite = super.createComposite(CalcTypes.MULTIPLICATION)
            composite.putUserData(KEY_FEATURE, "right")
        } else if (type == CalcTypes.PRIMARY_EXPRESSION_ASSIGNED_RIGHT) {
            composite = super.createComposite(CalcTypes.PRIMARY_EXPRESSION)
            composite.putUserData(KEY_FEATURE, "right")
        } else {
            composite = super.createComposite(type)
        }
        return composite
    }
}