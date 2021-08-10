package com.intellij.xtext.module

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.PropertiesComponent
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.xtext.generator.MainGenerator
import com.intellij.xtext.language.EcorePackageRegistry
import com.intellij.xtext.language.XtextIcon
import com.intellij.xtext.language.psi.XtextFile
import com.intellij.xtext.metamodel.MetaContext
import com.intellij.xtext.metamodel.MetaContextImpl
import com.intellij.xtext.module.wizard.EcoreModelJarInfo
import com.intellij.xtext.module.wizard.XtextGrammarFileInfo
import com.intellij.xtext.module.wizard.XtextSecondWizardStep
import com.intellij.xtext.module.wizard.XtextSelectSdkWizardStep
import com.intellij.xtext.persistence.XtextLanguageDependenciesManager
import com.intellij.xtext.persistence.XtextLanguageInfoManager
import org.jetbrains.plugins.gradle.service.project.wizard.AbstractGradleModuleBuilder
import org.jetbrains.plugins.gradle.service.project.wizard.GradleStructureWizardStep
import java.io.File
import java.io.InputStream
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
    private val separator = File.separator

    init {
        this.addListener { module ->
            module.putUserData(GrammarKitGenerator.XTEXT_MODULE_BUILDER_KEY, this)
        }
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
        return XtextIcon.FILE
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
        val project = rootModel.project
        val sourcePath = "$contentEntryPath${separator}src${separator}main${separator}java"
        addPluginJars()
        configureGradleBuildScript(rootModel)
        grammarFile?.let {
            registerEpackages()
            val usedGrammarFiles = usedGrammars.map { it.file!! }.toMutableList()
            val allGrammarFiles = mutableListOf(*usedGrammarFiles.toTypedArray(), it)
            context = MetaContextImpl(allGrammarFiles)
            assertNotNull(context)
            val generator = MainGenerator(langExtension, context as MetaContext, sourcePath + separator)
            generator.generate()
            copyXtextFilesToProject(project)
            persistInputValues(project)
        }

    }

    fun copyXtextFilesToProject(project: Project) {
        val grammarsDir =
            "$contentEntryPath${separator}src${separator}main${separator}java${separator}${langExtension}Language$separator$langExtension${separator}grammar"
        val grammarFileTargetPath = Paths.get("$grammarsDir$separator${langExtension.capitalize()}.xtext")
        Files.copy(grammarFile!!.virtualFile.inputStream, grammarFileTargetPath)

//        val usedGrammarsFiles = usedGrammars.mapNotNull { it.file  }.map{File(it.virtualFile.path)}
//        JarUtil.createJarWithFiles("$contentEntryPath${separator}libs${separator}usedGrammars.jar", usedGrammarsFiles)

//        val usedGrammarsDir = "$grammarsDir${separator}dependencies"
//        File(usedGrammarsDir).mkdirs()
//        usedGrammars.filter { it.file != null }.forEach {
//            val grammarName = it.grammarName
//            val targetPath = Paths.get("$usedGrammarsDir$separator${grammarName}.xtext")
//            Files.copy(it.file!!.virtualFile.inputStream, targetPath)
//        }
    }

    protected fun persistInputValues(project: Project) {
        val ecoreModels = mutableMapOf<String, String>()
        importedModels.filter { it.file != null }.forEach {
            ecoreModels.put(it.uri, it.targetPath!!)
        }
        val grammars = mutableMapOf<String, String>()
        usedGrammars.filter { it.file != null }.forEach {
            grammars.put(it.grammarName, it.filePath)
        }
        val dependenciesManager = XtextLanguageDependenciesManager.getInstance(project)
        dependenciesManager.refreshEcoreModels(ecoreModels)
        dependenciesManager.refreshGrammars(grammars)
        XtextLanguageInfoManager.getInstance(project).setNameAndExtension(langName, langExtension)
    }

    fun configureGradleBuildScript(rootModel: ModifiableRootModel) {
        getBuildScriptData(rootModel.module)?.let {
            val kotlinPluginVersion = PropertiesComponent.getInstance().getValue("installed.kotlin.plugin.version")
            val kotlinJvmPluginVersion = kotlinPluginVersion?.split("-")?.get(1) ?: "1.4.10"

            val pluginJarPaths =
                "'libs/Xtext.jar', 'libs/DefaultGrammars.jar', 'libs/org.eclipse.emf.common_2.16.0.v20190528-0845.jar', 'libs/org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar', 'libs/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar', 'libs/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar', 'libs/org.xtext.xtext.model.jar'"

            val importedModelsPaths = importedModels.mapNotNull { it.targetPath }
                .joinToString(separator = "', '", prefix = "'", postfix = "'")

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
                    |        java.srcDirs project.files("src/main/java", "src/main/gen")
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
        val libDir = File("$contentEntryPath/libs")
        libDir.mkdirs()

        importedModels.filter { it.file != null }.forEach {
            copyJarToNewProject(it)
        }

        copyJarToNewProject("Xtext.jar")
        copyJarToNewProject("DefaultGrammars.jar")
        copyJarToNewProject("org.eclipse.emf.common_2.16.0.v20190528-0845.jar")
        copyJarToNewProject("org.eclipse.emf.ecore.change_2.14.0.v20190528-0725.jar")
        copyJarToNewProject("org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar")
        if (!File("$contentEntryPath/libs/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar").exists()) {
            copyJarToNewProject("org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar")
        }
        copyJarToNewProject("org.xtext.xtext.model.jar")

        copyIcon()
    }


    protected fun copyJarToNewProject(jarName: String) {
        val jarUrl = javaClass.classLoader.getResource(jarName)
        jarUrl ?: throw Exception("Couldn`t find file resource: $jarName")
        val targetPath = Paths.get("$contentEntryPath/libs/$jarName")
        Files.copy(jarUrl.openStream(), targetPath)
    }

    protected fun copyJarToNewProject(importedJar: EcoreModelJarInfo) {
        var inputStream: InputStream? = null
        val jarPath = importedJar.path!!
        val jarName = jarPath.split(separator).last()
        LocalFileSystem.getInstance().findFileByIoFile(File(jarPath))?.let {
            inputStream = it.inputStream
            importedJar.targetPath = "libs/$jarName"
        }
        inputStream ?: throw Exception("Couldn`t find file resource: $jarPath")
        val targetPath = Paths.get("$contentEntryPath/libs/$jarName")
        Files.copy(inputStream, targetPath)
    }


    protected fun copyIcon() {
        val iconsDir = File("$contentEntryPath/src/main/resources/icons")
        iconsDir.mkdirs()
        var jarUri = javaClass.classLoader.getResource("icons/xtextIcon.png")
        var targetPath = Paths.get("$contentEntryPath/src/main/resources/icons/${langExtension}Icon.png")
        Files.copy(jarUri.openStream(), targetPath)
    }

    protected fun registerEpackages() {
        val ePackages = importedModels.map { helper.getEcoreModelEPackage(it.file!!)!! }
        ePackages.forEach {
            EcorePackageRegistry.instance.registerPackage(it)
        }
    }

//    companion object {
//        @JvmStatic
//        val ECORE_PACKAGE_KEY: Key<CachedValue<Map<String, String>>> = Key.create("Associated ecore models packages")
//    }

}