package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.calcLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class calcElementType extends IElementType {
    private String debugName;

    public calcElementType(@NotNull @NonNls String debugName) {
        super(debugName, calcLanguage.INSTANCE);
        this.debugName = debugName;
    }

    public String getDebugName() {
        return debugName;
    }
}