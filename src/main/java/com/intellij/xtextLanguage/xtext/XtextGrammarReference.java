package com.intellij.xtextLanguage.xtext;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XtextGrammarReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String key;

    public XtextGrammarReference(@NotNull PsiElement element, TextRange textRange) {
        super(element, textRange);
        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        return _multiResolve();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return _getVariants();
    }

    private ResolveResult[] _multiResolve() {
        Project project = myElement.getProject();
        List<ResolveResult> result = new ArrayList<>();
        if (XtextReferenceUtil.DEFAULT_GRAMMARS.contains(key)) {
            PsiFile grammar = XtextReferenceUtil.getDefaultGrammar(key, project);
            if (grammar != null) result.add(new PsiElementResolveResult(grammar));
        } else {
            List<XtextFile> grammars = XtextReferenceUtil.findAllGrammarsInProject(project);
            result = grammars.stream()
                    .filter(grammar -> {
                        String grammarName = PsiTreeUtil.findChildOfType(grammar, XtextGrammarID.class).getText();
//                    String[] nameParts = grammarName.split("\\.");
//                    int namePartsCount = nameParts.length;
//                    if (namePartsCount > 1) {
//                        grammarName = nameParts[namePartsCount - 1];
//                    }
                        return grammarName.equals(key);
                    })
                    .map(grammar -> new PsiElementResolveResult(grammar))
                    .collect(Collectors.toList());

        }
        return result.toArray(new ResolveResult[result.size()]);
    }

    private Object[] _getVariants() {
        Project project = myElement.getProject();
        List<XtextFile> grammars = XtextReferenceUtil.findAllGrammarsInProject(project);
        List<LookupElement> variants = new ArrayList<LookupElement>();
        grammars.forEach(it -> {
            String grammarName = PsiTreeUtil.findChildOfType(it, XtextGrammarID.class).getText();
//            String[] nameParts = grammarName.split("\\.");
//            int namePartsCount = nameParts.length;
//            String shortName = grammarName;
//            if (namePartsCount > 1) {
//                shortName = nameParts[namePartsCount - 1];
//            }
            variants.add(LookupElementBuilder.create(grammarName).
                    withIcon(XtextIcons.FILE).
                    withTypeText(grammarName)
            );
        });

        return variants.toArray();
    }
}