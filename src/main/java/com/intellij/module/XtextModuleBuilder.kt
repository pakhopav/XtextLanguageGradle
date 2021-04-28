package com.intellij.module

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.projectWizard.ModuleBuilderListener
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.impl.libraries.ApplicationLibraryTable
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Pair
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.download.DownloadableFileDescription
import com.intellij.util.download.DownloadableFileService
import com.intellij.xtextLanguage.xtext.EcorePackageRegistry
import com.intellij.xtextLanguage.xtext.XtextIcons
import com.intellij.xtextLanguage.xtext.generator.generators.MainGenerator
import com.intellij.xtextLanguage.xtext.generator.models.MetaContextImpl
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import org.intellij.grammar.actions.BnfRunJFlexAction
import org.intellij.grammar.actions.GenerateAction
import org.jetbrains.plugins.gradle.service.project.wizard.AbstractGradleModuleBuilder
import org.jetbrains.plugins.gradle.service.project.wizard.GradleStructureWizardStep
import org.jetbrains.plugins.groovy.lang.psi.GroovyPsiElementFactory
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrClosableBlock
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.Icon
import kotlin.test.assertNotNull

class XtextModuleBuilder : AbstractGradleModuleBuilder() {
    var usedGrammars = mutableListOf<XtextGrammarFileInfo>()
    var importedModels = mutableListOf<EcoreModelJarInfo>()
    var langName = ""
    var langExtension = ""
    var grammarFile: XtextFile? = null

    private val helper = XtextModuleBuilderHelper()

    init {
        this.addListener(XtextModuleBuilderListener(this))
    }

    override fun getModuleType(): ModuleType<*> {
        return XtextModuleType()
    }

    override fun getBuilderId(): String? {
        return "xtext"
    }
//builder.getPresentableName(), builder.getDescription(), builder.getNodeIcon(), builder.getWeight(), builder.getParentGroup()

    override fun getPresentableName(): String {
        return "Xtext"
    }

    override fun getDescription(): String {
        return "Xtext support"
    }

    override fun getNodeIcon(): Icon {
        return XtextIcons.FILE
    }

    override fun getWeight(): Int {
        return 50
    }


    @Throws(ConfigurationException::class)
    override fun setupRootModel(rootModel: ModifiableRootModel) {
        super.setupRootModel(rootModel)
        val separator = File.separator
        val sourcePath = "$contentEntryPath${separator}src${separator}main${separator}java"
////        val contentEntry = doAddContentEntry(rootModel)
////        if (contentEntry != null) {
////            File(sourcePath).mkdirs()
////            val sourceRoot = LocalFileSystem.getInstance()
////                .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(sourcePath))
////            if (sourceRoot != null) contentEntry.addSourceFolder(sourceRoot, false, "")
////        }
//

        grammarFile?.let {
            registerEpackages()
            addLibraries(rootModel)
            val usedGrammarFiles = usedGrammars.map { it.file!! }.toMutableList()
            usedGrammarFiles.add(it)
            val context = MetaContextImpl(usedGrammarFiles)
            val generator = MainGenerator(langExtension, context, sourcePath + "/")
            generator.generate()
        }

        addPluginJars()
    }


    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
//        val parentSteps = super.createWizardSteps(wizardContext, modulesProvider)
        return arrayOf(XtextSecondWizardStep(wizardContext, this), GradleStructureWizardStep(this, wizardContext))
    }

    override fun getIgnoredSteps(): MutableList<Class<out ModuleWizardStep>> {
        return mutableListOf(ProjectSettingsStep::class.java)
    }

    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?): ModuleWizardStep? {
        val step = XtextSelectSdkWizardStep(context, this)
        Disposer.register(parentDisposable!!, step)
        return step
    }

    protected fun addPluginJars() {
        var jarUri = javaClass.classLoader.getResource("Xtext.jar")
        val libDir = File("$contentEntryPath/libs")
        libDir.mkdirs()
        var targetPath = Paths.get("$contentEntryPath/libs/Xtext.jar")
        Files.copy(jarUri.openStream(), targetPath)

        jarUri = javaClass.classLoader.getResource("org.eclipse.emf.common_2.16.0.v20190528-0845.jar")
        targetPath = Paths.get("$contentEntryPath/libs/org.eclipse.emf.common_2.16.0.v20190528-0845.jar")
        Files.copy(jarUri.openStream(), targetPath)

        jarUri = javaClass.classLoader.getResource("org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar")
        targetPath = Paths.get("$contentEntryPath/libs/org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar")
        Files.copy(jarUri.openStream(), targetPath)

        jarUri = javaClass.classLoader.getResource("org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar")
        targetPath = Paths.get("$contentEntryPath/libs/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar")
        Files.copy(jarUri.openStream(), targetPath)

        jarUri = javaClass.classLoader.getResource("org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar")
        targetPath = Paths.get("$contentEntryPath/libs/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar")
        Files.copy(jarUri.openStream(), targetPath)

        jarUri = javaClass.classLoader.getResource("org.xtext.xtext.model.jar")
        targetPath = Paths.get("$contentEntryPath/libs/org.xtext.xtext.model.jar")
        Files.copy(jarUri.openStream(), targetPath)
    }

    protected fun registerEpackages() {
        val ePackages = importedModels.map { helper.getEcoreModelEPackage(it.file!!)!! }
        ePackages.forEach {
            EcorePackageRegistry.instance.registerPackage(it)
        }
    }

    protected fun addLibraries(rootModel: ModifiableRootModel) {
        val libTable = rootModel.moduleLibraryTable
        importedModels.forEach {
            val library = libTable.createLibrary()
            val modifiableModel = library.modifiableModel
            modifiableModel.addRoot(VfsUtil.getUrlForLibraryRoot(File(it.path)), OrderRootType.SOURCES)
            modifiableModel.addRoot(VfsUtil.getUrlForLibraryRoot(File(it.path)), OrderRootType.CLASSES)
            modifiableModel.commit()
        }
    }

    protected fun getUsedGrs(rootModel: ModifiableRootModel): List<XtextFile> {
//        val currProject = ProjectManager.getInstance().openProjects[0]
        val currProject = rootModel.project
        val usedGrs = ArrayList<XtextFile>()
        val terminalsPath =
            "/Users/pavel/work/xtextGradle/XtextLanguageGradle/src/test/resources/testData/generation/generateBnf/Terminals.xtext"
        val terminalsVirtualFile =
            LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(terminalsPath))
        var terminals: XtextFile? = null
        terminalsVirtualFile?.let {
            terminals = PsiManager.getInstance(currProject).findFile(it) as XtextFile
        }
        val entityPath =
            "/Users/pavel/work/xtextGradle/XtextLanguageGradle/src/test/resources/testData/generation/generateBnf/entityLan.xtext"
        val entityVirtualFile = LocalFileSystem.getInstance()
            .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(entityPath))
        var entity: XtextFile? = null
        entityVirtualFile?.let {
            entity = PsiManager.getInstance(currProject).findFile(it) as XtextFile
        }

        entity?.let { usedGrs.add(it) }
        terminals?.let { usedGrs.add(it) }

        return usedGrs
    }

    class XtextModuleBuilderListener(val builder: XtextModuleBuilder) : ModuleBuilderListener {

        override fun moduleCreated(module: Module) {
            val project = module.project
            val buildFilePath = "${builder.contentEntryPath}${File.separator}build.gradle"
            val virtualBuildFile =
                LocalFileSystem.getInstance().refreshAndFindFileByPath(FileUtil.toSystemIndependentName(buildFilePath))
            val buildFile =
                PsiManager.getInstance(ProjectManager.getInstance().defaultProject).findFile(virtualBuildFile!!)
            val dependenciesBlock = PsiTreeUtil.getChildrenOfType(buildFile, GrMethodCallExpression::class.java)
                .filter { it.node.firstChildNode.text == "dependencies" }.firstOrNull()
            assertNotNull(dependenciesBlock)
            val factory = GroovyPsiElementFactory.getInstance(project)
            WriteCommandAction.writeCommandAction(project, buildFile).compute<Unit, Throwable> {
                val dependenciesClosableBlock =
                    PsiTreeUtil.findChildOfType(dependenciesBlock, GrClosableBlock::class.java)!!
                val paths = builder.importedModels.filter { it.file != null }.map { it.path }
                    .joinToString(separator = "\", \"", prefix = "\"", postfix = "\"")
                var statement = factory.createStatementFromText("compile files($paths)\n", null)
                val closingBracket = dependenciesClosableBlock.node.lastChildNode.psi
                dependenciesClosableBlock.addBefore(statement, closingBracket)
                val pluginJarPaths = "\"libs/Xtext.jar\"," +
                        " \"libs/org.eclipse.emf.common_2.16.0.v20190528-0845.jar\"," +
                        " \"libs/org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar\"," +
                        " \"libs/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar\"," +
                        " \"libs/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar\"," +
                        " \"libs/org.xtext.xtext.model.jar\""
                statement = factory.createStatementFromText("   compile files($pluginJarPaths)\n", null)
                dependenciesClosableBlock.addBefore(statement, closingBracket)
                val t1text = "sourceSets {\n" +
                        "    main {\n" +
                        "        java.srcDirs project.files(\"src/main/java\", \"gen\")\n" +
                        "    }\n" +
                        "    test {\n" +
                        "        java.srcDirs \"src/test/java\"\n" +
                        "    }\n" +
                        "}"
                val srcSetBlock = factory.createExpressionFromText(t1text)
                val parent = dependenciesBlock.parent
                parent?.addBefore(srcSetBlock, dependenciesBlock)

                val reformer = CodeStyleManager.getInstance(project)
                reformer.reformat(parent)
                val documentManager = PsiDocumentManager.getInstance(project)
                val doc = documentManager.getDocument(buildFile!!)
                doc?.let { documentManager.commitDocument(it) }

                updatePluginXml()
                generateParser(module)
                generateLexer(module)
            }

        }


        protected fun updatePluginXml() {
            val contentEntry = builder.contentEntryPath
            val pluginXmlFile =
                LocalFileSystem.getInstance().refreshAndFindFileByPath("$contentEntry/resources/META-INF/plugin.xml")
            print("")
        }

        protected fun generateParser(module: Module) {
            val project = module.project
            val extension = builder.langExtension.toLowerCase()
            val bnfGrammarFilePath =
                "${builder.contentEntryPath}/src/main/java/${extension}Language/${extension}/grammar/${extension.capitalize()}.bnf"
            val bnfGrammarFile = LocalFileSystem.getInstance().findFileByPath(bnfGrammarFilePath)
            bnfGrammarFile?.let {
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
                val var4 = pairs!!.iterator()

                while (var4.hasNext()) {
                    val pair: Pair<VirtualFile, DownloadableFileDescription> = var4.next()
                    modifiableModel.addRoot((pair.first as VirtualFile)!!, OrderRootType.CLASSES)
                }

                modifiableModel.commit()

            }
            val var6 = urls.size

            for (var7 in 0 until var6) {
                val url = urls[var7]
                val var9 = pairs.iterator()
                while (var9.hasNext()) {
                    val pair: Pair<VirtualFile, DownloadableFileDescription> = var9.next()
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
}