// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextCaretEOF;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromcaretEOFCaretEOF;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class XtextCaretEOFImpl extends XtextPsiCompositeElementImpl implements XtextCaretEOF {

    public XtextCaretEOFImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitCaretEOF(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextRuleFromcaretEOFCaretEOF getRuleFromcaretEOFCaretEOF() {
        return findNotNullChildByClass(XtextRuleFromcaretEOFCaretEOF.class);
    }

}
