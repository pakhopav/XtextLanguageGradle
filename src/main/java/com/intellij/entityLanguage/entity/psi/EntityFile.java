package com.intellij.entityLanguage.entity.psi;

import com.intellij.entityLanguage.entity.EntityFileType;
import com.intellij.entityLanguage.entity.EntityLanguage;
import com.intellij.entityLanguage.entity.emf.EntityEmfBridge;
import com.intellij.entityLanguage.entity.emf.EntityEmfVisitor;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.emf.EmfFileBase;
import org.eclipse.emf.ecore.EObject;
import org.jetbrains.annotations.NotNull;
import org.xtext.example.entity.entity.Domainmodel;

import javax.swing.*;

public class EntityFile extends PsiFileBase implements EmfFileBase {

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

    @NotNull
    @Override
    public EObject getEmfRoot() {
        if (emfModelRoot == null) {
            EntityEmfBridge bridge = new EntityEmfBridge();
            emfModelRoot = (Domainmodel) bridge.createEmfModel(this);
        }
        return emfModelRoot;
    }

    private Domainmodel createMyEmfModel() {
        EntityDomainmodel psiRoot = PsiTreeUtil.findChildOfAnyType(this, EntityDomainmodel.class);
        return psiRoot == null ? null : EntityEmfVisitor.Companion.getRawEmfModel(psiRoot);
    }

}
