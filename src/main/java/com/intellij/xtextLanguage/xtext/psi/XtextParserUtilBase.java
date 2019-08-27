package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.xtextLanguage.xtext.XtextParserDefinition;
import org.jetbrains.annotations.Nullable;

public class XtextParserUtilBase extends GeneratedParserUtilBase
{


    public static PsiBuilder.Marker enter_section_(PsiBuilder builder) {
        return GeneratedParserUtilBase.enter_section_(builder);
    }

    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets) {
        ErrorState state = new ErrorState();

//        state.completionState = new CompletionState();
        ErrorState.initState(state, builder, root, extendsSets);
        return new Builder(builder, state, parser);
    }

}
