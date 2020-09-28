package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.EcorePackageRegistry
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.BridgeRuleType
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextReferencedMetamodel
import org.eclipse.emf.ecore.EPackage

class BridgeRuleTypeRegistry() {
    private val importedModels = mutableMapOf<String, EPackage>()

    fun addXtextFiles(xtextFiles: List<XtextFile>) {
        xtextFiles.flatMap { PsiTreeUtil.findChildrenOfType(it, XtextReferencedMetamodel::class.java) }
                .forEach { addMetamodel(it) }
    }

    fun addMetamodel(model: XtextReferencedMetamodel) {
        val modelUri = model.referenceEcoreEPackageSTRING.text.replace("\"", "").replace("\'", "")
        EcorePackageRegistry.instance.getPackage(modelUri)?.let {
            importedModels.put(model.validID?.text ?: "", it)
        }
    }

    fun findOrCreateType(typeName: String): BridgeRuleType {
        if (typeName.contains("::")) {
            val modelName = typeName.split("::")[0]
            val modelType = typeName.split("::")[1]
            val modelPackage = importedModels.get(modelName)
            return BridgeRuleType(modelPackage!!.getEClassifier(modelType).instanceTypeName, modelPackage.nsPrefix)
        } else {
            importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
                val result = ePackage.getEClassifier(typeName)
                if (result != null) {
                    return BridgeRuleType(result.instanceTypeName, ePackage.nsPrefix)
                }
            }

        }
        return BridgeRuleType(typeName, "")
    }

    companion object {
        fun forXtextFiles(xtextFiles: List<XtextFile>) = BridgeRuleTypeRegistry().also {
            it.addXtextFiles(xtextFiles)
        }
    }
}