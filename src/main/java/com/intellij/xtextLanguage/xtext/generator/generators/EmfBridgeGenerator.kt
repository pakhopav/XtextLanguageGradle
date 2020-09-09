package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.BridgeModel
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.*
import java.io.FileOutputStream
import java.io.PrintWriter

class EmfBridgeGenerator(extension: String, val model: BridgeModel) : AbstractGenerator(extension) {
    private val capitalizedExtension = extension.capitalize()


    fun generateAllBridgeRuleFiles() {
//        generateSuperClass()
        model.bridgeRules.forEach {
            generateEmfBridgeRuleFile(it)
        }
        generateScopeFile()
        generateEmfCreator()
        generateBridgeFile()

    }

//    private fun generateSuperClass() {
//        val file = createFile("${capitalizedExtension}BridgeRule.kt", myGenDir + "/emf/rules")
//        val out = PrintWriter(FileOutputStream(file))
//        out.print("""
//            |package $packageDir.emf;
//
//            |import ${model.emfModelPath}.${model.emfModelPrefix}Factory
//            |import ${model.emfModelPath}.${model.emfModelPrefix}Package
//            |import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
//
//            |abstract class ${capitalizedExtension}BridgeRule : EmfBridgeRule {
//            |protected val eFACTORY = ${model.emfModelPrefix}Factory.eINSTANCE
//            |protected val ePACKAGE = ${model.emfModelPrefix}Package.eINSTANCE
//            |}
//        """.trimMargin("|"))
//        out.close()
//    }

    private fun generateEmfBridgeRuleFile(rule: BridgeModelRule) {
        val file = createFile("$capitalizedExtension${rule.name}BridgeRule.kt", myGenDir + "/emf/rules")
        val out = PrintWriter(FileOutputStream(file))
        out.println("package $packageDir.emf")
        rule.importStrings.forEach {
            out.println("import $it")
        }
        out.println("""
            |import org.eclipse.emf.common.util.EList
            |import com.intellij.${extension}Language.$extension.${capitalizedExtension}ParserDefinition
            |import com.intellij.${extension}Language.$extension.psi.*
            |import com.intellij.psi.PsiElement
            |import com.intellij.xtextLanguage.xtext.emf.*
            |import org.eclipse.emf.ecore.EObject
            |import org.eclipse.emf.ecore.EClass
            
            |class ${capitalizedExtension}${rule.name}BridgeRule : EmfBridgeRule {
        """.trimMargin("|"))
        generateLiteralAssignmentMethod(rule.literalAssignments, out)
        generateObjectAssignmentMethod(rule.objectAssignments, out)
        generateRewriteMethod(rule.rewrites, out)
        generateFactoryMethod(rule.returnType, out)
        generateActionMethod(rule.simpleActions, out)
        out.print("\n}")
        out.close()
    }

    private fun generateLiteralAssignmentMethod(assignments: List<AssignableObject>, out: PrintWriter) {
        out.println("    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {")
        var elseWord = ""
        assignments.forEach { assignment ->
//            val elementTypeString = if(it is AssignableKeyword) "${it.keywordText.toUpperCase()}_KEYWORD" else "${it.psiElementType.toUpperCase()}"
            val toAssignString = if (assignment.returnType.name != "String") "${assignment.returnType.name}(literal.text)" else "literal.text"
            out.print("""
                |        ${elseWord}if (
            """.trimMargin("|"))
            assignment.psiElementTypes.forEach {
                out.print("pointer.node.elementType == ${capitalizedExtension}Types.${it}")
                if (assignment.psiElementTypes.last() != it) out.print(" || ")
            }
            out.println(") {")
            out.println("""
                |            return object : LiteralAssignment {
                |                override fun assign(obj: EObject, literal: PsiElement) {
                |                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${assignment.assignment.text}" }
            """.trimMargin("|"))
            "        ${elseWord}if (pointer.node.elementType == ${capitalizedExtension}Types.${assignment.psiElementTypes}) {"
            when (assignment.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("                    obj.eSet(feature, $toAssignString)")
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                    val list = obj.eGet(feature) as EList<${assignment.returnType.name}>
                        |                    list.add($toAssignString as ${assignment.returnType.name})
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


    private fun generateObjectAssignmentMethod(assignments: List<AssignableObject>, out: PrintWriter) {
        out.println("    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {")
        var elseWord = ""
        assignments.forEach { assignment ->


            out.print("""
                |        ${elseWord}if (
            """.trimMargin("|"))
            assignment.psiElementTypes.forEach {
                out.print("pointer.node.elementType == ${capitalizedExtension}Types.${it}")
                if (assignment.psiElementTypes.last() != it) out.print(" || ")
            }
            out.println(") {")
            out.println("""
                |            return object : ObjectAssignment {
                |                override fun assign(obj: EObject, toAssign: EObject) {
                |                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${assignment.assignment.text}" }
            """.trimMargin("|"))

            when (assignment.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("                    obj.eSet(feature, toAssign)")
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                    val list = obj.eGet(feature) as EList<${assignment.returnType.name}>
                        |                    list.add(toAssign as ${assignment.returnType.name})
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

    private fun generateRewriteMethod(rewrites: List<Rewrite>, out: PrintWriter) {
        out.println("   override fun findRewrite(pointer: PsiElement): Rewrite? {")
        var elseWord = ""
        rewrites.forEach {
            out.println("""
                |       ${elseWord}if (pointer.node.elementType == ${capitalizedExtension}Types.${it.psiElementType}) {
                |           return object : Rewrite {
                |               override fun rewrite(obj: EObject): EObject {
                |                   val temp = ${getFactoryName(it.newObjectType)}.create(${getPackageName(it.newObjectType)}.${it.newObjectType.name.decapitalize()})
                |                   val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${it.assignment.text}" }
            """.trimMargin("|"))
            when (it.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("""
                        |                   temp.eSet(feature, obj)
                        |                   return temp
                    """.trimMargin("|"))
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   val list = temp.eGet(feature) as EList<${it.returnType.name}>
                        |                   list.add(obj as ${it.returnType.name})
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

    private fun generateFactoryMethod(returnType: BridgeRuleType, out: PrintWriter) {
        out.println("""
            |    override fun createObject(): EObject {
            |       return ${getFactoryName(returnType)}.create(${getPackageName(returnType)}.${returnType.name.decapitalize()})
            |    }
            |
        """.trimMargin("|"))
    }

    private fun generateActionMethod(actions: List<BridgeSimpleAction>, out: PrintWriter) {
        out.println("""
            |    override fun findAction(pointer: PsiElement): EObject? {
        """.trimMargin("|"))
        actions.forEach {
            var elseWord = ""
            out.println("""
            |        ${elseWord}if (pointer.node.elementType == ${capitalizedExtension}Types.${it.psiElementType}){
            |            return ${getFactoryName(it.returnType)}.create(${getPackageName(it.returnType)}.${it.returnType.name.decapitalize()})
            |        }
        """.trimMargin("|"))
            elseWord = "else "
        }
        out.println("""
            |        return null
            |    }
        """.trimMargin("|"))
    }

    private fun generateBridgeFile() {
        val file = createFile("${capitalizedExtension}EmfBridge.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package com.intellij.${extension}Language.${extension}.emf
            |
            |import com.intellij.${extension}Language.${extension}.psi.${capitalizedExtension}File
            |import com.intellij.${extension}Language.${extension}.psi.${capitalizedExtension}${model.rootRuleName}
            |import com.intellij.psi.PsiFile
            |import com.intellij.psi.util.PsiTreeUtil
            |import com.intellij.xtextLanguage.xtext.emf.EmfBridge
            |import org.eclipse.emf.ecore.EObject
            |
            |class ${capitalizedExtension}EmfBridge : EmfBridge {
            |    override fun createEmfModel(file: PsiFile): EObject? {
            |        if (file is ${capitalizedExtension}File) {
            |            val filePsiRoot = PsiTreeUtil.findChildOfType(file, ${capitalizedExtension}${model.rootRuleName}::class.java)
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

    private fun generateEmdCreatorImports(out: PrintWriter) {

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
        getEmfCreatorImports().forEach { out.println("import $it") }
    }

    private fun getEmfCreatorImports(): Set<String> {
        val resultSet = mutableSetOf<String>()
        model.crossReferences.forEach {
            resultSet.add(it.container.path)

        }
        model.bridgeRules.filter { it.hasName }.forEach {
            resultSet.add(it.returnType.path)
        }
        return resultSet
    }

    private fun generateEmdCreatorFields(out: PrintWriter) {
//        out.println("""
//            |    override val eFACTORY = ${model.emfModelPrefix}Factory.eINSTANCE
//            |    val ePACKAGE = ${model.emfModelPrefix}Package.eINSTANCE
//            """.trimMargin("|"))
        model.bridgeRules.forEach {
            out.println("    private val ${it.name.toUpperCase()} = ${capitalizedExtension}${it.name}BridgeRule()")
        }
        model.crossReferences.forEach {
            out.println("    private val ${createCrossReferenceMapName(it)} = mutableListOf<Pair<${it.container.name}, String>>()")
        }
    }

    private fun createCrossReferenceMapName(reference: BridgeCrossReference): String {
        return "${reference.container.name.decapitalize()}To${reference.target.name}NameList"
    }


    private fun generateGetBridgeRuleForPsiElementMethod(out: PrintWriter) {
        out.println("    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {")
        model.bridgeRules.forEach {
            out.println("""
                |        if(psiElement is ${capitalizedExtension}${it.name}){
                |            return ${it.name.toUpperCase()}
                |        } 
            """.trimMargin("|"))
        }
        model.assignmentRefactoringsInfos.forEach { refactorInfo ->
            var conditionString = ""
            refactorInfo.duplicateRuleNames.forEach {
                conditionString += " psiElement is ${capitalizedExtension}$it ||"
            }
            conditionString = conditionString.slice(0..conditionString.length - 3)
            out.println("""
                |        if($conditionString){
                |            return ${refactorInfo.originRuleName.toUpperCase()}
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
        model.bridgeRules
                .filter { it.hasName }
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
                        |        val scope = ${capitalizedExtension}Scope(modelDescriptions)
                    """.trimMargin("|"))
        model.crossReferences.forEach {
            out.println("""
                        |        ${createCrossReferenceMapName(it)}.forEach {
                        |            val container = it.first
                        |            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
                        |            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "${it.assignment.text}" }
                        |            resolvedDefinition?.let { 
                    """.trimMargin("|"))
            when (it.assignment.type) {
                AssignmentType.EQUALS -> {
                    out.println("""
                        |                   container.eSet(feature, it)
                    """.trimMargin("|"))
                }
                AssignmentType.PLUS_EQUALS -> {
                    out.println("""
                        |                   val list = container.eGet(feature) as EList<${it.target.name}>
                        |                   list.add(it as ${it.target.name})
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
        model.crossReferences.forEach {
            out.print("psiElement is $capitalizedExtension${it.psiElementName}")
            if (model.crossReferences.last() != it) {
                out.print(" || ")
            } else {
                out.println()
            }
        }
        out.println("    }")
    }

    private fun generateCreateCrossReferenceMethod(out: PrintWriter) {
        var elseWord = ""
        out.println("    override fun createCrossReference(psiElement: PsiElement, container: EObject) {")
//        model.crossReferences.joinToString(separator = "else ") {
//                    """
//                        |        if (psiElement is $capitalizedExtension${it.psiElementName})
//                        |            referenced${it.target}Map.put(container as ${it.container}, psiElement.text)
//                    """.trimMargin("|")
//        }
        model.crossReferences.forEach {
            out.println("""
                        |        ${elseWord}if (container is ${it.container.name} && psiElement is $capitalizedExtension${it.psiElementName})
                        |            ${createCrossReferenceMapName(it)}.add(Pair(container, psiElement.text))
                    """.trimMargin("|"))
            elseWord = "else "
        }
        out.println("    }")
    }


    private fun generateEmfCreator() {
        val file = createFile("${capitalizedExtension}EmfCreator.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        generateEmdCreatorImports(out)
        out.println("class ${capitalizedExtension}EmfCreator : EmfCreator() {")
        generateEmdCreatorFields(out)
        generateGetBridgeRuleForPsiElementMethod(out)
        generateRegisterObjectMethod(out)
        generateCompleteRawModelMethod(out)
        generateIsCrossReferenceMethod(out)
        generateCreateCrossReferenceMethod(out)
        out.print("}")
        out.close()
    }

    private fun generateScopeFile() {
        val file = createFile("${capitalizedExtension}Scope.kt", myGenDir + "/emf/scope")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package com.intellij.${extension}Language.${extension}.emf.scope
            |
            |import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
            |import com.intellij.xtextLanguage.xtext.emf.Scope
            |
            |class ${capitalizedExtension}Scope(val descriptions: List<ObjectDescription>) : Scope {
            |
            |    override fun getSingleElement(name: String): ObjectDescription? {
            |        return descriptions.firstOrNull { it.objectName == name }
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun getFactoryName(type: BridgeRuleType): String {
        return "${type.path.removeSuffix(type.name)}${type.prefix.capitalize()}Factory.eINSTANCE"
    }

    private fun getPackageName(type: BridgeRuleType): String {
        return "${type.path.removeSuffix(type.name)}${type.prefix.capitalize()}Package.eINSTANCE"
    }

    companion object {
        private fun PrintWriter.piped(text: String) = this.println(text.trimMargin("|"))
    }
}