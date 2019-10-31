// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromCaretEOFBranch1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.EOF_KEY_KEYWORD;

public class XtextRuleFromCaretEOFBranch1Impl extends XtextPsiCompositeElementImpl implements XtextRuleFromCaretEOFBranch1 {

    public XtextRuleFromCaretEOFBranch1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitRuleFromCaretEOFBranch1(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getEofKeyKeyword() {
        return findNotNullChildByType(EOF_KEY_KEYWORD);
    }

}
