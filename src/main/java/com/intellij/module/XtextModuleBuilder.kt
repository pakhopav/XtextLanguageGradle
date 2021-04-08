package com.intellij.module

import com.intellij.ide.util.projectWizard.JavaModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleBuilderListener
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleType
import com.intellij.openapi.options.ConfigurationException
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.PsiManager
import com.intellij.xtextLanguage.xtext.generator.generators.MainGenerator
import com.intellij.xtextLanguage.xtext.generator.models.MetaContextImpl
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import java.io.File

class XtextModuleBuilder : JavaModuleBuilder(), ModuleBuilderListener {

    @JvmField
    var langName = ""
    var langExtension = ""
    var grammarFilePath = ""
    val usedGrammars: List<String> = ArrayList()


    override fun moduleCreated(module: Module) {
    }

    override fun getModuleType(): ModuleType<*> {
        return XtextModuleType()
    }

    override fun getBuilderId(): String? {
        return "xtext"
    }


    @Throws(ConfigurationException::class)
    override fun setupRootModel(rootModel: ModifiableRootModel) {


        val sourcePath = contentEntryPath + File.separator + "src"


        val contentEntry = doAddContentEntry(rootModel)

        if (contentEntry != null) {
            File(sourcePath).mkdirs()
            val sourceRoot = LocalFileSystem.getInstance()
                .refreshAndFindFileByPath(FileUtil.toSystemIndependentName(sourcePath))
            if (sourceRoot != null) contentEntry.addSourceFolder(sourceRoot, false, "")
        }

        val context = MetaContextImpl(getUsedGrs(rootModel))
        val generator = MainGenerator("entity", context, sourcePath + "/")
        generator.generate()
    }


    override fun createWizardSteps(
        wizardContext: WizardContext,
        modulesProvider: ModulesProvider
    ): Array<ModuleWizardStep> {
        return arrayOf(XtextSecondWizardStep(wizardContext, this))
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
}