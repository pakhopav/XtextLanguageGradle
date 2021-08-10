package com.intellij.xtext.metamodel

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtext.language.EcorePackageRegistry
import com.intellij.xtext.language.psi.XtextFile
import com.intellij.xtext.language.psi.XtextReferencedMetamodel
import com.intellij.xtext.metamodel.elements.emf.EmfClassDescriptor
import org.eclipse.emf.ecore.EPackage

class EmfModelRegistry() {
    private val importedModels = mutableMapOf<String, EPackage>()

    fun addXtextFiles(xtextFiles: List<XtextFile>) {
        xtextFiles.flatMap { PsiTreeUtil.findChildrenOfType(it, XtextReferencedMetamodel::class.java) }
                .forEach { addMetamodel(it) }
    }

    fun addMetamodel(model: XtextReferencedMetamodel) {
        val modelUri = model.referenceePackageSTRING.text.replace("\"", "").replace("\'", "")
        EcorePackageRegistry.instance.getPackage(modelUri)?.let {
            importedModels.put(model.validID?.text ?: "", it)
        }
    }

//    fun findOrCreateType(psiTypeRef: XtextTypeRef): EmfClassDescriptor? {
//        psiTypeRef.referenceAbstractMetamodelDeclaration?.let {
//            val modelName = it.text
//            val modelType = psiTypeRef.referenceEcoreEClassifier.text
//            val modelPackage = importedModels.get(modelName)
//            return EmfClassDescriptor(modelPackage!!.getEClassifier(modelType).instanceTypeName, modelPackage.nsPrefix)
//        }
//
//        importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
//            val result = ePackage.getEClassifier(psiTypeRef.referenceEcoreEClassifier.text)
//            result?.let {
//                return EmfClassDescriptor(it.instanceTypeName, ePackage.nsPrefix)
//            }
//        }
//
//
//        return null
//    }

    fun findOrCreateType(typeName: String): EmfClassDescriptor? {
        if (typeName.contains("::")) {
            val modelName = typeName.split("::")[0]
            val modelType = typeName.split("::")[1]
            val modelPackage = importedModels.get(modelName)

            return EmfClassDescriptor(modelPackage!!.getEClassifier(modelType))
        } else {
            importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
                val result = ePackage.getEClassifier(typeName)
                if (result != null) {
                    return EmfClassDescriptor(result)
                }
            }

        }
        return null
    }

    companion object {
        fun forXtextFiles(xtextFiles: List<XtextFile>) = com.intellij.xtext.metamodel.EmfModelRegistry().also {
            it.addXtextFiles(xtextFiles)
        }
    }
}