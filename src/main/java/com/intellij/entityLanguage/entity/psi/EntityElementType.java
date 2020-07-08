package com.intellij.entityLanguage.entity.psi;

import com.intellij.entityLanguage.entity.EntityLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class EntityElementType extends IElementType {
    private String debugName;

    public EntityElementType(@NotNull @NonNls String debugName) {
        super(debugName, EntityLanguage.INSTANCE);
        this.debugName = debugName;
    }

    public String getDebugName() {
        return debugName;
    }
}