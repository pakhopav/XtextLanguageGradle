package com.intellij.xtext.module

import com.intellij.openapi.externalSystem.model.DataNode
import com.intellij.openapi.externalSystem.model.ProjectKeys
import com.intellij.openapi.externalSystem.model.project.ModuleData
import com.intellij.openapi.externalSystem.model.project.ProjectData
import com.intellij.openapi.externalSystem.service.project.IdeModelsProvider
import com.intellij.openapi.externalSystem.service.project.manage.AbstractProjectDataService
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.groovy.util.removeUserData

class OnSuccessImportGrammarKitLauncher : AbstractProjectDataService<ModuleData, Void>() {
    override fun getTargetDataKey() = ProjectKeys.MODULE

    override fun onSuccessImport(
        imported: MutableCollection<DataNode<ModuleData>>,
        projectData: ProjectData?,
        project: Project,
        modelsProvider: IdeModelsProvider
    ) {
        super.onSuccessImport(imported, projectData, project, modelsProvider)

        val xtextModule = imported.mapNotNull {
            modelsProvider.findIdeModule(it.data)
        }.firstOrNull {
            it.xtextModuleBuilder != null
        }

        val builder = xtextModule?.xtextModuleBuilder ?: return

        DumbService.getInstance(project).runWhenSmart {
            val gkitGenerator = GrammarKitGenerator(builder, project)
            gkitGenerator.launchGrammarKitGeneration()
            gkitGenerator.updatePluginXml()
            xtextModule.removeUserData(GrammarKitGenerator.XTEXT_MODULE_BUILDER_KEY)
        }
    }

    private val Module.xtextModuleBuilder
        get() = this.getUserData(GrammarKitGenerator.XTEXT_MODULE_BUILDER_KEY)
}




