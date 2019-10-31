package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.openapi.application.ReadAction;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.XtextParserDefinition;
import com.intellij.xtextLanguage.xtext.grammar.XtextLexer;
import com.intellij.xtextLanguage.xtext.parser.XtextParser;

public abstract class XtextElementFactory {
    public static <T> T parseFromString(String text, IElementType type, Class<T> expectedClass) {
        PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
        PsiBuilder psiBuilder = factory.createBuilder(new XtextParserDefinition(), new XtextLexer(), text);
        XtextParser parser = new XtextParser();
        parser.parseLight(type, psiBuilder);
        ASTNode astNode = ReadAction.compute(psiBuilder::getTreeBuilt);
        PsiElement psiResult = XtextTypes.Factory.createElement(astNode);
        if (PsiTreeUtil.hasErrorElements(psiResult)) {
            return null;
        }
        return expectedClass.isInstance(psiResult) ? expectedClass.cast(psiResult) : null;
    }

    public static XtextParserRule createParserRule(String name) {
        XtextParserRule rule =
                parseFromString(name, XtextTypes.PARSER_RULE, XtextParserRule.class);
        if (rule == null) {
            throw new IllegalStateException("Can't parse to PARSER_RULE declaration: " + name);
        }
        return rule;
    }

    public static XtextValidID createValidID(String name) {
        XtextValidID validID =
                parseFromString(name, XtextTypes.VALID_ID, XtextValidID.class);
        if (validID == null) {
            throw new IllegalStateException("Can't parse to VALID_ID declaration: " + name);
        }
        return validID;
    }

    public static XtextKeyword createKeyword(String name) {
        XtextKeyword keyword =
                parseFromString(name, XtextTypes.KEYWORD, XtextKeyword.class);
        if (keyword == null) {
            throw new IllegalStateException("Can't parse to KEYWORD declaration: " + name);
        }
        return keyword;
    }


}