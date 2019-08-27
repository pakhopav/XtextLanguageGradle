package com.intellij.xtextLanguage.xtext;


import com.intellij.lang.*;
        import com.intellij.lexer.Lexer;
        import com.intellij.openapi.project.Project;
        import com.intellij.psi.*;
        import com.intellij.psi.tree.*;
        import com.intellij.xtextLanguage.xtext.psi.XtextFile;
        import com.intellij.xtextLanguage.xtext.parser.XtextParser;
        import com.intellij.xtextLanguage.xtext.psi.*;
        import org.jetbrains.annotations.NotNull;

public class XtextParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(XtextTypes.SL_COMMENT, XtextTypes.ML_COMMENT);
    public static final TokenSet KEYWORDS = TokenSet.create(XtextTypes.GRAMMAR, XtextTypes.RETURNS, XtextTypes.IMPORT,XtextTypes.GENERATE,XtextTypes.HIDDEN,XtextTypes.TERMINAL,XtextTypes.ENUM,XtextTypes.WITH , XtextTypes.FRAGMENT, XtextTypes.CURRENT, XtextTypes.EOF_KEY);
    public static final IFileElementType FILE = new IFileElementType(XtextLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new XtextlexerAdapter();
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
        return new XtextParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new XtextFile(viewProvider);
    }

    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return XtextTypes.Factory.createElement(node);
    }
}