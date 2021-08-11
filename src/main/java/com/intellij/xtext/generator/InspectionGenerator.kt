package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class InspectionGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateInspection() {
        val file = createOrFindFile(extension.capitalize() + "Inspection.java", myGenDir + "/validation")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            package $packageDir.validation;
            
            import com.intellij.codeInspection.LocalInspectionTool;
            import com.intellij.codeInspection.ProblemsHolder;
            import com.intellij.psi.PsiElement;
            import com.intellij.psi.PsiElementVisitor;
            import com.intellij.psi.PsiFile;
            import com.intellij.psi.util.CachedValuesManager;
            import $packageDir.psi.${extensionCapitalized}File;
            import com.intellij.xtextLanguage.xtext.bridge.EmfCreator;
            import com.intellij.xtextLanguage.xtext.bridge.BridgeResult;
            import com.intellij.xtextLanguage.xtext.inspections.AbstractValidator;
            import com.intellij.xtextLanguage.xtext.inspections.InspectorBase;
            import com.intellij.xtextLanguage.xtext.inspections.ValidatorMethodsHolder;
            import org.eclipse.emf.ecore.EObject;
            import org.jetbrains.annotations.NotNull;
            import java.lang.reflect.InvocationTargetException;
            import java.lang.reflect.Method;
            import java.util.List;
            
            
            public class ${extensionCapitalized}Inspection extends LocalInspectionTool {
            
                @NotNull
                @Override
                public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
                    if (holder.getFile() instanceof ${extensionCapitalized}File) {
                        return new ${extensionCapitalized}Inspector(holder, isOnTheFly);
                    }
                    return PsiElementVisitor.EMPTY_VISITOR;
                }
            
                private static class ${extensionCapitalized}Inspector extends InspectorBase {
                    ${extensionCapitalized}Validator validator = new ${extensionCapitalized}Validator();
                    ValidatorMethodsHolder methodsHolder = new ValidatorMethodsHolder(validator);
            
                    ${extensionCapitalized}Inspector(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
                        super(problemsHolder, isOnTheFly);
                    }
            
                    @Override
                    public void visitElement(@NotNull PsiElement element) {
                        PsiFile psiFile = element.getContainingFile();
                        BridgeResult bridgeContext = CachedValuesManager.getCachedValue(psiFile, EmfCreator.Companion.getKEY(), new ${extensionCapitalized}CachedValueProvider(psiFile));
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
        """.trimIndent()
        )
        out.close()

    }
}
