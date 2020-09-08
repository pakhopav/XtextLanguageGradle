package com.intellij.statLanguage.stat.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.statLanguage.stat.StatLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class StatElementType extends IElementType {
    private String debugName;
    public StatElementType(@NotNull @NonNls String debugName) {
        super(debugName, StatLanguage.INSTANCE);
        this.debugName = debugName;
    }

    public String getDebugName() {
        return debugName;
    }
}