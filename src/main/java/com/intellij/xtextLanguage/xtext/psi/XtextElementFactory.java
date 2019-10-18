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

public class XtextElementFactory {

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

    //    public static PsiElement createValidID(String name) {
//        XtextRuleIdentifier ruleId =
//                parseFromString(name, XtextTypes.RULE_IDENTIFIER, XtextRuleIdentifier.class);
//        if (ruleId == null) {
//            throw new IllegalStateException("Can't parse to RULE_IDENTIFIER declaration: " + name);
//        }
//        return ruleId.getValidID();
//    }
//
    public static XtextParserRule createParserRule(String text) {
        XtextParserRule rule =
                parseFromString(text, XtextTypes.PARSER_RULE, XtextParserRule.class);
        if (rule == null) {
            throw new IllegalStateException("Can't parse to XtextParserRuleHolder declaration: " + text);
        }
        return rule;
    }

}