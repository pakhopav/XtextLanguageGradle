// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class SmalljavaQualifiedNameImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaQualifiedName {

  public SmalljavaQualifiedNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SmalljavaVisitor visitor) {
    visitor.visitQualifiedName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
    else super.accept(visitor);
  }

}
