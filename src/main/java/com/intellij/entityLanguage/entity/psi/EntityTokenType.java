package com.intellij.entityLanguage.entity.psi;

import com.intellij.entityLanguage.entity.EntityLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class EntityTokenType extends IElementType {
    @NotNull
    private final String myDebugName;

    public EntityTokenType(@NotNull @NonNls String debugName) {
        super(debugName, EntityLanguage.INSTANCE);
        myDebugName = debugName;
    }

    public String getDebugName() {
        return myDebugName;
    }
}