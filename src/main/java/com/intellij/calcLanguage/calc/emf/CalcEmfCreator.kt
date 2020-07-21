package com.intellij.calcLanguage.calc.emf


import arithmetics.ArithmeticsPackage
import arithmetics.FunctionCall
import arithmetics.Import
import com.intellij.calcLanguage.calc.psi.calcREFERENCEAbstractDefinitionID
import com.intellij.calcLanguage.calc.psi.calcREFERENCEModuleID
import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfCreator
import org.eclipse.emf.ecore.EObject


class CalcEmfCreator : EmfCreator() {

    private var referencedAbstractDefinitions = mutableMapOf<FunctionCall, String>()
    private var referencedModules = mutableMapOf<Import, String>()


    private fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID, functionCall: FunctionCall) {
        referencedAbstractDefinitions.put(functionCall, psiAbstractDefinitionID.text)
    }

    private fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID, import: Import) {
        referencedModules.put(import, psiModuleID.text)
    }

    override fun completeRawModel() {
        val scope = EntityScope(modelDescriptions)
        referencedAbstractDefinitions.forEach {
            val container = it.key
            val resolvedDefinition = scope.getSingleElement(it.value)?.obj
            resolvedDefinition?.let { container.eSet(ArithmeticsPackage.eINSTANCE.functionCall_Func, resolvedDefinition) }
        }

        referencedModules.forEach {
            val container = it.key
            val resolvedModule = scope.getSingleElement(it.value)?.obj
            resolvedModule?.let { container.eSet(ArithmeticsPackage.eINSTANCE.import_Module, resolvedModule) }
        }
    }


    override fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is calcREFERENCEAbstractDefinitionID || psiElement is calcREFERENCEModuleID
    }

    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (psiElement is calcREFERENCEModuleID) visitREFERENCEModuleID(psiElement, container as Import)
        else if (psiElement is calcREFERENCEAbstractDefinitionID) visitREFERENCEAbstractDefinitionID(psiElement, container as FunctionCall)
    }

}