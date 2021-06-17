package com.intellij.module

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.PropertiesComponent
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.Disposer
import com.intellij.xtextLanguage.xtext.EcorePackageRegistry
import com.intellij.xtextLanguage.xtext.XtextIcons
import com.intellij.xtextLanguage.xtext.generator.generators.MainGenerator
import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.MetaContextImpl
import com.intellij.xtextLanguage.xtext.psi.XtextFile
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

        val iconsDir = File("$contentEntryPath/src/main/resources/icons")
        iconsDir.mkdirs()
        jarUri = javaClass.classLoader.getResource("icons/simpleIcon.png")
        targetPath = Paths.get("$contentEntryPath/src/main/resources/icons/${langExtension}Icon.png")
        Files.copy(jarUri.openStream(), targetPath)
    }

    protected fun registerEpackages() {
        val ePackages = importedModels.map { helper.getEcoreModelEPackage(it.file!!)!! }
        ePackages.forEach {
            EcorePackageRegistry.instance.registerPackage(it)
        }
    }


}