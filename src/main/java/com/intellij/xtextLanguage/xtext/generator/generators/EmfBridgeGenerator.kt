package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.AssignmentType
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EcoreFactory
import org.eclipse.emf.ecore.EcorePackage
import java.io.FileOutputStream
import java.io.PrintWriter
import kotlin.test.assertNotNull

class EmfBridgeGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {
    private val capitalizedExtension = extension.capitalize()
    private val relevantRules = filterRelevantParserRules()
    private val crossReferences = createCrossReferenceList()

    fun generateAll() {
        generateBridgeRuleFiles()
        generateScopeFile()
        generateEmfCreator()
        generateBridgeFile()

    }

    private fun generateEmfCreator() {

        val d2 = EcorePackage.eINSTANCE.getEClassifier("EInt")
        d2 as EDataType
        val i = EcoreFactory.eINSTANCE.createFromString(d2, "5")
//        val d = SmallJavaPackage.eINSTANCE.sjAccessLevel
//        val access = SmallJavaFactory.eINSTANCE.createFromString(d, "private")
//        val fild = SmallJavaFactory.eINSTANCE.createSJMember()
//        fild.access = access as SJAccessLevel


        val file = createFile("${capitalizedExtension}EmfCreator.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        generateEmfCreatorImports(out)
        out.println("class ${capitalizedExtension}EmfCreator : EmfCreator() {")
        generateEmfCreatorFields(out)
        generateGetBridgeRuleForPsiElementMethod(out)
        generateRegisterObjectMethod(out)
        generateCompleteRawModelMethod(out)
        generateIsCrossReferenceMethod(out)
        generateCreateCrossReferenceMethod(out)
        out.print("}")
        out.close()
    }

    private fun generateEmfCreatorImports(out: PrintWriter) {
        out.println("""
            package com.intellij.${extension}Language.${extension}.emf
            import com.intellij.${extension}Language.${extension}.psi.*
            import com.intellij.${extension}Language.${extension}.emf.scope.${capitalizedExtension}Scope
            import com.intellij.psi.PsiElement
            import com.intellij.xtextLanguage.xtext.emf.*
            import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
            import org.eclipse.emf.ecore.EClass
            import org.eclipse.emf.ecore.EObject
            import org.eclipse.emf.common.util.EList
            """.trimIndent())
        relevantRules.map { it.returnType }.distinct().forEach {
            out.println("import ${it.classPath}")
        }
    }

    private fun generateEmfCreatorFields(out: PrintWriter) {
        relevantRules.forEach {
            out.println("    private val ${it.name.toUpperCase()} = ${capitalizedExtension}${it.name}BridgeRule()")
        }
        crossReferences.forEach {
            out.println("    private val ${createCrossReferenceMapName(it)} = mutableListOf<Pair<${it.container.className}, String>>()")
        }
        out.println("    private val scope = ${capitalizedExtension}Scope(modelDescriptions)")

    }

    private fun generateGetBridgeRuleForPsiElementMethod(out: PrintWriter) {
        out.println("    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {")
        relevantRules.forEach {
            val rulePsiElementTypeName = NameGenerator.toGKitTypesName(it.name)
            out.println("""
                |        if(psiElement.node.elementType == ${capitalizedExtension}Types.$rulePsiElementTypeName){
                |            return ${it.name.toUpperCase()}
                |        } 
            """.trimMargin("|"))
        }
        context.rules.filterIsInstance<DuplicateRule>().groupBy { it.originRuleName }.forEach {
            val conditionString = it.value
                    .map { "${capitalizedExtension}${it.name}" }
                    .joinToString(prefix = " psiElement is ", separator = " || psiElement is")
            out.println("""
                |        if($conditionString){
                |            return ${it.key.toUpperCase()}
                |        } 
            """.trimMargin("|"))
        }

        out.println("        return null\n    }")
    }


    private fun generateRegisterObjectMethod(out: PrintWriter) {
        var elseWord = ""
        out.println("""
                |    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
                |        obj?.let {            
            """.trimMargin("|"))
        relevantRules
                .filter { it.hasNameFeature() }
                .forEach {
                    out.println("""
                        |            ${elseWord}if (obj is ${it.name}) {     
                        |                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                        |                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
                        |            }     
                    """.trimMargin("|"))
                    elseWord = "else "
                }
        out.println("""
                        |            else return  
                        |        }
                        |    }
                    """.trimMargin("|"))
    }

    private fun generateCompleteRawModelMethod(out: PrintWriter) {
        out.println("""
                        |    override fun completeRawModel() {
                    """.trimMargin("|"))
        crossReferences.forEach {
            out.println("""
                        |        ${createCrossReferenceMapName(it)}.forEach {
                        |            val container = it.first
                        |            val resolvedObject = scope.getSingleElementOfType(it.second, ${getPackageName(it.target)}.${NameGenerator.toPropertyName(it.target.className)})
                        |            resolvedObject?.let { 
                        |               val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${it.assignment.featureName}" }
                    """.trimMargin("|"))
            when (it.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("""
                        |                   container.eSet(feature, it)
                    """.trimMargin("|"))
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   val list = container.eGet(feature) as EList<${it.target.className}>
                        |                   list.add(it as ${it.target.className})
                    """.trimMargin("|"))
                }
                AssignmentType.QUESTION_EQUALS -> {
                    out.println("""
                        |                   container.eSet(feature, true)
                    """.trimMargin("|"))
                }
            }
            out.println("""
                        |            }
                        |        }
                    """.trimMargin("|"))
        }
        out.println("    }")
    }

    private fun generateIsCrossReferenceMethod(out: PrintWriter) {
        out.print("""
                        |    override fun isCrossReference(psiElement: PsiElement): Boolean {
                        |        return 
                    """.trimMargin("|"))
        out.println(crossReferences.map { "$capitalizedExtension${it.psiElementName}" }.joinToString(prefix = "psiElement is ", separator = " || psiElement is "))
        out.println("    }")
    }

    private fun generateCreateCrossReferenceMethod(out: PrintWriter) {
        var elseWord = ""
        out.println("    override fun createCrossReference(psiElement: PsiElement, container: EObject) {")
        crossReferences.forEach {
            out.println("""
                        |        ${elseWord}if (container is ${it.container.className} && psiElement is $capitalizedExtension${NameGenerator.toGKitClassName(it.psiElementName)})
                        |            ${createCrossReferenceMapName(it)}.add(Pair(container, psiElement.text))
                    """.trimMargin("|"))
            elseWord = "else "
        }
        out.println("    }")
    }



    private fun generateBridgeRuleFiles() {
        relevantRules.forEach {
            generateEmfBridgeRuleFile(it)
        }
    }

    private fun generateEmfBridgeRuleFile(rule: TreeRule) {
        val file = createFile("$capitalizedExtension${rule.name}BridgeRule.kt", myGenDir + "/emf/rules")
        val out = PrintWriter(FileOutputStream(file))
        out.println("package $packageDir.emf")

        out.println("""
            |import com.intellij.${extension}Language.$extension.psi.*
            |import com.intellij.psi.PsiElement
            |import com.intellij.xtextLanguage.xtext.emf.*
            |import org.eclipse.emf.ecore.EObject
            |import org.eclipse.emf.ecore.EDataType
            |
            |class ${capitalizedExtension}${rule.name}BridgeRule : EmfBridgeRule {
        """.trimMargin("|"))
        generateLiteralAssignmentMethod(rule, out)
        generateObjectAssignmentMethod(rule, out)
        generateRewriteMethod(rule, out)
        generateFactoryMethod(rule, out)
        generateActionMethod(rule, out)
        out.print("\n}")
        out.close()
    }

    private fun generateLiteralAssignmentMethod(rule: TreeRule, out: PrintWriter) {
        out.println("    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {")
        var elseWord = ""
        val literalAssignments = context.findLiteralAssignmentsInRule(rule)
        literalAssignments.forEach { node ->
            val assignableType = if (node is TreeRuleCall) context.getRuleByName(node.getBnfName()).returnType else EmfClassDescriptor.STRING
            val psiElementTypeName = node.getPsiElementTypeName()
            assertNotNull(psiElementTypeName)
            out.print("""
                |        ${elseWord}if (
            """.trimMargin("|"))
            out.println("pointer.node.elementType == ${capitalizedExtension}Types.$psiElementTypeName) {")
            out.println("""
                |            return object : LiteralAssignment {
                |                override fun assign(obj: EObject, literal: PsiElement) {
                |                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${node.assignment!!.featureName}" }
                |                    val ePackage = ${getPackageName(assignableType)}
                |                    val classifier = ePackage.getEClassifier("${assignableType.className}") as EDataType
                |                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)
                                    
            """.trimMargin("|"))
            when (node.assignment!!.type) {
                AssignmentType.EQUALS -> {
                    out.println("                    obj.eSet(feature, value)")
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   feature?.let {
                        |                       Helper.esetMany(obj, it, value)
                        |                   }
                    """.trimMargin("|"))
                }
                AssignmentType.QUESTION_EQUALS -> {
                    out.println("                    obj.eSet(feature, true)")
                }
            }
            out.println("""
                |                }
                |            }
                |        }
            """.trimMargin("|"))
            if (elseWord.isEmpty()) elseWord = "else "
        }
        out.println("""
            |        return null
            |    }
        """.trimMargin("|"))
    }


    private fun generateObjectAssignmentMethod(rule: TreeRule, out: PrintWriter) {
        out.println("    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {")
        var elseWord = ""
        val objectAssignments = context.findObjectAssignmentsInRule(rule)
        objectAssignments.forEach { node ->
            val psiElementTypeName = node.getPsiElementTypeName()
            val psiElementClassName = "$capitalizedExtension${NameGenerator.toGKitClassName(node.getBnfName())}"
            out.print("""
                |        ${elseWord}if (
            """.trimMargin("|"))
            out.println("pointer is $psiElementClassName) {")

            out.println("""
                |            return object : ObjectAssignment {
                |                override fun assign(obj: EObject, toAssign: EObject) {
                |                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${node.assignment!!.featureName}" }
            """.trimMargin("|"))

            when (node.assignment!!.type) {
                AssignmentType.EQUALS -> {
                    out.println("                    obj.eSet(feature, toAssign)")
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   feature?.let {
                        |                       Helper.esetMany(obj, it, toAssign)
                        |                   }
                    """.trimMargin("|"))
                }
                AssignmentType.QUESTION_EQUALS -> {
                    out.println("                    obj.eSet(feature, true)")
                }
            }
            out.println("""
                |                }
                |            }
                |        }
            """.trimMargin("|"))
            if (elseWord.isEmpty()) elseWord = "else "
        }
        out.println("""
            |        return null
            |    }
        """.trimMargin("|"))
    }


    private fun generateRewriteMethod(rule: TreeRule, out: PrintWriter) {
        out.println("   override fun findRewrite(pointer: PsiElement): Rewrite? {")
        var elseWord = ""
        val nodesWithRewrite = rule.filterNodesInSubtree { it is TreeLeaf && it.rewrite != null }.map { it as TreeLeaf }
        nodesWithRewrite.forEach { node ->
            val newObjectType = context.getClassDescriptionByName(node.rewrite!!.newObjectClassName)
            assertNotNull(newObjectType)
            val psiElementType = node.getPsiElementTypeName()
            out.println("""
                |       ${elseWord}if (pointer.node.elementType == ${capitalizedExtension}Types.${psiElementType}) {
                |           return object : Rewrite {
                |               override fun rewrite(obj: EObject): EObject {
                |                   val temp = ${getFactoryName(newObjectType)}.create(${getPackageName(newObjectType)}.${NameGenerator.toPropertyName(newObjectType.className)})
                |                   val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${node.rewrite!!.assignment.featureName}" }
            """.trimMargin("|"))
            when (node.rewrite!!.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("""
                        |                   temp.eSet(feature, obj)
                        |                   return temp
                    """.trimMargin("|"))
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   feature?.let {
                        |                       Helper.esetMany(temp, it, obj)
                        |                   }
                        |                   return temp
                    """.trimMargin("|"))
                }
                AssignmentType.QUESTION_EQUALS -> {
                    out.println("""
                        |                   temp.eSet(feature, true)
                        |                   return temp
                    """.trimMargin("|"))
                }
            }
            out.println("""
                |               }
                |           }
                |       }
            """.trimMargin("|"))
            if (elseWord.isEmpty()) elseWord = "else "
        }
        out.println("""
            |       return null
            |   }
        """.trimMargin("|"))

    }

    private fun generateFactoryMethod(rule: TreeRule, out: PrintWriter) {
        out.println("""
            |    override fun createObject(): EObject {
            |       return ${getFactoryName(rule.returnType)}.create(${getPackageName(rule.returnType)}.${NameGenerator.toPropertyName(rule.returnType.className)})
            |    }
            |
        """.trimMargin("|"))
    }


    private fun generateActionMethod(rule: TreeRule, out: PrintWriter) {
        out.println("""
            |    override fun findAction(pointer: PsiElement): EObject? {
        """.trimMargin("|"))
        val nodesWithSimpleAction = rule.filterNodesInSubtree { it is TreeLeaf && it.simpleActionText != null }.map { it as TreeLeaf }
        var elseWord = ""
        nodesWithSimpleAction.forEach { node ->
            val newObjectType = context.getClassDescriptionByName(node.simpleActionText!!)
            assertNotNull(newObjectType)
            val psiElementType = node.getPsiElementTypeName()
            out.println("""
            |        ${elseWord}if (pointer.node.elementType == ${capitalizedExtension}Types.${psiElementType}){
            |            return ${getFactoryName(newObjectType)}.create(${getPackageName(newObjectType)}.${NameGenerator.toPropertyName(newObjectType.className)})
            |        }
        """.trimMargin("|"))
            elseWord = "else "
        }
        out.println("""
            |        return null
            |    }
        """.trimMargin("|"))
    }


    private fun generateScopeFile() {
        val file = createFile("${capitalizedExtension}Scope.kt", myGenDir + "/emf/scope")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package com.intellij.${extension}Language.${extension}.emf.scope
            |
            |import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
            |import com.intellij.xtextLanguage.xtext.emf.impl.ScopeImpl
            |
            |class ${capitalizedExtension}Scope(descriptions: List<ObjectDescription>) : ScopeImpl(descriptions) {
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateBridgeFile() {
        val file = createFile("${capitalizedExtension}EmfBridge.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        val rootRuleName = context.rules.first().name
        out.print("""
            |package com.intellij.${extension}Language.${extension}.emf
            |
            |import com.intellij.${extension}Language.${extension}.psi.${capitalizedExtension}File
            |import com.intellij.${extension}Language.${extension}.psi.${capitalizedExtension}${rootRuleName}
            |import com.intellij.psi.PsiFile
            |import com.intellij.psi.util.PsiTreeUtil
            |import com.intellij.xtextLanguage.xtext.emf.EmfBridge
            |import org.eclipse.emf.ecore.EObject
            |
            |class ${capitalizedExtension}EmfBridge : EmfBridge {
            |    override fun createEmfModel(file: PsiFile): EObject? {
            |        if (file is ${capitalizedExtension}File) {
            |            val filePsiRoot = PsiTreeUtil.findChildOfType(file, ${capitalizedExtension}${rootRuleName}::class.java)
            |            filePsiRoot?.let {
            |                val emfCreator = ${capitalizedExtension}EmfCreator()
            |                return emfCreator.createModel(it)
            |            }
            |        }
            |        return null
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun getPathsToImport(rule: TreeRule): List<EmfClassDescriptor> {
        val nodes = context.findLiteralAssignmentsInRule(rule)
        return nodes.filterIsInstance<TreeRuleCall>()
                .map { getDescriptorOfCalledRule(it) }
                .filter { it != EmfClassDescriptor.STRING }
                .filterNotNull().distinct()
    }

    private fun getTypeNameOfCalledRule(node: TreeRuleCall): String {
        return getDescriptorOfCalledRule(node)?.className ?: "String"
    }

    private fun getDescriptorOfCalledRule(node: TreeRuleCall): EmfClassDescriptor? {
        context.getParserRuleByName(node.getBnfName())?.let {
            return it.returnType
        }
        context.terminalRules.firstOrNull { it.name == node.getBnfName() }?.let {
            return if (it.returnTypeText == "String") null else context.getClassDescriptionByName(it.returnTypeText)
        }
        return null
    }

    private fun filterRelevantParserRules(): List<TreeParserRule> {
        return context.rules
                .filterIsInstance<TreeParserRule>()
                .filter { !it.isDatatypeRule }
    }

    private fun createCrossReferenceList(): List<BridgeCrossReference> {
        val crossReferences = mutableSetOf<BridgeCrossReference>()
        relevantRules.forEach {
            val crossReferencesNodes = it.filterNodesInSubtree { it is TreeCrossReference }.map { it as TreeCrossReference }
            crossReferencesNodes.forEach {
                val containerRule = context.getParserRuleByName(it.containerRuleName) as TreeParserRule
                assertNotNull(containerRule)
                val containerClassDescriptor = containerRule.returnType
                val targetClassDescriptor = it.targetType
                val psiElementName = NameGenerator.toGKitClassName(it.getBnfName())
                crossReferences.add(BridgeCrossReference(it.assignment, containerClassDescriptor, targetClassDescriptor, psiElementName))
            }
        }
        return crossReferences.toList()
    }

    private fun createCrossReferenceMapName(reference: BridgeCrossReference): String {
        return "${reference.container.className.decapitalize()}To${reference.target.className}NameList"
    }

    private fun getFactoryName(type: EmfClassDescriptor): String {
        return "${type.classPath.removeSuffix(type.className)}${type.packagePrefix.capitalize()}Factory.eINSTANCE"
    }

    private fun getPackageName(type: EmfClassDescriptor): String {
        return "${type.packagePath}.${type.packagePrefix.capitalize()}Package.eINSTANCE"
    }

    data class BridgeCrossReference(val assignment: Assignment,
                                    val container: EmfClassDescriptor,
                                    val target: EmfClassDescriptor,
                                    val psiElementName: String) {


    }
}