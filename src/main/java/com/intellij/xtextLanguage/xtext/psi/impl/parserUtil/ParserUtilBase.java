package com.intellij.xtextLanguage.xtext.psi.impl.parserUtil;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.xtextLanguage.xtext.psi.XtextElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextTokenType;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextTypesUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParserUtilBase extends GeneratedParserUtilBaseCopy {
    private static ArrayList<XtextTokenType> expectedKeywords = new ArrayList<>();

    public ArrayList<XtextTokenType> getExpectedKeywords() {
        return expectedKeywords;
    }
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser) {
        return adapt_builder_(root, builder, parser, null);
    }
    public static PsiBuilder adapt_builder_(IElementType root, PsiBuilder builder, PsiParser parser, TokenSet[] extendsSets){
        ErrorState state = new ErrorStateExt();
        ErrorState.initState(state, builder, root, extendsSets);
        return new Builder(builder, state, parser);
    }

    public static class ErrorStateExt extends GeneratedParserUtilBaseCopy.ErrorState{
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
