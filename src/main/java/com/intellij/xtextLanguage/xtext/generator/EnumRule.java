package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextAnnotation;
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule;

public class EnumRule {
    public XtextEnumRule myRule;
    public String name;
    public String returnType;
    public String alternatives;
    public String[] anotations;

    public EnumRule(XtextEnumRule rule) {
        myRule = rule;
        name = rule.getName();
        returnType = rule.getReturns() != null ? rule.getReturns().getNextSibling().getText() : null;
        alternatives = getAlternativesAsString(myRule);
        XtextAnnotation[] xtextAnnotations = PsiTreeUtil.getChildrenOfType(myRule, XtextAnnotation.class);
        if (xtextAnnotations != null && xtextAnnotations.length > 0) {
            anotations = new String[xtextAnnotations.length];
            int i = 0;
            for (XtextAnnotation xtextAnnotation : xtextAnnotations) {
                anotations[i] = xtextAnnotation.getText();
                i++;
            }
        }
    }

    protected String getAlternativesAsString(XtextEnumRule myRule) {
        String result = "";

        return result;
    }
}
