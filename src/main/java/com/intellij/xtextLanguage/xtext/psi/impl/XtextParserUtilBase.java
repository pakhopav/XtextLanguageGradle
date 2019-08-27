package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.vfs.ex.dummy.DummyFileIdGenerator;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.xtextLanguage.xtext.XtextParserDefinition;
import com.intellij.xtextLanguage.xtext.grammar.XtextLexer;
import com.intellij.xtextLanguage.xtext.parser.XtextParser;
import com.intellij.xtextLanguage.xtext.psi.XtextElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextTokenType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class XtextParserUtilBase extends GeneratedParserUtilBaseCopy {
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser) {
        return adapt_builder_(root, builder, parser, null);
    }
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets){
        ErrorState state = new ErrorStateExt();
        ErrorState.initState(state, builder, root, extendsSets);
        return new Builder(builder, state, parser);
    }

    public static class ErrorStateExt extends GeneratedParserUtilBaseCopy.ErrorState{
        public static ArrayList<XtextTokenType> expectedKeywords = new ArrayList<>();
        @Override
        public void appendExpected(@NotNull StringBuilder sb, int position, boolean expected) {
            super.appendExpected(sb, position, expected);
            expectedKeywords.clear();
            MyList<Variant> list = variants;
            for (Variant variant : list){
                if (position == variant.position){
                    if(variant.object instanceof XtextTokenType){
                        expectedKeywords.add((XtextTokenType) variant.object);
                    }
                    else if (variant.object instanceof String){
                        String name = (String) variant.object;
                        name = name.substring(1,name.length()-1).toUpperCase().replace(" ", "_");
                        XtextElementType type = XtextTypesUtil.findByName(name);
                        if(type != null){
                            if(XtextTypesUtil.findKeywords(type).size()>0){
                                expectedKeywords.addAll(XtextTypesUtil.findKeywords(type));

                            }
                        }
//                        expectedKeywords.add(XtextTypesUtil.findByName(name)) ;
                    }
                }
            }

        }
    }
}
