package com.intellij.entityLanguage.entity.psi;

import com.intellij.entityLanguage.entity.EntityFileType;
import com.intellij.entityLanguage.entity.EntityLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.xtext.example.entity.entity.Domainmodel;

import javax.swing.*;

public class EntityFile extends PsiFileBase {

    private Domainmodel emfModelRoot;



    public EntityFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, EntityLanguage.INSTANCE);
    }


    @NotNull
    @Override
    public FileType getFileType() {
        return EntityFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Entity File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }


}
