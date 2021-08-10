package com.intellij.xtext.module

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.impl.libraries.ApplicationLibraryTable
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.Key
import com.intellij.openapi.util.Pair
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.XmlElementFactory
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlTag
import com.intellij.util.IncorrectOperationException
import com.intellij.util.download.DownloadableFileDescription
import com.intellij.util.download.DownloadableFileService
import com.intellij.xtext.metamodel.model.MetaContext
import com.intellij.xtext.metamodel.model.elements.names.NameGenerator
import com.intellij.xtext.metamodel.model.elements.tree.TreeCrossReference
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtext.metamodel.model.elements.tree.TreeParserRule
import org.apache.commons.io.FileUtils
import org.intellij.grammar.actions.BnfRunJFlexAction
import org.intellij.grammar.actions.GenerateAction
import java.io.File
import kotlin.test.assertNotNull


class GrammarKitGenerator(
    private val languageExtension: String,
    private val context: MetaContext,
    private val project: Project
) {

    private val basePath: String = project.basePath ?: throw Exception("Default project has no base path")
    private val separator = File.separator

    constructor(builder: XtextModuleBuilder, project: Project) : this(
        builder.langExtension,
        builder.context!!,
        project
    )


    companion object {
        val XTEXT_MODULE_BUILDER_KEY = Key<XtextModuleBuilder>(
            GrammarKitGenerator::class.qualifiedName + ":XtextModuleBuilder"
        )
    }

    fun launchGrammarKitGeneration(cleanGenDir: Boolean = false) {
        WriteCommandAction.writeCommandAction(project).compute<Unit, Throwable> {
//            updatePluginXml(project)
            generateParser(cleanGenDir)
            generateLexer()
        }
    }


    //FIXME remove method from the class
    fun updatePluginXml() {
        WriteCommandAction.writeCommandAction(project).compute<Unit, Throwable> {
            val pluginXmlFilePath =
                "$basePath${separator}src${separator}main${separator}resources${separator}META-INF${separator}plugin.xml"
            val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(pluginXmlFilePath)
            assertNotNull(virtualFile)
            val file = PsiManager.getInstance(project).findFile(virtualFile)
            val factory = XmlElementFactory.getInstance(project)
            val extensionsTags =
                PsiTreeUtil.findChildrenOfType(file, XmlTag::class.java).filter { it.name == "extensions" }
            var extensionsTag = extensionsTags[0]
            if (extensionsTags.size > 1) {
                for (tag in extensionsTags) {
                    if (tag.getAttribute("defaultExtensionNs")?.value == "com.intellij") {
                        extensionsTag = tag
                    }
                }
            }
            assertNotNull(extensionsTag)
            val extensionCapitalized = languageExtension.capitalize()
            val packageDir = "${languageExtension}Language.$languageExtension"

            try {
                addOrReplaceChildTag(
                    extensionsTag,
                    factory.createTagFromText("<fileTypeFactory implementation=\"${packageDir}.${extensionCapitalized}FileTypeFactory\"/>")
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<lang.parserDefinition language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}ParserDefinition\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<lang.syntaxHighlighterFactory language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}SyntaxHighlighterFactory\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<completion.contributor language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}CompletionContributor\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<psi.referenceContributor language=\"$extensionCapitalized\" implementation=\"${packageDir}.${extensionCapitalized}ReferenceContributor\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<lang.refactoringSupport language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}RefactoringSupportProvider\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguage(
                    extensionsTag,
                    factory.createTagFromText("<lang.findUsagesProvider language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}FindUsagesProvider\"/>"),
                    extensionCapitalized
                )
                addOrReplaceChildTagForSpecificLanguageAndShortName(
                    extensionsTag,
                    factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}Inspection\" displayName=\"$extensionCapitalized inspection\" enabledByDefault=\"true\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}Inspection\"/>"),
                    extensionCapitalized,
                    "${extensionCapitalized}Inspection"
                )
                addOrReplaceChildTagForSpecificLanguageAndShortName(
                    extensionsTag,
                    factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}ReferencesInspection\" displayName=\"$extensionCapitalized reference inspection\" enabledByDefault=\"true\" level=\"ERROR\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}ReferencesInspection\"/>"),
                    extensionCapitalized,
                    "${extensionCapitalized}ReferencesInspection"
                )
                val crossReferences = context?.rules?.filterIsInstance<TreeParserRule>()
                    ?.flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }
                    ?.distinctBy { it.getBnfName() }
                crossReferences?.forEach {
                    val referenceName = NameGenerator.toGKitClassName(it.getBnfName())
                    val newTag =
                        factory.createTagFromText("<lang.elementManipulator forClass=\"${packageDir}.impl.${extensionCapitalized}${referenceName}Impl\" implementationClass=\"${packageDir}.psi.${extensionCapitalized}${referenceName}Manipulator\"/>")
                    extensionsTag.getSubTag(newTag.name)?.let {
                        if (it.getAttribute("forClass")?.value == "${packageDir}.impl.${extensionCapitalized}${referenceName}Impl") {
                            it.replace(newTag)
                        } else {
                            extensionsTag.addSubTag(newTag, false)
                        }
                    } ?: run {
                        extensionsTag.addSubTag(newTag, false)
                    }
                }
            } catch (ex: IncorrectOperationException) {
                ex.message?.let { error(it) }
            }


            //======================
//            val pluginXmlFile = File(pluginXmlFilePath)
//            val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pluginXmlFile)
//            assertNotNull(document)
//            val extensionsTagsList = document.getElementsByTagName("extensions")
//
//
//            findNodeChildrenByName(extensionsTag, "fileTypeFactory")
//            var children = extensionsTag.findChildrenByName("fileTypeFactory")
//            var newElement = document.createElement("fileTypeFactory")
//            newElement.attributes.setNamedItem(document.createAttributeWithValue("implementation","${packageDir}.${extensionCapitalized}FileTypeFactory"))
//            if(children.isEmpty()){
//                extensionsTag.appendChild(newElement)
//            }else{
//                extensionsTag.replaceChild(newElement, children[0])
//            }
//
//            children = extensionsTag.findChildrenByName("lang.parserDefinition")
//            newElement = document.createElement("lang.parserDefinition")
//            newElement.attributes.setNamedItem(document.createAttributeWithValue("language", extensionCapitalized))
//            newElement.attributes.setNamedItem(document.createAttributeWithValue("implementationClass", "${packageDir}.${extensionCapitalized}ParserDefinition"))
//            if(children.isEmpty()){
//                extensionsTag.appendChild(newElement)
//            }else{
//                var oldChild: Node? = null
//                children.forEach {
//                    if(it.attributes.getNamedItem("language").nodeValue == extensionCapitalized) oldChild = it
//                }
//                if(oldChild == null){
//                    extensionsTag.appendChild(newElement)
//                }else{
//                    extensionsTag.replaceChild(newElement, oldChild)
//                }
//            }
//
//            document.normalizeDocument()
//            val tFactory = TransformerFactory.newInstance()
//            val transformer: Transformer = tFactory.newTransformer()
//
//            val source = DOMSource(document)
//            val result = StreamResult(virtualFile.getOutputStream(null))
//            transformer.transform(source, result)
//             ============================

//
//            val tags = mutableListOf<XmlTag>()
//            tags.add(factory.createTagFromText("<fileTypeFactory implementation=\"${packageDir}.${extensionCapitalized}FileTypeFactory\"/>"))
//            tags.add(factory.createTagFromText("<lang.parserDefinition language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}ParserDefinition\"/>"))
//            tags.add(factory.createTagFromText("<lang.syntaxHighlighterFactory language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}SyntaxHighlighterFactory\"/>"))
//            tags.add(factory.createTagFromText("<completion.contributor language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}CompletionContributor\"/>"))
//            tags.add(factory.createTagFromText("<psi.referenceContributor language=\"$extensionCapitalized\" implementation=\"${packageDir}.${extensionCapitalized}ReferenceContributor\"/>"))
//            tags.add(factory.createTagFromText("<lang.refactoringSupport language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}RefactoringSupportProvider\"/>"))
//            tags.add(factory.createTagFromText("<lang.findUsagesProvider language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}FindUsagesProvider\"/>"))
//            tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}Inspection\" displayName=\"$extensionCapitalized inspection\" enabledByDefault=\"true\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}Inspection\"/>"))
//            tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}ReferencesInspection\" displayName=\"$extensionCapitalized reference inspection\" enabledByDefault=\"true\" level=\"ERROR\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}ReferencesInspection\"/>"))
//            val crossReferences = context?.rules?.filterIsInstance<TreeParserRule>()?.flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }?.distinctBy { it.getBnfName() }
//            crossReferences?.forEach {
//                val referenceName = NameGenerator.toGKitClassName(it.getBnfName())
//                tags.add(factory.createTagFromText("<lang.elementManipulator forClass=\"${packageDir}.impl.${extensionCapitalized}${referenceName}Impl\" implementationClass=\"${packageDir}.psi.${extensionCapitalized}${referenceName}Manipulator\"/>"))
//            }
//
//            tags.forEach { extensionsTag.add(it) }
            val reformer = CodeStyleManager.getInstance(project)
            reformer.reformat(extensionsTag)
        }
    }


    protected fun generateParser(cleanGenDir: Boolean) {

        val extension = languageExtension.toLowerCase()
        val bnfGrammarFilePath =
            "$basePath/src/main/java/${extension}Language/${extension}/grammar/${languageExtension.capitalize()}.bnf"
        val bnfGrammarFile = LocalFileSystem.getInstance().findFileByPath(bnfGrammarFilePath)
        bnfGrammarFile?.let {
            if (cleanGenDir) {
                val genDir = File("$basePath/src/main/gen")
                if (genDir.exists()) {
                    FileUtils.cleanDirectory(genDir)
                }
            }
            PsiDocumentManager.getInstance(project).commitAllDocuments()
            FileDocumentManager.getInstance().saveAllDocuments()
            val list = mutableListOf<VirtualFile>(it)
            GenerateAction.doGenerate(project, list)
        }
    }


    protected fun generateLexer() {
        val flexFiles = mutableListOf<File>()
        val jflexJarFile = File("$basePath${separator}jflex-1.7.0-2.jar")
        val skeletonFile = File("$basePath${separator}idea-flex.skeleton")
        if (jflexJarFile.exists() && skeletonFile.exists()) {
            flexFiles.add(jflexJarFile)
            flexFiles.add(skeletonFile)
        } else {
            val urls = arrayOf(
//                System.getProperty(
//                    "grammar.kit.jflex.jar",
//
//                    "https://jetbrains.bintray.com/intellij-third-party-dependencies/org/jetbrains/intellij/deps/jflex/jflex/1.7.0-2/jflex-1.7.0-2.jar"
//                ),
                System.getProperty(
                    "grammar.kit.jflex.jar",

                    "https://cache-redirector.jetbrains.com/intellij-dependencies/org/jetbrains/intellij/deps/jflex/jflex/1.7.0-2/jflex-1.7.0-2.jar"
                ),

                System.getProperty(
                    "grammar.kit.jflex.skeleton",
                    "https://raw.github.com/JetBrains/intellij-community/master/tools/lexer/idea-flex.skeleton"
                )
            )
            val libraryName = "JFlex & idea-flex.skeleton"
            val service = DownloadableFileService.getInstance()
            val descriptions = urls.map { service.createFileDescription(it, it.substring(it.lastIndexOf("/") + 1)) }

            val pairs: List<Pair<VirtualFile, DownloadableFileDescription>>? =
                service.createDownloader(descriptions, libraryName)
                    .downloadWithProgress(basePath, project, null)
            assertNotNull(pairs)

            ApplicationManager.getApplication().runWriteAction {

                ApplicationManager.getApplication().assertWriteAccessAllowed()
                var library = ApplicationLibraryTable.getApplicationTable().getLibraryByName(libraryName)
                if (library == null) {
                    val modifiableModel = ApplicationLibraryTable.getApplicationTable().modifiableModel
                    library = modifiableModel.createLibrary(libraryName)
                    modifiableModel.commit()
                }

                val modifiableModel = library.modifiableModel
                for (pair in pairs) {
                    modifiableModel.addRoot((pair.first as VirtualFile)!!, OrderRootType.CLASSES)
                }
                modifiableModel.commit()

            }
            for (url in urls) {
                for (pair in pairs) {
                    if (Comparing.equal(url, (pair.second as DownloadableFileDescription).downloadUrl)) {
                        flexFiles.add(VfsUtil.virtualToIoFile((pair.first as VirtualFile)!!))
                        break
                    }
                }
            }
        }

        val batchId = "jflex@" + System.nanoTime()
        val extension = languageExtension.toLowerCase()
        val flexFilePath =
            "${basePath}/src/main/java/${extension}Language/${extension}/grammar/${extension.capitalize()}.flex"
        val flexFile = LocalFileSystem.getInstance().findFileByPath(flexFilePath)
        assertNotNull(flexFile)
        object : Runnable {
            override fun run() {
                BnfRunJFlexAction.doGenerate(project, flexFile, flexFiles, batchId)
            }
        }.run()
    }


    private fun addOrReplaceChildTag(parent: XmlTag, newTag: XmlTag) {
        parent.getSubTag(newTag.name)?.let {
            it.replace(newTag)
        } ?: run {
            parent.addSubTag(newTag, false)
        }
    }

    private fun addOrReplaceChildTagForSpecificLanguage(parent: XmlTag, newTag: XmlTag, langName: String) {
        parent.getSubTag(newTag.name)?.let {
            if (it.getAttribute("language")?.value == langName) {
                it.replace(newTag)
            } else {
                parent.addSubTag(newTag, false)
            }
        } ?: run {
            parent.addSubTag(newTag, false)
        }
    }

    private fun addOrReplaceChildTagForSpecificLanguageAndShortName(
        parent: XmlTag,
        newTag: XmlTag,
        langName: String,
        shortName: String
    ) {
        parent.getSubTag(newTag.name)?.let {
            if (it.getAttribute("language")?.value == langName && it.getAttribute("shortName")?.value == shortName) {
                it.replace(newTag)
            } else {
                parent.addSubTag(newTag, false)
            }
        } ?: run {
            parent.addSubTag(newTag, false)
        }
    }


    fun XmlTag.getSubTag(name: String): XmlTag? {
        for (tag in this.subTags) {
            if (tag.name == name) return tag
        }
        return null
    }

//    fun Document.createAttributeWithValue(attrName: String, attrValue: String): Attr {
//        val attr = this.createAttribute(attrName)
//        attr.value = attrValue
//        return attr
//    }
//
//    fun Node.findChildrenByName(name: String): List<Node>{
//        val result = mutableListOf<Node>()
//        val childNodes = this.childNodes
//        for(i in 0 until childNodes.length){
//            val child = childNodes.item(i)
//            if(child.nodeName == name) result.add(child)
//        }
//        return result
//    }
}