package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class ReferencesInspectionGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateReferencesInspection() {
        val file = createOrFindFile(extension.capitalize() + "ReferencesInspection.java", myGenDir + "/inspection")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            package $packageDir.inspection;
            
            import com.intellij.codeInspection.LocalInspectionTool;
            import com.intellij.codeInspection.ProblemDescriptor;
            import com.intellij.codeInspection.ProblemHighlightType;
            import com.intellij.codeInspection.ProblemsHolder;
            import com.intellij.openapi.util.TextRange;
            import com.intellij.psi.*;
            import $packageDir.psi.${extensionCapitalized}File;
            import com.intellij.xtextLanguage.xtext.inspections.InspectorBase;
            import org.jetbrains.annotations.NotNull;

            import java.util.List;

            public class ${extensionCapitalized}ReferencesInspection  extends LocalInspectionTool {

                @NotNull
                @Override
                public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
                    if (holder.getFile() instanceof ${extensionCapitalized}File) {
                        return new ${extensionCapitalized}ReferencesInspection.${extensionCapitalized}ReferenceInspector(holder, isOnTheFly);
                    }
                    return PsiElementVisitor.EMPTY_VISITOR;
                }

                private static class ${extensionCapitalized}ReferenceInspector extends InspectorBase {
                    PsiReferenceService referenceService = PsiReferenceService.getService();

                    ${extensionCapitalized}ReferenceInspector(@NotNull ProblemsHolder problemsHolder, boolean isOnTheFly) {
                        super(problemsHolder, isOnTheFly);
                    }

                    @Override
                    public void visitElement(@NotNull PsiElement element) {
                        List<PsiReference> references = referenceService.getReferences(element, PsiReferenceService.Hints.NO_HINTS);
                        for(PsiReference reference : references){
                            if(reference.resolve() == null){
                                TextRange range = null;
                                ProblemDescriptor error = myHolder.getManager().createProblemDescriptor(element, range , "Couldn't resolve reference", ProblemHighlightType.GENERIC_ERROR, myOnTheFly);
                                myHolder.registerProblem(error);
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
