package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.CalcLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CalcTokenType extends IElementType {
    @NotNull
    private final String myDebugName;

    public CalcTokenType(@NotNull @NonNls String debugName) {
        super(debugName, CalcLanguage.INSTANCE);
        myDebugName = debugName;
    }

    public String getDebugName() {
        return myDebugName;
    }
}