package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.smalljavaLanguage.smalljava.SmalljavaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SmalljavaTokenType extends IElementType {
    @NotNull
    private final String myDebugName;

    public SmalljavaTokenType(@NotNull @NonNls String debugName) {
        super(debugName, SmalljavaLanguage.INSTANCE);
        myDebugName = debugName;
    }

    public String getDebugName() {
        return myDebugName;
    }
}