package com.intellij.module

import com.intellij.framework.addSupport.FrameworkSupportInModuleProvider
import com.intellij.ide.util.newProjectWizard.AddSupportForFrameworksPanel
import com.intellij.ide.util.newProjectWizard.impl.FrameworkSupportModelBase
import com.intellij.ide.util.projectWizard.ModuleBuilder.ModuleConfigurationUpdater
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.StatisticsAwareModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.internal.statistic.eventLog.FeatureUsageData
import com.intellij.openapi.Disposable
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.JdkComboBox
import com.intellij.openapi.roots.ui.configuration.ProjectStructureConfigurable
import com.intellij.openapi.roots.ui.configuration.projectRoot.LibrariesContainerFactory
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.layout.CCFlags
import com.intellij.ui.layout.panel
import org.jetbrains.plugins.gradle.frameworkSupport.GradleFrameworkSupportProvider
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class XtextSelectSdkWizardStep(val context: WizardContext?, val builder: XtextModuleBuilder) : ModuleWizardStep(),
    Disposable,
    StatisticsAwareModuleWizardStep {
    private var myFrameworksPanel: AddSupportForFrameworksPanel
    private val myFrameworksPanelPlaceholder = JPanel(BorderLayout())
    private var jdkComboBox: JdkComboBox
    private val mySdkChooserHolder = JPanel(BorderLayout())
    private val contentPanel by lazy {
        panel {
            row("Project SDK:") {
                mySdkChooserHolder(CCFlags.growX)
            }
            row("") {
                myFrameworksPanelPlaceholder()
                    .visible(false)
            }
        }
    }

    init {
        val project = context!!.project ?: ProjectManager.getInstance().defaultProject
        val container = LibrariesContainerFactory.createContainer(context!!.project)
        val model: FrameworkSupportModelBase = object : FrameworkSupportModelBase(project, builder, container) {
            override fun getBaseDirectoryForLibrariesPath(): String {
                return StringUtil.notNullize(builder.contentEntryPath)
            }
        }
        myFrameworksPanel = AddSupportForFrameworksPanel(emptyList(), model, true, null)

        val providers = ArrayList<FrameworkSupportInModuleProvider>()
        providers.addAll(GradleFrameworkSupportProvider.EP_NAME.extensions)
        myFrameworksPanel!!.setProviders(providers, emptySet(), setOf("java", "gradle-intellij-plugin"))
        Disposer.register(this, myFrameworksPanel)
        myFrameworksPanelPlaceholder.add(myFrameworksPanel.getMainPanel())
        val configurationUpdater: ModuleConfigurationUpdater = object : ModuleConfigurationUpdater() {
            override fun update(module: Module, rootModel: ModifiableRootModel) {
                myFrameworksPanel.addSupport(module, rootModel)
            }
        }
        builder.addModuleConfigurationUpdater(configurationUpdater)
        val projectConfig = ProjectStructureConfigurable.getInstance(project)
        val mySdksModel = projectConfig.getProjectJdksModel()
        jdkComboBox = JdkComboBox(project, mySdksModel, null, null, null, null)
        mySdkChooserHolder.add(jdkComboBox)
//        mySdksModel,
//        sdk -> sdk instanceof SdkType && (type == null || type.equals(sdk)),
//        null,
//        null,
//        null)
//        final ProjectStructureConfigurable projectConfig = ProjectStructureConfigurable.getInstance(myProject);
//        mySdksModel = projectConfig.getProjectJdksModel();
//        myJdkChooser = new JdkComboBox(myProject,
//        mySdksModel,
//        sdk -> sdk instanceof SdkType && (type == null || type.equals(sdk)),
//        null,
//        null,
//        null);
    }

    override fun getComponent(): JComponent {
        return contentPanel
    }

    override fun validate(): Boolean {
        return jdkComboBox.selectedJdk != null
    }

    override fun updateDataModel() {
        context!!.projectJdk = jdkComboBox.selectedJdk
    }

    override fun dispose() {

    }

    override fun addCustomFeatureUsageData(eventId: String, data: FeatureUsageData) {
        myFrameworksPanel.reportSelectedFrameworks(eventId, data)
    }
}