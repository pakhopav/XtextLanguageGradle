package com.intellij.xtextLanguage.xtext;


import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.completion.util.ParenthesesInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.intellij.util.text.CaseInsensitiveStringHashingStrategy;
import com.intellij.xtextLanguage.xtext.grammar.XtextLexer;
import com.intellij.xtextLanguage.xtext.parser.XtextParser;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextTokenType;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextParserUtilBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static com.intellij.lang.parser.GeneratedParserUtilBase.isWhitespaceOrComment;
import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.instanceOf;
import static com.intellij.util.containers.ContainerUtil.newTroveSet;
import static com.intellij.util.containers.ContainerUtil.set;


public class XtextCompletionContributor extends CompletionContributor {
    public XtextCompletionContributor() {


        extend(CompletionType.BASIC, psiElement(XtextTypes.ID).withLanguage(XtextLanguage.INSTANCE)
//                            .andOr(
//
//                                    psiElement().without(new PatternCondition<PsiElement>("XtextWithParent") {
//                                        @Override
//                                        public boolean accepts(@NotNull PsiElement t, ProcessingContext context) {
//                                            PsiElement parent = t.getParent();
//
//                                            return parent.getNode().getElementType() == XtextTypes.VALID_ID;
//                                        }
//                                    })
//                                psiElement().withParent( PsiErrorElement.class),
//                                psiElement().with(new PatternCondition<PsiElement>("XtextBeforeSibling") {
//                                    @Override
//                                    public boolean accepts(@NotNull PsiElement t, ProcessingContext context) {
//                                          PsiElement nextSibling = t.getNextSibling();
//                                          return nextSibling instanceof  PsiErrorElement ;
//                                    }
//                                })
//                            )
                ,
                new XtextKeywordCompletionProvider());

    }


    static class XtextKeywordCompletionProvider extends CompletionProvider<CompletionParameters> {
        private static String myPrefix;

        private  static  final  ArrayList<String> XtextKeywords = new ArrayList<>(Arrays.asList(
                "grammar",
                "with",
                "generate",
                "import",
                "hidden",
                "returns",
                "fragment",
                "as",
                "terminal",
                "enum"

        ));
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters,
                                      @NotNull ProcessingContext context,
                                      @NotNull CompletionResultSet result) {
            for (String keyword : getKeywordsUsingErrorReport(parameters.getPosition(), parameters)) {
                result.addElement(createKeywordLookupElement(keyword));
            }
        }
        private static Collection<String> getKeywords(PsiElement position, CompletionParameters parameters) {
            XtextFile xFile = (XtextFile)position.getContainingFile();
            String fragment = InjectedLanguageUtil.getUnescapedText(xFile, null, position);
            boolean empty = StringUtil.isEmptyOrSpaces(fragment);
            myPrefix =  position.getText().substring(0,parameters.getPosition().getText().length()-(CompletionInitializationContext.DUMMY_IDENTIFIER.length()-1));
            String text = empty ? CompletionInitializationContext.DUMMY_IDENTIFIER : fragment;
            PsiFile file = PsiFileFactory.getInstance(xFile.getProject()).createFileFromText("xtext.xtext", XtextLanguage.INSTANCE, text, true, false);
            int completionOffset = empty ? 0 : fragment.length();
            GeneratedParserUtilBase.CompletionState state = new GeneratedParserUtilBase.CompletionState(completionOffset) {

                @Override
                public boolean prefixMatches(@NotNull PsiBuilder builder, @NotNull String text) {

                    return XtextPrefixMathches(builder,text, myPrefix);

                }

                public boolean XtextPrefixMathches(@NotNull PsiBuilder builder, @NotNull String text, String prefix){
                    if (GeneratedParserUtilBase.ErrorState.get(builder).currentFrame.errorReportedAt != -1){
                        return false;
                    }
                    int builderOffset = builder.getCurrentOffset();

                    int diff = offset - builderOffset;
                    int length = text.length();
                    if (diff == 0) {
                        return prefixMatches(prefix, text);
                    }
                    else if (diff > 0 && diff <= length) {

                        return prefixMatches(prefix, text);
                    }
                    else if (diff < 0) {
                        for (int i=-1; ; i--) {
                            IElementType type = builder.rawLookup(i);
                            int tokenStart = builder.rawTokenTypeStart(i);
                            if (isWhitespaceOrComment(builder, type)) {
                                diff = offset - tokenStart;
                            }
                            else if (type != null && tokenStart < offset) {
                                CharSequence fragment = builder.getOriginalText().subSequence(tokenStart, offset);
                                if (prefixMatches(fragment.toString(), text)) {
                                    diff = offset - tokenStart;
                                }
                                break;
                            }
                            else break;
                        }
                        return diff >= 0 && diff < length;
                    }
                    return false;
                }

                @Override
                public boolean prefixMatches(@NotNull String prefix, @NotNull String variant) {
                    return variant.startsWith(prefix);
                }

                @Nullable
                @Override
                public String convertItem(Object o) {
//                    if (o instanceof IElementType[]) return super.convertItem(o);
                    String text = o instanceof XtextTokenType ? ((XtextTokenType) o).getDebugName(): null;
                    return text != null && text.length() > 0  ? text : null;
                }
            };
            file.putUserData(GeneratedParserUtilBase.COMPLETION_STATE_KEY, state);
            TreeUtil.ensureParsed(file.getNode());

            return state.items;
        }
        private static Collection<String> getKeywordsUsingErrorReport(PsiElement position,CompletionParameters parameters){
            String myPrefix =  position.getText().substring(0,parameters.getPosition().getText().length()-(CompletionInitializationContext.DUMMY_IDENTIFIER.length()-1));

            XtextFile xFile = (XtextFile) position.getContainingFile();
            String fragment = InjectedLanguageUtil.getUnescapedText(xFile, null, position);
            PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
            PsiBuilder psiBuilder = factory.createBuilder(new XtextParserDefinition(), new XtextLexer(), fragment);
            XtextParser parser = new XtextParser();
            parser.parseLight(position.getContainingFile().getNode().getElementType(), psiBuilder);
            ArrayList<String> strings = new ArrayList<>();
            int i = 0;
            for(XtextTokenType t : XtextParserUtilBase.ErrorStateExt.expectedKeywords){
                if(XtextKeywords.contains(t.getDebugName()) && t.getDebugName().startsWith(myPrefix)){
                    strings.add(t.getDebugName());
                }

                i++;
            }
            return strings;
        }
        private static LookupElement createKeywordLookupElement(String keyword) {
            LookupElementBuilder builder = LookupElementBuilder.create(keyword).bold();

            return TailTypeDecorator.withTail(builder.withCaseSensitivity(true), TailType.SPACE);
        }
    }

}


//    @Override
//    public void beforeCompletion(@NotNull CompletionInitializationContext context) {
//        super.beforeCompletion(context);
//    }
//
//    @Override
//    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
//        super.fillCompletionVariants(parameters, result);
//    }

