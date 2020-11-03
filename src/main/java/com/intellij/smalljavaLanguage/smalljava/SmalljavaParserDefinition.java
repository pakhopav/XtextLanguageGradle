package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.smalljavaLanguage.smalljava.parser.SmalljavaParser;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes;
import org.jetbrains.annotations.NotNull;

public class SmalljavaParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet KEYWORDS = TokenSet.create(
            SmalljavaTypes.PACKAGE_KEYWORD,
            SmalljavaTypes.IMPORT_KEYWORD,
            SmalljavaTypes.CLASS_KEYWORD,
            SmalljavaTypes.EXTENDS_KEYWORD,
            SmalljavaTypes.PRIVATE_KEYWORD,
            SmalljavaTypes.PUBLIC_KEYWORD,
            SmalljavaTypes.PROTECTED_KEYWORD,
            SmalljavaTypes.RETURN_KEYWORD,
            SmalljavaTypes.IF_KEYWORD,
            SmalljavaTypes.ELSE_KEYWORD,
            SmalljavaTypes.TRUE_KEYWORD,
            SmalljavaTypes.FALSE_KEYWORD,
            SmalljavaTypes.THIS_KEYWORD,
            SmalljavaTypes.SUPER_KEYWORD,
            SmalljavaTypes.NULL_KEYWORD,
            SmalljavaTypes.NEW_KEYWORD);

    public static final TokenSet COMMENTS = TokenSet.create(SmalljavaTypes.SL_COMMENT, SmalljavaTypes.ML_COMMENT);
    public static final IFileElementType FILE = new IFileElementType(SmalljavaLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new SmalljavaLexerAdapter();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }
    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new SmalljavaParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new SmalljavaFile(viewProvider);
    }

    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return SmalljavaTypes.Factory.createElement(node);
    }
}
