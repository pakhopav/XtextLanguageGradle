package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class ReferenceFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateReferenceFile() {
        val file = createOrFindFile(extension.capitalize() + "Reference.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            |import com.intellij.codeInsight.lookup.LookupElement;
            |import com.intellij.codeInsight.lookup.LookupElementBuilder;
            |import com.intellij.openapi.util.TextRange;
            |import com.intellij.psi.*;
            |import org.jetbrains.annotations.NotNull;
            |import org.jetbrains.annotations.Nullable;
            
            |import java.util.ArrayList;
            |import java.util.List;
            
            |public class ${extension.capitalize()}Reference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
            |    private String key;
            |    private List<Class<? extends PsiNameIdentifierOwner>> tClasses;
            
            |    public ${extension.capitalize()}Reference(@NotNull PsiElement element, TextRange textRange, List<Class<? extends PsiNameIdentifierOwner>> tclasses) {
            |        super(element, textRange);
            |        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
            |        this.tClasses = tclasses;
            |    }
            
            |    @NotNull
            |    @Override
            |    public ResolveResult[] multiResolve(boolean incompleteCode) {
            |        return multiResolve(incompleteCode, tClasses);
            |    }
            
            |    @Nullable
            |    @Override
            |    public PsiElement resolve() {
            |        ResolveResult[] resolveResults = multiResolve(false);
            |        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
            |    }
            
            |    @NotNull
            |    @Override
            |    public Object[] getVariants() {
            |    return getVariants(tClasses);
            |    }
            |    public ResolveResult[] multiResolve(boolean incompleteCode, final List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |        PsiFile file = myElement.getContainingFile();
            |        ResolveResult[] results = classes.stream()
            |                .flatMap(it -> ${extensionCapitalized}Util.findElementsInCurrentFile(file, it, key).stream())
            |                .map(PsiElementResolveResult::new).toArray(ResolveResult[]::new);
            |        return results;
            |    }
        
        
            |    public Object[] getVariants(List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |        PsiFile file = myElement.getContainingFile();
            |        Object[] variants = classes.stream()
            |                .flatMap(it -> ${extensionCapitalized}Util.findElementsInCurrentFile(file, it).stream())
            |                .filter(it -> (it.getName() != null && it.getName().length() > 0))
            |                .map(it -> LookupElementBuilder.create(it).
            |                           withIcon(${extensionCapitalized}Icons.FILE).
            |                           withTypeText(it.getContainingFile().getName())).toArray(Object[]::new);
        
            |        return variants;
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}