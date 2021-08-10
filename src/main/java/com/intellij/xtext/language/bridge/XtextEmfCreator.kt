package com.intellij.xtext.language.bridge

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtext.bridge.BridgeResult
import com.intellij.xtext.bridge.EmfBridgeRule
import com.intellij.xtext.bridge.EmfCreator
import com.intellij.xtext.bridge.ObjectDescription
import com.intellij.xtext.bridge.impl.ObjectDescriptionImpl
import com.intellij.xtext.language.bridge.rules.*
import com.intellij.xtext.language.bridge.scope.XtextScope
import com.intellij.xtext.language.psi.*
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.xtext.example.xtext.xtext.*
import org.xtext.example.xtext.xtext.Annotation
import kotlin.test.assertNotNull

class XtextEmfCreator : EmfCreator() {
    private val GRAMMAR = XtextGrammarBridgeRule()
    private val ABSTRACTRULE = XtextAbstractRuleBridgeRule()
    private val ABSTRACTMETAMODELDECLARATION = XtextAbstractMetamodelDeclarationBridgeRule()
    private val GENERATEDMETAMODEL = XtextGeneratedMetamodelBridgeRule()
    private val REFERENCEDMETAMODEL = XtextReferencedMetamodelBridgeRule()
    private val ANNOTATION = XtextAnnotationBridgeRule()
    private val PARSERRULE = XtextParserRuleBridgeRule()
    private val PARAMETER = XtextParameterBridgeRule()
    private val TYPEREF = XtextTypeRefBridgeRule()
    private val ALTERNATIVES = XtextAlternativesBridgeRule()
    private val ALTERNATIVESSUFFIX1 = XtextAlternativesSuffix1BridgeRule()
    private val CONDITIONALBRANCH = XtextConditionalBranchBridgeRule()
    private val UNORDEREDGROUP = XtextUnorderedGroupBridgeRule()
    private val UNORDEREDGROUPSUFFIX1 = XtextUnorderedGroupSuffix1BridgeRule()
    private val GROUP = XtextGroupBridgeRule()
    private val GROUPSUFFIX1 = XtextGroupSuffix1BridgeRule()
    private val ABSTRACTTOKEN = XtextAbstractTokenBridgeRule()
    private val ABSTRACTTOKENWITHCARDINALITY = XtextAbstractTokenWithCardinalityBridgeRule()
    private val ACTION = XtextActionBridgeRule()
    private val ABSTRACTTERMINAL = XtextAbstractTerminalBridgeRule()
    private val KEYWORD = XtextKeywordBridgeRule()
    private val RULECALL = XtextRuleCallBridgeRule()
    private val NAMEDARGUMENT = XtextNamedArgumentBridgeRule()
    private val LITERALCONDITION = XtextLiteralConditionBridgeRule()
    private val DISJUNCTION = XtextDisjunctionBridgeRule()
    private val CONJUNCTION = XtextConjunctionBridgeRule()
    private val NEGATION = XtextNegationBridgeRule()
    private val ATOM = XtextAtomBridgeRule()
    private val PARENTHESIZEDCONDITION = XtextParenthesizedConditionBridgeRule()
    private val PARAMETERREFERENCE = XtextParameterReferenceBridgeRule()
    private val TERMINALRULECALL = XtextTerminalRuleCallBridgeRule()
    private val PREDICATEDKEYWORD = XtextPredicatedKeywordBridgeRule()
    private val PREDICATEDRULECALL = XtextPredicatedRuleCallBridgeRule()
    private val ASSIGNMENT = XtextAssignmentBridgeRule()
    private val ASSIGNABLETERMINAL = XtextAssignableTerminalBridgeRule()
    private val PARENTHESIZEDASSIGNABLEELEMENT = XtextParenthesizedAssignableElementBridgeRule()
    private val ASSIGNABLEALTERNATIVES = XtextAssignableAlternativesBridgeRule()
    private val ASSIGNABLEALTERNATIVESSUFFIX1 = XtextAssignableAlternativesSuffix1BridgeRule()
    private val CROSSREFERENCE = XtextCrossReferenceBridgeRule()
    private val CROSSREFERENCEABLETERMINAL = XtextCrossReferenceableTerminalBridgeRule()
    private val PARENTHESIZEDELEMENT = XtextParenthesizedElementBridgeRule()
    private val PREDICATEDGROUP = XtextPredicatedGroupBridgeRule()
    private val TERMINALRULE = XtextTerminalRuleBridgeRule()
    private val TERMINALALTERNATIVES = XtextTerminalAlternativesBridgeRule()
    private val TERMINALALTERNATIVESSUFFIX1 = XtextTerminalAlternativesSuffix1BridgeRule()
    private val TERMINALGROUP = XtextTerminalGroupBridgeRule()
    private val TERMINALGROUPSUFFIX1 = XtextTerminalGroupSuffix1BridgeRule()
    private val TERMINALTOKEN = XtextTerminalTokenBridgeRule()
    private val TERMINALTOKENELEMENT = XtextTerminalTokenElementBridgeRule()
    private val PARENTHESIZEDTERMINALELEMENT = XtextParenthesizedTerminalElementBridgeRule()
    private val ABSTRACTNEGATEDTOKEN = XtextAbstractNegatedTokenBridgeRule()
    private val NEGATEDTOKEN = XtextNegatedTokenBridgeRule()
    private val UNTILTOKEN = XtextUntilTokenBridgeRule()
    private val WILDCARD = XtextWildcardBridgeRule()
    private val CHARACTERRANGE = XtextCharacterRangeBridgeRule()
    private val ENUMRULE = XtextEnumRuleBridgeRule()
    private val ENUMLITERALS = XtextEnumLiteralsBridgeRule()
    private val ENUMLITERALSSUFFIX1 = XtextEnumLiteralsSuffix1BridgeRule()
    private val ENUMLITERALDECLARATION = XtextEnumLiteralDeclarationBridgeRule()
    private val grammarToGrammarNameList = mutableListOf<Pair<Grammar, String>>()
    private val grammarToAbstractRuleNameList = mutableListOf<Pair<Grammar, String>>()
    private val generatedMetamodelToEPackageNameList = mutableListOf<Pair<GeneratedMetamodel, String>>()
    private val referencedMetamodelToEPackageNameList = mutableListOf<Pair<ReferencedMetamodel, String>>()
    private val parserRuleToAbstractRuleNameList = mutableListOf<Pair<ParserRule, String>>()
    private val typeRefToAbstractMetamodelDeclarationNameList = mutableListOf<Pair<TypeRef, String>>()
    private val typeRefToEClassifierNameList = mutableListOf<Pair<TypeRef, String>>()
    private val ruleCallToAbstractRuleNameList = mutableListOf<Pair<RuleCall, String>>()
    private val namedArgumentToParameterNameList = mutableListOf<Pair<NamedArgument, String>>()
    private val parameterReferenceToParameterNameList = mutableListOf<Pair<ParameterReference, String>>()
    private val enumLiteralDeclarationToEEnumLiteralNameList = mutableListOf<Pair<EnumLiteralDeclaration, String>>()
    private val scope = XtextScope(modelDescriptions)
    override fun createBridge(psiFile: PsiFile): BridgeResult? {
        val rootElement = PsiTreeUtil.findChildOfType(psiFile, XtextGrammar::class.java)
        assertNotNull(rootElement)
        val emfRoot = createModel(rootElement)
        emfRoot?.let {
            return BridgeResult(emfRoot, bridgeMap)
        }
        return null
    }

    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {
        if (psiElement.node.elementType == XtextTypes.GRAMMAR) {
            return GRAMMAR
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_RULE) {
            return ABSTRACTRULE
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_METAMODEL_DECLARATION) {
            return ABSTRACTMETAMODELDECLARATION
        }
        if (psiElement.node.elementType == XtextTypes.GENERATED_METAMODEL) {
            return GENERATEDMETAMODEL
        }
        if (psiElement.node.elementType == XtextTypes.REFERENCED_METAMODEL) {
            return REFERENCEDMETAMODEL
        }
        if (psiElement.node.elementType == XtextTypes.ANNOTATION) {
            return ANNOTATION
        }
        if (psiElement.node.elementType == XtextTypes.PARSER_RULE) {
            return PARSERRULE
        }
        if (psiElement.node.elementType == XtextTypes.PARAMETER) {
            return PARAMETER
        }
        if (psiElement.node.elementType == XtextTypes.TYPE_REF) {
            return TYPEREF
        }
        if (psiElement.node.elementType == XtextTypes.ALTERNATIVES) {
            return ALTERNATIVES
        }
        if (psiElement.node.elementType == XtextTypes.ALTERNATIVES_SUFFIX_1) {
            return ALTERNATIVESSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.CONDITIONAL_BRANCH) {
            return CONDITIONALBRANCH
        }
        if (psiElement.node.elementType == XtextTypes.UNORDERED_GROUP) {
            return UNORDEREDGROUP
        }
        if (psiElement.node.elementType == XtextTypes.UNORDERED_GROUP_SUFFIX_1) {
            return UNORDEREDGROUPSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.GROUP) {
            return GROUP
        }
        if (psiElement.node.elementType == XtextTypes.GROUP_SUFFIX_1) {
            return GROUPSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_TOKEN) {
            return ABSTRACTTOKEN
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_TOKEN_WITH_CARDINALITY) {
            return ABSTRACTTOKENWITHCARDINALITY
        }
        if (psiElement.node.elementType == XtextTypes.ACTION) {
            return ACTION
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_TERMINAL) {
            return ABSTRACTTERMINAL
        }
        if (psiElement.node.elementType == XtextTypes.KEYWORD || psiElement.node.elementType == XtextTypes.KEYWORD_1) {
            return KEYWORD
        }
        if (psiElement.node.elementType == XtextTypes.RULE_CALL) {
            return RULECALL
        }
        if (psiElement.node.elementType == XtextTypes.NAMED_ARGUMENT) {
            return NAMEDARGUMENT
        }
        if (psiElement.node.elementType == XtextTypes.LITERAL_CONDITION) {
            return LITERALCONDITION
        }
        if (psiElement.node.elementType == XtextTypes.DISJUNCTION) {
            return DISJUNCTION
        }
        if (psiElement.node.elementType == XtextTypes.CONJUNCTION || psiElement.node.elementType == XtextTypes.CONJUNCTION_1) {
            return CONJUNCTION
        }
        if (psiElement.node.elementType == XtextTypes.NEGATION || psiElement.node.elementType == XtextTypes.NEGATION_1) {
            return NEGATION
        }
        if (psiElement.node.elementType == XtextTypes.ATOM) {
            return ATOM
        }
        if (psiElement.node.elementType == XtextTypes.PARENTHESIZED_CONDITION) {
            return PARENTHESIZEDCONDITION
        }
        if (psiElement.node.elementType == XtextTypes.PARAMETER_REFERENCE) {
            return PARAMETERREFERENCE
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_RULE_CALL) {
            return TERMINALRULECALL
        }
        if (psiElement.node.elementType == XtextTypes.PREDICATED_KEYWORD) {
            return PREDICATEDKEYWORD
        }
        if (psiElement.node.elementType == XtextTypes.PREDICATED_RULE_CALL) {
            return PREDICATEDRULECALL
        }
        if (psiElement.node.elementType == XtextTypes.ASSIGNMENT) {
            return ASSIGNMENT
        }
        if (psiElement.node.elementType == XtextTypes.ASSIGNABLE_TERMINAL) {
            return ASSIGNABLETERMINAL
        }
        if (psiElement.node.elementType == XtextTypes.PARENTHESIZED_ASSIGNABLE_ELEMENT) {
            return PARENTHESIZEDASSIGNABLEELEMENT
        }
        if (psiElement.node.elementType == XtextTypes.ASSIGNABLE_ALTERNATIVES) {
            return ASSIGNABLEALTERNATIVES
        }
        if (psiElement.node.elementType == XtextTypes.ASSIGNABLE_ALTERNATIVES_SUFFIX_1) {
            return ASSIGNABLEALTERNATIVESSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.CROSS_REFERENCE) {
            return CROSSREFERENCE
        }
        if (psiElement.node.elementType == XtextTypes.CROSS_REFERENCEABLE_TERMINAL) {
            return CROSSREFERENCEABLETERMINAL
        }
        if (psiElement.node.elementType == XtextTypes.PARENTHESIZED_ELEMENT) {
            return PARENTHESIZEDELEMENT
        }
        if (psiElement.node.elementType == XtextTypes.PREDICATED_GROUP) {
            return PREDICATEDGROUP
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_RULE) {
            return TERMINALRULE
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_ALTERNATIVES) {
            return TERMINALALTERNATIVES
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_ALTERNATIVES_SUFFIX_1) {
            return TERMINALALTERNATIVESSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_GROUP) {
            return TERMINALGROUP
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_GROUP_SUFFIX_1) {
            return TERMINALGROUPSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_TOKEN) {
            return TERMINALTOKEN
        }
        if (psiElement.node.elementType == XtextTypes.TERMINAL_TOKEN_ELEMENT) {
            return TERMINALTOKENELEMENT
        }
        if (psiElement.node.elementType == XtextTypes.PARENTHESIZED_TERMINAL_ELEMENT) {
            return PARENTHESIZEDTERMINALELEMENT
        }
        if (psiElement.node.elementType == XtextTypes.ABSTRACT_NEGATED_TOKEN) {
            return ABSTRACTNEGATEDTOKEN
        }
        if (psiElement.node.elementType == XtextTypes.NEGATED_TOKEN) {
            return NEGATEDTOKEN
        }
        if (psiElement.node.elementType == XtextTypes.UNTIL_TOKEN) {
            return UNTILTOKEN
        }
        if (psiElement.node.elementType == XtextTypes.WILDCARD) {
            return WILDCARD
        }
        if (psiElement.node.elementType == XtextTypes.CHARACTER_RANGE) {
            return CHARACTERRANGE
        }
        if (psiElement.node.elementType == XtextTypes.ENUM_RULE) {
            return ENUMRULE
        }
        if (psiElement.node.elementType == XtextTypes.ENUM_LITERALS) {
            return ENUMLITERALS
        }
        if (psiElement.node.elementType == XtextTypes.ENUM_LITERALS_SUFFIX_1) {
            return ENUMLITERALSSUFFIX1
        }
        if (psiElement.node.elementType == XtextTypes.ENUM_LITERAL_DECLARATION) {
            return ENUMLITERALDECLARATION
        }
        return null
    }

    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
        obj?.let {
            if (obj is Grammar) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is GeneratedMetamodel) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is Annotation) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is ParserRule) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is Parameter) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is TerminalRule) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is EnumRule) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else return
        }
    }

    override fun completeRawModel() {
        grammarToGrammarNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.grammar)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "usedGrammars" }
                val list = container.eGet(feature) as EList<Grammar>
                list.add(it as Grammar)
            }
        }
        grammarToAbstractRuleNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(
                it.second,
                org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.abstractRule
            )
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "hiddenTokens" }
                val list = container.eGet(feature) as EList<AbstractRule>
                list.add(it as AbstractRule)
            }
        }
        generatedMetamodelToEPackageNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.eclipse.emf.ecore.EcorePackage.eINSTANCE.ePackage)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "ePackage" }
                container.eSet(feature, it)
            }
        }
        referencedMetamodelToEPackageNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.eclipse.emf.ecore.EcorePackage.eINSTANCE.ePackage)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "ePackage" }
                container.eSet(feature, it)
            }
        }
        parserRuleToAbstractRuleNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(
                it.second,
                org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.abstractRule
            )
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "hiddenTokens" }
                val list = container.eGet(feature) as EList<AbstractRule>
                list.add(it as AbstractRule)
            }
        }
        typeRefToAbstractMetamodelDeclarationNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(
                it.second,
                org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.abstractMetamodelDeclaration
            )
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "metamodel" }
                container.eSet(feature, it)
            }
        }
        typeRefToEClassifierNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.eclipse.emf.ecore.EcorePackage.eINSTANCE.eClassifier)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "classifier" }
                container.eSet(feature, it)
            }
        }
        ruleCallToAbstractRuleNameList.forEach {
            val container = it.first
            val resolvedObject = scope.getSingleElementOfType(
                it.second,
                org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.abstractRule
            )
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "rule" }
                container.eSet(feature, it)
            }
        }
        namedArgumentToParameterNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.parameter)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "parameter" }
                container.eSet(feature, it)
            }
        }
        parameterReferenceToParameterNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.parameter)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "parameter" }
                container.eSet(feature, it)
            }
        }
        enumLiteralDeclarationToEEnumLiteralNameList.forEach {
            val container = it.first
            val resolvedObject =
                scope.getSingleElementOfType(it.second, org.eclipse.emf.ecore.EcorePackage.eINSTANCE.eEnumLiteral)
            resolvedObject?.let {
                val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "enumLiteral" }
                container.eSet(feature, it)
            }
        }
    }

    override fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is XtextREFERENCEGrammarGrammarID || psiElement is XtextREFERENCEAbstractRuleRuleID || psiElement is XtextREFERENCEEPackageSTRING || psiElement is XtextREFERENCEEPackageSTRING || psiElement is XtextREFERENCEAbstractRuleRuleID || psiElement is XtextREFERENCEAbstractMetamodelDeclarationID || psiElement is XtextREFERENCEEClassifierID || psiElement is XtextREFERENCEAbstractRuleRuleID || psiElement is XtextREFERENCEParameterID || psiElement is XtextREFERENCEParameterID || psiElement is XtextREFERENCEEEnumLiteralID
    }

    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (container is Grammar && psiElement is XtextREFERENCEGrammarGrammarID)
            grammarToGrammarNameList.add(Pair(container, psiElement.text))
        else if (container is Grammar && psiElement is XtextREFERENCEAbstractRuleRuleID)
            grammarToAbstractRuleNameList.add(Pair(container, psiElement.text))
        else if (container is GeneratedMetamodel && psiElement is XtextREFERENCEEPackageSTRING)
            generatedMetamodelToEPackageNameList.add(Pair(container, psiElement.text))
        else if (container is ReferencedMetamodel && psiElement is XtextREFERENCEEPackageSTRING)
            referencedMetamodelToEPackageNameList.add(Pair(container, psiElement.text))
        else if (container is ParserRule && psiElement is XtextREFERENCEAbstractRuleRuleID)
            parserRuleToAbstractRuleNameList.add(Pair(container, psiElement.text))
        else if (container is TypeRef && psiElement is XtextREFERENCEAbstractMetamodelDeclarationID)
            typeRefToAbstractMetamodelDeclarationNameList.add(Pair(container, psiElement.text))
        else if (container is TypeRef && psiElement is XtextREFERENCEEClassifierID)
            typeRefToEClassifierNameList.add(Pair(container, psiElement.text))
        else if (container is RuleCall && psiElement is XtextREFERENCEAbstractRuleRuleID)
            ruleCallToAbstractRuleNameList.add(Pair(container, psiElement.text))
        else if (container is NamedArgument && psiElement is XtextREFERENCEParameterID)
            namedArgumentToParameterNameList.add(Pair(container, psiElement.text))
        else if (container is ParameterReference && psiElement is XtextREFERENCEParameterID)
            parameterReferenceToParameterNameList.add(Pair(container, psiElement.text))
        else if (container is EnumLiteralDeclaration && psiElement is XtextREFERENCEEEnumLiteralID)
            enumLiteralDeclarationToEEnumLiteralNameList.add(Pair(container, psiElement.text))
    }
}