// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextCharacterRange;
import com.intellij.xtextLanguage.xtext.psi.XtextKeyword;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.RANGE_KEYWORD;

public class XtextCharacterRangeImpl extends XtextPsiCompositeElementImpl implements XtextCharacterRange {

  public XtextCharacterRangeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitCharacterRange(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<XtextKeyword> getKeywordList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextKeyword.class);
  }

  @Override
  @Nullable
  public PsiElement getRangeKeyword() {
      return findChildByType(RANGE_KEYWORD);
  }

}
