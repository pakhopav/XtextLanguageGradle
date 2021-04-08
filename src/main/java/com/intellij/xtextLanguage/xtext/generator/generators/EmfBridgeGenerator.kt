package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.AssignmentType
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeDuplicateRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter
import kotlin.test.assertNotNull

class EmfBridgeGenerator(extension: String, val context: MetaContext, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    private val capitalizedExtension = extension.capitalize()
    private val relevantRules = filterRelevantParserRules()
    private val crossReferences = createCrossReferenceList()
    private val bridgeRuleGenerator = BridgeRuleFileGenerator(extension, context, rootPath)

    fun generateAll() {
        generateBridgeRuleFiles()
        generateScopeFile()
        generateEmfCreator()
        generateBridgeFile()

    }

    private fun generateBridgeRuleFiles() {
        relevantRules.forEach {
            bridgeRuleGenerator.generateEmfBridgeRuleFile(it)
        }
    }

    private fun generateEmfCreator() {
        val file = createFile("${capitalizedExtension}EmfCreator.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        generateEmfCreatorImports(out)
        out.println("class ${capitalizedExtension}EmfCreator : EmfCreator() {")
        generateEmfCreatorFields(out)
        generateCreateBridgeMethod(out)
        generateGetBridgeRuleForPsiElementMethod(out)
        generateRegisterObjectMethod(out)
        generateCompleteRawModelMethod(out)
        generateIsCrossReferenceMethod(out)
        generateCreateCrossReferenceMethod(out)
        out.print("}")
        out.close()
    }


    private fun generateCreateBridgeMethod(out: PrintWriter) {
        val rootRuleName = context.rules.first().name
        out.println(
            """
            |    override fun createBridge(psiFile: PsiFile): BridgeResult? {
            |        val rootElement = PsiTreeUtil.findChildOfType(psiFile, $extensionCapitalized$rootRuleName::class.java)
            |        assertNotNull(rootElement)
            |        val emfRoot = createModel(rootElement)
            |        emfRoot?.let {
            |            return BridgeResult(emfRoot, bridgeMap)
            |        }
            |        return null
            |    }
            """.trimMargin("|")
        )
    }

    private fun generateEmfCreatorImports(out: PrintWriter) {
        out.println(
            """
            package com.intellij.${extension}Language.${extension}.emf
            
            import com.intellij.${extension}Language.${extension}.emf.rules.*
            import com.intellij.${extension}Language.${extension}.psi.*
            import com.intellij.${extension}Language.${extension}.emf.scope.${capitalizedExtension}Scope
            import com.intellij.psi.PsiElement
            import com.intellij.xtextLanguage.xtext.emf.*
            import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
            import org.eclipse.emf.ecore.EClass
            import org.eclipse.emf.ecore.EObject
            import org.eclipse.emf.common.util.EList
            import com.intellij.psi.PsiFile
            import com.intellij.psi.util.PsiTreeUtil
            import kotlin.test.assertNotNull
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
        relevantRules.forEach { rule ->
            val rulePsiElementTypeName = NameGenerator.toGKitTypesName(rule.name)
            var conditionString = "psiElement.node.elementType == ${capitalizedExtension}Types.$rulePsiElementTypeName"

            val duplicates = context.rules.filterIsInstance<TreeDuplicateRule>().filter { it.originRuleName == rule.name }
            if (duplicates.isNotEmpty()) {
                val conditionStringExpention = duplicates
                        .map { "${capitalizedExtension}Types.${NameGenerator.toGKitTypesName(it.name)}" }
                        .joinToString(prefix = " || psiElement.node.elementType == ", separator = " || psiElement.node.elementType == ")
                conditionString += conditionStringExpention
            }
            out.println("""
                |        if($conditionString){
                |            return ${rule.name.toUpperCase()}
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
                        |            val resolvedObject = scope.getSingleElementOfType(it.second, ${it.target.getPackageInstanceString()}.${NameGenerator.toPropertyName(it.target.className)})
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



    private fun filterRelevantParserRules(): List<TreeParserRule> {
        return context.rules
                .filterIsInstance<TreeParserRule>()
                .filter { !it.isDatatypeRule && it !is TreeDuplicateRule }
    }

    private fun createCrossReferenceList(): List<BridgeCrossReference> {
        val crossReferences = mutableSetOf<BridgeCrossReference>()
        relevantRules.forEach {
            val crossReferencesNodes = it.filterNodesInSubtree { it is TreeCrossReference }.map { it as TreeCrossReference }
            crossReferencesNodes.forEach { crossReference ->
                val containerRule = context.rules.filterIsInstance<TreeParserRule>().firstOrNull { it.name == crossReference.containerRuleName }
                assertNotNull(containerRule)
                val containerClassDescriptor = containerRule.returnType
                val targetClassDescriptor = crossReference.targetType
                val psiElementName = NameGenerator.toGKitClassName(crossReference.getBnfName())
                crossReferences.add(BridgeCrossReference(crossReference.assignment, containerClassDescriptor, targetClassDescriptor, psiElementName))
            }
        }
        return crossReferences.toList()
    }

    private fun createCrossReferenceMapName(reference: BridgeCrossReference): String {
        return "${reference.container.className.decapitalize()}To${reference.target.className}NameList"
    }

    data class BridgeCrossReference(val assignment: Assignment,
                                    val container: EmfClassDescriptor,
                                    val target: EmfClassDescriptor,
                                    val psiElementName: String)
}