package com.intellij.xtextLanguage.xtext.psi;


import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.XtextLanguage;
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
//    @Override
//    public String toString() {
//        return "XtextTokenType." + super.toString();
//    }
}