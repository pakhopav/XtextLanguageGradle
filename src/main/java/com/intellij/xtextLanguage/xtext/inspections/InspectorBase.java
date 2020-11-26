package com.intellij.xtextLanguage.xtext.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.emf.BridgeResult;
import org.jetbrains.annotations.NotNull;


public abstract class InspectorBase extends PsiElementVisitor {
    protected final ProblemsHolder myHolder;
    protected final boolean myOnTheFly;
    public BridgeResult bridgeContext = null;

    public InspectorBase(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
        myHolder = problemsHolder;
        myOnTheFly = isOnTheFly;
    }
}
