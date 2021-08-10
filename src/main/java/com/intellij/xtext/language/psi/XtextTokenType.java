package com.intellij.xtext.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.xtext.language.XtextLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class XtextTokenType extends IElementType {
    @NotNull
    private final String myDebugName;
    public XtextTokenType(@NotNull @NonNls String debugName) {
        super(debugName, XtextLanguage.INSTANCE);
        myDebugName = debugName;
    }
    public String getDebugName(){
        return myDebugName;
    }
}