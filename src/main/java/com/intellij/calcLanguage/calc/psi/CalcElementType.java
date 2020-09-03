package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.CalcLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class CalcElementType extends IElementType {
    private String debugName;
    public CalcElementType(@NotNull @NonNls String debugName) {
        super(debugName, CalcLanguage.INSTANCE);
        this.debugName = debugName;
    }

    public String getDebugName() {
        return debugName;
    }
}