package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.smalljavaLanguage.smalljava.SmalljavaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SmalljavaElementType extends IElementType {
    private String debugName;

    public SmalljavaElementType(@NotNull @NonNls String debugName) {
        super(debugName, SmalljavaLanguage.INSTANCE);
        this.debugName = debugName;
    }

    public String getDebugName() {
        return debugName;
    }
}