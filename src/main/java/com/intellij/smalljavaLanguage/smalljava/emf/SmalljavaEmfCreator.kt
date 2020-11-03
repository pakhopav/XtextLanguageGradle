package com.intellij.smalljavaLanguage.smalljava.emf

import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.emf.scope.SmalljavaScope
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJClassQualifiedName
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJMemberID
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJSymbolID
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.EmfCreator
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.ecore.EObject
import smallJava.*

class SmalljavaEmfCreator : EmfCreator() {
    private val SJPROGRAM = SmalljavaSJProgramBridgeRule()
    private val SJIMPORT = SmalljavaSJImportBridgeRule()
    private val SJCLASS = SmalljavaSJClassBridgeRule()
    private val SJMEMBER = SmalljavaSJMemberBridgeRule()
    private val SJFIELD = SmalljavaSJFieldBridgeRule()
    private val SJMETHOD = SmalljavaSJMethodBridgeRule()
    private val SJPARAMETER = SmalljavaSJParameterBridgeRule()
    private val SJMETHODBODY = SmalljavaSJMethodBodyBridgeRule()
    private val SJSTATEMENT = SmalljavaSJStatementBridgeRule()
    private val SJRETURN = SmalljavaSJReturnBridgeRule()
    private val SJVARIABLEDECLARATION = SmalljavaSJVariableDeclarationBridgeRule()
    private val SJIFSTATEMENT = SmalljavaSJIfStatementBridgeRule()
    private val SJIFBLOCK = SmalljavaSJIfBlockBridgeRule()
    private val SJBLOCK = SmalljavaSJBlockBridgeRule()
    private val SJSYMBOL = SmalljavaSJSymbolBridgeRule()
    private val SJEXPRESSION = SmalljavaSJExpressionBridgeRule()
    private val SJASSIGNMENT = SmalljavaSJAssignmentBridgeRule()
    private val SJSELECTIONEXPRESSION = SmalljavaSJSelectionExpressionBridgeRule()
    private val SJTERMINALEXPRESSION = SmalljavaSJTerminalExpressionBridgeRule()
    private val sJClassToSJClassNameList = mutableListOf<Pair<SJClass, String>>()
    private val sJFieldToSJClassNameList = mutableListOf<Pair<SJField, String>>()
    private val sJMethodToSJClassNameList = mutableListOf<Pair<SJMethod, String>>()
    private val sJParameterToSJClassNameList = mutableListOf<Pair<SJParameter, String>>()
    private val sJVariableDeclarationToSJClassNameList = mutableListOf<Pair<SJVariableDeclaration, String>>()
    private val sJExpressionToSJMemberNameList = mutableListOf<Pair<SJExpression, String>>()
    private val sJExpressionToSJSymbolNameList = mutableListOf<Pair<SJExpression, String>>()
    private val sJExpressionToSJClassNameList = mutableListOf<Pair<SJExpression, String>>()
    private val scope = SmalljavaScope(modelDescriptions)
    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {
        if (psiElement.node.elementType == SmalljavaTypes.SJ_PROGRAM) {
            return SJPROGRAM
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_IMPORT) {
            return SJIMPORT
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_CLASS) {
            return SJCLASS
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_MEMBER) {
            return SJMEMBER
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_FIELD) {
            return SJFIELD
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_METHOD) {
            return SJMETHOD
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_PARAMETER) {
            return SJPARAMETER
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_METHOD_BODY) {
            return SJMETHODBODY
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_STATEMENT) {
            return SJSTATEMENT
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_RETURN) {
            return SJRETURN
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_VARIABLE_DECLARATION) {
            return SJVARIABLEDECLARATION
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_IF_STATEMENT) {
            return SJIFSTATEMENT
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_IF_BLOCK || psiElement.node.elementType == SmalljavaTypes.SJ_IF_BLOCK_1) {
            return SJIFBLOCK
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_BLOCK) {
            return SJBLOCK
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_SYMBOL) {
            return SJSYMBOL
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_EXPRESSION) {
            return SJEXPRESSION
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_ASSIGNMENT) {
            return SJASSIGNMENT
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_SELECTION_EXPRESSION) {
            return SJSELECTIONEXPRESSION
        }
        if (psiElement.node.elementType == SmalljavaTypes.SJ_TERMINAL_EXPRESSION) {
            return SJTERMINALEXPRESSION
        }
        return null
    }

    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
        obj?.let {
            if (obj is SJProgram) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is SJClass) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is SJField) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is SJMethod) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is SJParameter) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is SJVariableDeclaration) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else return
        }
    }

    override fun completeRawModel() {
        sJClassToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "superclass" }
                container.eSet(feature, it)
            }
        }
        sJFieldToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                container.eSet(feature, it)
            }
        }
        sJMethodToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                container.eSet(feature, it)
            }
        }
        sJParameterToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                container.eSet(feature, it)
            }
        }
        sJVariableDeclarationToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                container.eSet(feature, it)
            }
        }
        sJExpressionToSJMemberNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjMember)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "member" }
                container.eSet(feature, it)
            }
        }
        sJExpressionToSJSymbolNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjSymbol)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "symbol" }
                container.eSet(feature, it)
            }
        }
        sJExpressionToSJClassNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(it.second, smallJava.SmallJavaPackage.eINSTANCE.sjClass)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                container.eSet(feature, it)
            }
        }
    }

    override fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is SmalljavaREFERENCESJClassQualifiedName || psiElement is SmalljavaREFERENCESJClassQualifiedName || psiElement is SmalljavaREFERENCESJClassQualifiedName || psiElement is SmalljavaREFERENCESJClassQualifiedName || psiElement is SmalljavaREFERENCESJClassQualifiedName || psiElement is SmalljavaREFERENCESJMemberID || psiElement is SmalljavaREFERENCESJSymbolID || psiElement is SmalljavaREFERENCESJClassQualifiedName
    }

    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (container is SJClass && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJClassToSJClassNameList.add(Pair(container, psiElement.text))
        else if (container is SJField && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJFieldToSJClassNameList.add(Pair(container, psiElement.text))
        else if (container is SJMethod && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJMethodToSJClassNameList.add(Pair(container, psiElement.text))
        else if (container is SJParameter && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJParameterToSJClassNameList.add(Pair(container, psiElement.text))
        else if (container is SJVariableDeclaration && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJVariableDeclarationToSJClassNameList.add(Pair(container, psiElement.text))
        else if (container is SJExpression && psiElement is SmalljavaREFERENCESJMemberID)
            sJExpressionToSJMemberNameList.add(Pair(container, psiElement.text))
        else if (container is SJExpression && psiElement is SmalljavaREFERENCESJSymbolID)
            sJExpressionToSJSymbolNameList.add(Pair(container, psiElement.text))
        else if (container is SJExpression && psiElement is SmalljavaREFERENCESJClassQualifiedName)
            sJExpressionToSJClassNameList.add(Pair(container, psiElement.text))
    }
}