package com.intellij.smalljavaLanguage.smalljava.psi;

import org.jetbrains.annotations.NotNull;

public class SmalljavaFileVisitor extends SmalljavaVisitor {
    @Override
    public void visitSJProgram(@NotNull SmalljavaSJProgram o) {
        o.getSJImportList().stream().forEach(this::visitSJImport);
        o.getSJClassList().stream().forEach(this::visitSJClass);
    }

    @Override
    public void visitSJClass(@NotNull SmalljavaSJClass o) {

    }

    @Override
    public void visitQualifiedName(@NotNull SmalljavaQualifiedName o) {
        super.visitQualifiedName(o);
    }
}
