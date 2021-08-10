package com.intellij.xtext.validation;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;


public abstract class InspectorBase extends PsiElementVisitor {
    protected final ProblemsHolder myHolder;
    protected final boolean myOnTheFly;

    public InspectorBase(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
        myHolder = problemsHolder;
        myOnTheFly = isOnTheFly;
    }
}
