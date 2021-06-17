package com.intellij.module

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.module.Module
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
import com.intellij.util.download.DownloadableFileDescription
import com.intellij.util.download.DownloadableFileService
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import org.intellij.grammar.actions.BnfRunJFlexAction
import org.intellij.grammar.actions.GenerateAction
import java.io.File
import kotlin.test.assertNotNull

class GrammarKitGenerator(val builder: XtextModuleBuilder) {
    companion object {
        val XTEXT_MODULE_BUILDER_KEY = Key<XtextModuleBuilder>(
            GrammarKitGenerator::class.qualifiedName + ":XtextModuleBuilder"
        )
    }

    fun launchGrammarKitGeneration(module: Module) {
        val project = module.project
        WriteCommandAction.writeCommandAction(project).compute<Unit, Throwable> {
            updatePluginXml(project)
            generateParser(module)
            generateLexer(module)
        }
    }

    protected fun updatePluginXml(project: Project) {
        val pluginXmlFilePath = "${builder.contentEntryPath}/src/main/resources/META-INF/plugin.xml"
        val virtualFile =
            LocalFileSystem.getInstance().refreshAndFindFileByPath(pluginXmlFilePath)
        assertNotNull(virtualFile)
        val file = PsiManager.getInstance(project).findFile(virtualFile)
        val factory = XmlElementFactory.getInstance(project)
        val context = builder.context
        val extension = builder.langExtension.toLowerCase()
        val extensionCapitalized = extension.capitalize()
        val packageDir = "${extension}Language.$extension"
        val tags = mutableListOf<XmlTag>()
        tags.add(factory.createTagFromText("<fileTypeFactory implementation=\"${packageDir}.${extensionCapitalized}FileTypeFactory\"/>"))
        tags.add(factory.createTagFromText("<lang.parserDefinition language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}ParserDefinition\"/>"))
        tags.add(factory.createTagFromText("<lang.syntaxHighlighterFactory language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}SyntaxHighlighterFactory\"/>"))
        tags.add(factory.createTagFromText("<completion.contributor language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}CompletionContributor\"/>"))
        tags.add(factory.createTagFromText("<psi.referenceContributor language=\"$extensionCapitalized\" implementation=\"${packageDir}.${extensionCapitalized}ReferenceContributor\"/>"))
        tags.add(factory.createTagFromText("<lang.refactoringSupport language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}RefactoringSupportProvider\"/>"))
        tags.add(factory.createTagFromText("<lang.findUsagesProvider language=\"$extensionCapitalized\" implementationClass=\"${packageDir}.${extensionCapitalized}FindUsagesProvider\"/>"))
        tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}Inspection\" displayName=\"$extensionCapitalized inspection\" enabledByDefault=\"true\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}Inspection\"/>"))
        tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"${extensionCapitalized}ReferencesInspection\" displayName=\"$extensionCapitalized reference inspection\" enabledByDefault=\"true\" level=\"ERROR\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}ReferencesInspection\"/>"))
        val crossReferences = context?.rules?.filterIsInstance<TreeParserRule>()
            ?.flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }?.distinctBy { it.getBnfName() }
        crossReferences?.forEach {
            val referenceName = NameGenerator.toGKitClassName(it.getBnfName())
            tags.add(factory.createTagFromText("<lang.elementManipulator forClass=\"${packageDir}.impl.${extensionCapitalized}${referenceName}Impl\" implementationClass=\"${packageDir}.psi.${extensionCapitalized}${referenceName}Manipulator\"/>"))
        }
        val extensionsTag =
            PsiTreeUtil.findChildrenOfType(file, XmlTag::class.java).filter { it.name == "extensions" }
                .firstOrNull()
        assertNotNull(extensionsTag)
        tags.forEach { extensionsTag.add(it) }
        val reformer = CodeStyleManager.getInstance(project)
        reformer.reformat(extensionsTag)
    }


    protected fun generateParser(module: Module) {
        val project = module.project
        val extension = builder.langExtension.toLowerCase()
        val bnfGrammarFilePath =
            "${builder.contentEntryPath}/src/main/java/${extension}Language/${extension}/grammar/${extension.capitalize()}.bnf"
        val bnfGrammarFile = LocalFileSystem.getInstance().findFileByPath(bnfGrammarFilePath)
        bnfGrammarFile?.let {
            PsiDocumentManager.getInstance(project).commitAllDocuments()
            FileDocumentManager.getInstance().saveAllDocuments()
            val list = mutableListOf<VirtualFile>(it)
            GenerateAction.doGenerate(project, list)
        }
    }

    protected fun generateLexer(module: Module) {
        val project = module.project
        val flexFiles = mutableListOf<File>()
        val urls = arrayOf(
            System.getProperty(
                "grammar.kit.jflex.jar",
                "https://jetbrains.bintray.com/intellij-third-party-dependencies/org/jetbrains/intellij/deps/jflex/jflex/1.7.0-2/jflex-1.7.0-2.jar"
            ),
            System.getProperty(
                "grammar.kit.jflex.skeleton",
                "https://raw.github.com/JetBrains/intellij-community/master/tools/lexer/idea-flex.skeleton"
            )
        )
        val libraryName = "JFlex & idea-flex.skeleton"
        val service = DownloadableFileService.getInstance()
        val descriptions: MutableList<DownloadableFileDescription?> = mutableListOf()

        for (i in urls.indices) {
            val url = urls[i]
            descriptions.add(service.createFileDescription(url, url.substring(url.lastIndexOf("/") + 1)))
        }

        val pairs: List<Pair<VirtualFile, DownloadableFileDescription>>? =
            service.createDownloader(descriptions, libraryName)
                .downloadWithProgress(builder.contentEntryPath, project, null)
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
        val batchId = "jflex@" + System.nanoTime()
        val extension = builder.langExtension.toLowerCase()
        val flexFilePath =
            "${builder.contentEntryPath}/src/main/java/${extension}Language/${extension}/grammar/${extension.capitalize()}.flex"
        val flexFile = LocalFileSystem.getInstance().findFileByPath(flexFilePath)
        assertNotNull(flexFile)
        object : Runnable {
            override fun run() {
                BnfRunJFlexAction.doGenerate(project, flexFile, flexFiles, batchId)
            }
        }.run()
    }
}