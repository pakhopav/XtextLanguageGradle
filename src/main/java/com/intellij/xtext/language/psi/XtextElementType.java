package com.intellij.xtext.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.xtext.language.XtextLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
            
public class XtextElementType extends IElementType {
    private String debugName;
    public XtextElementType(@NotNull @NonNls String debugName) {
        super(debugName, XtextLanguage.INSTANCE);
        this.debugName = debugName;
    }
    public String getDebugName(){
        return debugName;
    }
}