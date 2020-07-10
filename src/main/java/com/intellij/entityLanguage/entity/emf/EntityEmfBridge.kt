package com.intellij.entityLanguage.entity.emf

import com.intellij.entityLanguage.entity.psi.EntityDomainmodel
import com.intellij.entityLanguage.entity.psi.EntityFile
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject

class EntityEmfBridge : EmfBridge {


    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is EntityFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, EntityDomainmodel::class.java)
            filePsiRoot?.let {
                val visitor = EntityEmfVisitor()
                return visitor.createModel(it)
            }
        }
        return null
    }



}