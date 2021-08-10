package com.intellij.xtext.language.validation;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.xtext.bridge.BridgeResult;
import com.intellij.xtext.bridge.EmfCreator;
import com.intellij.xtext.language.psi.XtextFile;
import com.intellij.xtext.validation.AbstractValidator;
import com.intellij.xtext.validation.InspectorBase;
import com.intellij.xtext.validation.ValidatorMethodsHolder;
import org.eclipse.emf.ecore.EObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


public class XtextInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        if (holder.getFile() instanceof XtextFile) {
            return new XtextInspector(holder, isOnTheFly);
        }
        return PsiElementVisitor.EMPTY_VISITOR;
    }

    private static class XtextInspector extends InspectorBase {
        XtextValidator validator = new XtextValidator();
        ValidatorMethodsHolder methodsHolder = new ValidatorMethodsHolder(validator);

        XtextInspector(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
            super(problemsHolder, isOnTheFly);
        }

        @Override
        public void visitElement(@NotNull PsiElement element) {
            PsiFile psiFile = element.getContainingFile();
            BridgeResult bridgeContext = CachedValuesManager.getCachedValue(psiFile, EmfCreator.Companion.getASSOCIATED_EMF_OBJECT(), new XtextCachedBridgeProvider(psiFile));
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