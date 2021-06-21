package com.intellij.xtextLanguage.xtext.inspections;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XtextReferencesInspection extends LocalInspectionTool {
    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (holder.getFile() instanceof XtextFile) {
            return new XtextReferencesInspection.XtextReferenceInspector(holder, isOnTheFly);
        }
        return PsiElementVisitor.EMPTY_VISITOR;
    }

    private static class XtextReferenceInspector extends InspectorBase {
        PsiReferenceService referenceService = PsiReferenceService.getService();

        XtextReferenceInspector(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
            super(problemsHolder, isOnTheFly);
        }

        @Override
        public void visitElement(@NotNull PsiElement element) {
            List<PsiReference> references = referenceService.getReferences(element, PsiReferenceService.Hints.NO_HINTS);
            for (PsiReference reference : references) {
                if (reference.resolve() == null) {
                    TextRange range = null;
                    ProblemDescriptor error = myHolder.getManager().createProblemDescriptor(element, range, "Couldn't resolve reference", ProblemHighlightType.GENERIC_ERROR, myOnTheFly);
                    myHolder.registerProblem(error);
                }
            }
        }

    }
}
