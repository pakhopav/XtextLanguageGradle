package com.intellij.module

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.AppPropertiesComponentImpl
import com.intellij.ide.util.PropertiesComponent
import com.intellij.ide.util.projectWizard.ModuleBuilderListener
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.progress.EmptyProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.impl.libraries.ApplicationLibraryTable
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.Comparing
import com.intellij.openapi.util.Disposer
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
import com.intellij.util.indexing.UnindexedFilesUpdater
import com.intellij.xtextLanguage.xtext.EcorePackageRegistry
import com.intellij.xtextLanguage.xtext.XtextIcons
import com.intellij.xtextLanguage.xtext.generator.generators.MainGenerator
import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.MetaContextImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import org.intellij.grammar.actions.BnfRunJFlexAction
import org.intellij.grammar.actions.GenerateAction
import org.jetbrains.plugins.gradle.service.project.wizard.AbstractGradleModuleBuilder
import org.jetbrains.plugins.gradle.service.project.wizard.GradleStructureWizardStep
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
    var context: MetaContext? = null

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

    override fun setupModule(module: Module?) {
        PropertiesComponent.getInstance().setValue("LATEST_GRADLE_VERSION_KEY", "0.7.3");
        super.setupModule(module)
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
        configureGradleBuildScript(rootModel)
        grammarFile?.let {
            registerEpackages()
            val usedGrammarFiles = usedGrammars.map { it.file!! }.toMutableList()
            usedGrammarFiles.add(it)
            context = MetaContextImpl(usedGrammarFiles)
            assertNotNull(context)
            val generator = MainGenerator(langExtension, context as MetaContext, sourcePath + "/")
            generator.generate()
        }
        addPluginJars()
    }

    fun configureGradleBuildScript(rootModel: ModifiableRootModel) {
        getBuildScriptData(rootModel.module)?.let {
            val kotlinPluginVersion = PropertiesComponent.getInstance().getValue("installed.kotlin.plugin.version")
            val kotlinJvmPluginVersion = kotlinPluginVersion?.split("-")?.get(1) ?: "1.4.10"

            val pluginJarPaths =
                "'libs/Xtext.jar', 'libs/org.eclipse.emf.common_2.16.0.v20190528-0845.jar', 'libs/org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar', 'libs/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar', 'libs/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar', 'libs/org.xtext.xtext.model.jar'"

            val importedModelsPaths = importedModels.filter { it.file != null }.map { it.path }
                .joinToString(separator = "\", \"", prefix = "\"", postfix = "\"")

            it.addPluginDefinitionInPluginsGroup("id 'org.jetbrains.kotlin.jvm' version '$kotlinJvmPluginVersion'")
                .addDependencyNotation("compile files($pluginJarPaths)")
                .addDependencyNotation("compile files($importedModelsPaths)")
                .addOther(
                    """
                    |compileKotlin {
                    |    kotlinOptions {
                    |        jvmTarget = "1.8"
                    |    }
                    |}""".trimMargin("|")
                )

                .addOther(
                    """
                    |sourceSets {
                    |    main {
                    |        java.srcDirs project.files("src/main/java", "gen")
                    |    }
                    |    test {
                    |        java.srcDirs "src/test/java"
                    |    }
                    |}""".trimMargin("|")
                )
                .addOther("sourceCompatibility = 1.8")
        }
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


    class XtextModuleBuilderListener(val builder: XtextModuleBuilder) : ModuleBuilderListener {

        override fun moduleCreated(module: Module) {
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
            tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"$extensionCapitalized\" displayName=\"$extensionCapitalized inspection\" enabledByDefault=\"true\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}Inspection\"/>"))
            tags.add(factory.createTagFromText("<localInspection groupName=\"$extensionCapitalized\" language=\"$extensionCapitalized\" shortName=\"$extensionCapitalized\" displayName=\"$extensionCapitalized reference inspection\" enabledByDefault=\"true\" level=\"ERROR\" implementationClass=\"${packageDir}.inspection.${extensionCapitalized}ReferencesInspection\"/>"))
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
            UnindexedFilesUpdater(module.project).performInDumbMode(EmptyProgressIndicator())
            val s = AppPropertiesComponentImpl.getInstance()
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
            val props = AppPropertiesComponentImpl()
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