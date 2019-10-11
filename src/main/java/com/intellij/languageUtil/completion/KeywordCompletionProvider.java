package com.intellij.languageUtil.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.LookupElementDecorator;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.lang.BracePair;
import com.intellij.lang.Language;
import com.intellij.lang.PsiBuilder;
import com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.impl.source.tree.injected.InjectedLanguageUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class KeywordCompletionProvider<T extends PsiFile, F extends IElementType> extends CompletionProvider<CompletionParameters> {
    final Class<F> fClass;
    final List<String> leftBraces = Arrays.asList("(", "{", "<");
    final List<BracePair> myBraces = Arrays.asList(new BracePair(XtextTypes.L_BRACE, XtextTypes.R_BRACE, true), new BracePair(XtextTypes.L_BRACKET, XtextTypes.R_BRACKET, true), new BracePair(XtextTypes.L_ANGLE_BRACKET, XtextTypes.R_ANGLE_BRACKET, true));
    private Language language;
    private LanguageFileType fileType;

    public KeywordCompletionProvider(Language language, LanguageFileType fileType, Class<F> fClass) {
        this.language = language;
        this.fileType = fileType;
        this.fClass = fClass;
    }

    private Collection<String> getKeywords(PsiElement position, CompletionParameters parameters) throws IllegalAccessException, InstantiationException {
        T xFile = (T) position.getContainingFile();
        String fragment = InjectedLanguageUtil.getUnescapedText(xFile, null, position);
        boolean empty = StringUtil.isEmptyOrSpaces(fragment);

        final String prefix = position.getText().substring(0, parameters.getPosition().getText().length() - (CompletionInitializationContext.DUMMY_IDENTIFIER.length() - 1));
        String text = empty ? CompletionInitializationContext.DUMMY_IDENTIFIER : fragment;
        PsiFile file = PsiFileFactory.getInstance(xFile.getProject()).createFileFromText("name." + fileType.getDefaultExtension(), language, text, true, false);
        int completionOffset = empty ? 0 : fragment.length();
        GeneratedParserUtilBaseCopy.CompletionState state = new GeneratedParserUtilBaseCopy.CompletionState(completionOffset) {

            @Override
            public boolean prefixMatches(@NotNull PsiBuilder builder, @NotNull String text) {
                if (!errorOccured) {
                    if (text.startsWith(prefix)) {
                        return super.prefixMatches(builder, text);
                    }

                }
                return false;

            }

            @Nullable
            @Override
            public String convertItem(Object o) {
                if (o instanceof IElementType[]) {

                    return super.convertItem(o);
                }

                String text = fClass.isInstance(o) ? o.toString() : null;
                if (XtextTypes.TokenMetaInfo.isRegexpToken((IElementType) o)) return null;

                return text != null && text.length() > 0 ? text : null;
            }
        };
        file.putUserData(GeneratedParserUtilBaseCopy.COMPLETION_STATE_KEY, state);
        TreeUtil.ensureParsed(file.getNode());

        return state.items;
    }

    private LookupElement createKeywordLookupElement(String keyword) {

        LookupElementBuilder builder = LookupElementBuilder.create(keyword).bold();
        String rightBrace = "";
        if (keyword.contains(" ")) {
            String[] a = keyword.split(" ");
            for (String element : a) {
                for (BracePair pair : myBraces) {
                    if (element.equals(pair.getLeftBraceType().toString())) {
                        rightBrace = pair.getRightBraceType().toString();
                    }
                }

            }
        }
        return !rightBrace.equals("") ? createLookupElementWithInsertion(keyword, rightBrace) : TailTypeDecorator.withTail(builder.withCaseSensitivity(true), TailType.SPACE);
    }

    private LookupElement createLookupElementWithInsertion(String keyword, final String insert) {

        LookupElementBuilder builder = LookupElementBuilder.create(keyword);
        return LookupElementDecorator.withInsertHandler(builder, (testContext, item) -> {
            Document document = testContext.getDocument();
            int startOffset = testContext.getStartOffset() + keyword.length();
            testContext.setTailOffset(testContext.getTailOffset() - 1);
            document.insertString(startOffset, insert);
            testContext.getOffsetMap().addOffset(CompletionInitializationContext.START_OFFSET, startOffset + insert.length());
            testContext.commitDocument();
            testContext.getEditor().getCaretModel().moveToOffset(testContext.getTailOffset() + 1);
        });
    }
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters,
                                  @NotNull ProcessingContext context,
                                  @NotNull CompletionResultSet result) {

        try {
            for (String keyword : getKeywords(parameters.getPosition(), parameters)) {
                result.addElement(createKeywordLookupElement(keyword));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}