package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.calcLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class calcTokenType extends IElementType {
    @NotNull
    private final String myDebugName;

    public calcTokenType(@NotNull @NonNls String debugName) {
        super(debugName, calcLanguage.INSTANCE);
        myDebugName = debugName;
    }

    public String getDebugName() {
        return myDebugName;
    }
}