package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextAnnotation;
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule;

public class TerminalRule {
    public XtextTerminalRule myRule;
    public String name;
    public boolean isFragment;
    public String returnType;
    public String regexp;
    public String[] anotations;

    public TerminalRule(XtextTerminalRule rule) {
        myRule = rule;
        name = rule.getName();
        isFragment = rule.getFragment() != null;
        returnType = rule.getReturns() != null ? rule.getReturns().getNextSibling().getText() : null;
        regexp = getRegexpAsString(myRule);
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


    public String getRegexpAsString(XtextTerminalRule rule) {
        String regexp = "";
        String leafText = "";
        PsiElement colon = rule.getColon();
        PsiElement leaf = PsiTreeUtil.nextLeaf(colon);
        while (leaf != null) {
            if (PsiTreeUtil.isAncestor(rule, leaf, true)) {
                String type = leaf.getNode().getElementType().toString();
                if (type.equals("STRING")) {
                    leafText = leaf.getText().replace("\'", "");
                    leafText = avoidRegexpSymbols(leafText);
                    regexp += leafText;
                } else if (type.equals("!")) {
                    regexp = "";
                    break;
                } else if (type.equals("->")) {
                    regexp += "(?s).*";
                } else if (type.equals("WHITE_SPACE")) {
                    regexp += "";
                } else if (type.equals(";")) {
                    break;
                } else if (type.equals("ID")) {
                    regexp += "ref_" + leaf.getText();


                } else if (type.equals("..")) {
                    char ch = regexp.charAt(regexp.length() - 1);
                    regexp = regexp.substring(0, regexp.length() - 1);
                    regexp += "[" + ch + "-";
                    leaf = PsiTreeUtil.nextLeaf(leaf);
                    if (leaf.getNode().getElementType().toString().equals("WHITE_SPACE")) {
                        leaf = PsiTreeUtil.nextLeaf(leaf);
                    }
                    if (leaf.getNode().getElementType().toString().equals("STRING")) {
                        leafText = leaf.getText().replace("\'", "");
                        leafText = avoidRegexpSymbols(leafText);
                        regexp += leafText;
                        regexp += "]";
                    }


                } else {
                    regexp += leaf.getText();
                }
                leaf = PsiTreeUtil.nextLeaf(leaf);
            } else break;

        }
        return regexp;
    }

    public String avoidRegexpSymbols(String string) {
        String newString = "";
        char[] arr = string.toCharArray();
        for (char ch : arr) {
            if (ch == '*' || ch == '.' || ch == '"') {
                newString += '\\';
                newString += ch;
            } else newString += ch;
        }
        return newString;
    }
}
