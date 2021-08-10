package com.intellij.xtext.validation

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.xtext.bridge.BridgeResult
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import kotlin.test.assertNotNull

abstract class AbstractValidator {
    var context: ValidatorContext? = null

    protected fun warning(message: String, feature: EStructuralFeature) {
        assertNotNull(context)
        val associatedPsi = getAssociatedPsiElement(feature)
        val problemsHolder = context!!.problemsHolder
        val textRange: TextRange? = null
        val error = problemsHolder.getManager().createProblemDescriptor(
            associatedPsi,
            textRange,
            message,
            ProblemHighlightType.WARNING,
            context!!.isOnFly
        );
        problemsHolder.registerProblem(error)
    }


    protected fun error(message: String, feature: EStructuralFeature) {
        assertNotNull(context)
        val associatedPsi = getAssociatedPsiElement(feature)
        val problemsHolder = context!!.problemsHolder
        val textRange: TextRange? = null
        val error = problemsHolder.getManager()
            .createProblemDescriptor(associatedPsi, textRange, message, ProblemHighlightType.ERROR, context!!.isOnFly);
        problemsHolder.registerProblem(error)
    }

    protected fun getAssociatedPsiElement(feature: EStructuralFeature): PsiElement {
        val value = context!!.current.eGet(feature)
        val relatedPsi = context!!.bridgeContext.map.entries.firstOrNull { it.value == value }?.key
        return assertNotNull(relatedPsi)
    }


    class ValidatorContext(
        val problemsHolder: ProblemsHolder,
        val isOnFly: Boolean,
                           val bridgeContext: BridgeResult,
                           val current: EObject)
}