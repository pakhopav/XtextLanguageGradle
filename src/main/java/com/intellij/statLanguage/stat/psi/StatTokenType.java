package com.intellij.statLanguage.stat.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.statLanguage.stat.StatLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class StatTokenType extends IElementType {
    @NotNull
    private final String myDebugName;

    public StatTokenType(@NotNull @NonNls String debugName) {
        super(debugName, StatLanguage.INSTANCE);
        myDebugName = debugName;
    }

    public String getDebugName() {
        return myDebugName;
    }
}