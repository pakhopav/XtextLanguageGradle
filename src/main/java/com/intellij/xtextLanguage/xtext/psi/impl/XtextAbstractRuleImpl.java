package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule;
import org.jetbrains.annotations.NotNull;

public abstract class XtextAbstractRuleImpl extends XtextNamedElementImpl implements XtextAbstractRule {
    public XtextAbstractRuleImpl(@NotNull ASTNode node) {
        super(node);
    }

}
