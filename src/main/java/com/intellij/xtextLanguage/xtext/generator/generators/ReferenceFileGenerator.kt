package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class ReferenceFileGenerator(extension: String) : AbstractGenerator(extension) {
    fun generateReferenceFile() {
        val file = createFile(extension.capitalize() + "Reference.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
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
            |        return MultiResolve(incompleteCode, tClasses);
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
            |    return ${extension.capitalize()}GetVariants(tClasses);
            |    }
            
            |    public  ResolveResult[] MultiResolve(boolean incompleteCode, final List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |        PsiFile file = myElement.getContainingFile();
            |        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
            |        classes.forEach(it -> {
            |            elements.addAll((ArrayList)${extension.capitalize()}Util.findElementsInCurrentFile(file, it, key));
            |        });
            |        List<ResolveResult> results = new ArrayList<>();
            |        elements.forEach(it ->{
            |            results.add(new PsiElementResolveResult(it));
            |        });
            
            |        return results.toArray(new ResolveResult[results.size()]);
            |    }
            
            |    public Object[] ${extension.capitalize()}GetVariants( List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |    PsiFile file = myElement.getContainingFile();
            |    List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
            |    classes.forEach(it ->{
            |        elements.addAll((ArrayList)${extension.capitalize()}Util.findElementsInCurrentFile(file, it));
            |    });
            |    List<LookupElement> variants = new ArrayList<LookupElement>();
            |    elements.forEach(it ->{
            |        if (it.getName() != null && it.getName().length() > 0) {
            |            variants.add(LookupElementBuilder.create(it).
            |                 withIcon(${extension.capitalize()}Icons.FILE).
            |                 withTypeText(it.getContainingFile().getName())
            |                 );
            |        }
            |    });
            
            |    return variants.toArray();
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}