package com.intellij.smalljavaLanguage.smalljava.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile;
import com.intellij.xtextLanguage.xtext.emf.EmfCreator;
import com.intellij.xtextLanguage.xtext.inspections.AbstractValidator;
import com.intellij.xtextLanguage.xtext.inspections.InspectorBase;
import com.intellij.xtextLanguage.xtext.inspections.ValidatorMethodsHolder;
import org.eclipse.emf.ecore.EObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SmalljavaInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (holder.getFile() instanceof SmalljavaFile) {
            return new SmalljavaInspector(holder, isOnTheFly);
        }
        return PsiElementVisitor.EMPTY_VISITOR;
    }

    private static class SmalljavaInspector extends InspectorBase {
        SmalljavaValidator validator = new SmalljavaValidator();
        ValidatorMethodsHolder methodsHolder = new ValidatorMethodsHolder(validator);

        SmalljavaInspector(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
            super(problemsHolder, isOnTheFly);
        }

        @Override
        public void visitElement(@NotNull PsiElement element) {
            PsiFile psiFile = element.getContainingFile();
            bridgeContext = CachedValuesManager.getCachedValue(psiFile, EmfCreator.Companion.getKEY(), new SmalljavaCachedValueProvider(psiFile));
            Object associatedObj = bridgeContext.getMap().get(element);
            if (associatedObj instanceof EObject) {
                List<Method> methodsToInvoke = methodsHolder.getMethods((EObject) associatedObj);
                AbstractValidator.ValidatorContext validatorContext = new AbstractValidator.ValidatorContext(myHolder, myOnTheFly, bridgeContext, (EObject) associatedObj);
                validator.setContext(validatorContext);
                for (Method method : methodsToInvoke) {
                    try {
                        method.invoke(validator, associatedObj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
