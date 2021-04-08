package com.intellij.module

import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.application.WriteAction
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.PropertyBinding
import com.intellij.ui.layout.panel
import com.intellij.ui.layout.selected
import com.intellij.ui.layout.withTextBinding
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextGrammar
import java.awt.Color
import javax.swing.JLabel

class XtextSecondWizardStep(val context: WizardContext, val builder: XtextModuleBuilder) : ModuleWizardStep() {
    val usedGrammars = mutableListOf<XtextGrammarFileInfo>()
    var languageName = "LanguageName"
    var languageExtension = "lang"
    var xtextFilePath = "..."

    private val defaultProject = ProjectManager.getInstance().defaultProject
    private val helper = XtextModuleBuilderHelper(defaultProject)

    val useExistedGrammarCheckBox = JBCheckBox("Use existed .xtext file")
    val table = XtextUsedGrammarsTable("Used Grammars:")
    val table2 = XtextUsedGrammarsTable("Imported JARs:")
    val errorComment = JLabel("error").also {
        it.isVisible = false
        it.foreground = Color.RED
    }
    val languageNameTextField = JBTextField(languageName, 0)


    init {
        WriteAction.compute<Unit, RuntimeException> {
            PsiDocumentManager.getInstance(defaultProject).commitAllDocuments()
            FileDocumentManager.getInstance().saveAllDocuments()
        }
    }

    private val contentPanel by lazy {
        panel {
            row("") {
                useExistedGrammarCheckBox()
            }
            row("") {
                val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
                textFieldWithBrowseButton(
                    { xtextFilePath },
                    { v -> xtextFilePath = v },
                    "",
                    context.project,
                    fileChooserDescriptor,
                    myFileChosen
                )
                    .enableIf(useExistedGrammarCheckBox.selected)
                    .commentComponent(errorComment, true)
            }
            row("Language Name:") {
                languageNameTextField()
                    .withTextBinding(PropertyBinding({ languageName }, { v -> languageName = v }))
                    .focused()
            }
            row("Language Extension:") {
                textField(PropertyBinding({ languageExtension }, { v -> languageExtension = v }))
            }
            row {
                table.component()

//                table2.component()
            }

        }
    }

    override fun getComponent() = contentPanel

    override fun updateDataModel() {
        TODO("Not yet implemented")
    }

    override fun validate(): Boolean {
        if (xtextFilePath.isEmpty() || xtextFilePath.equals("...")) {
            if (languageName.isEmpty()) return false
            if (languageExtension.isEmpty()) return false
            return true
        } else {
            if (!allTalesFound()) return false
            return true
        }
    }

    val myFileChosen: ((chosenFile: VirtualFile) -> String) = { file: VirtualFile ->
        usedGrammars.clear()
        var xtextFile: XtextFile? = null
        var correctFile = true
        try {
            xtextFile = PsiManager.getInstance(defaultProject).findFile(file) as XtextFile
        } catch (e: ClassCastException) {
            errorComment.isVisible = true
            languageNameTextField.isEnabled = true
            correctFile = false
        }
        if (correctFile && xtextFile != null) {
            errorComment.isVisible = false
            setLanguageTextField(helper.getGrammarName(xtextFile))
            languageNameTextField.isEnabled = false
            findUsedGrammars(xtextFile)
            table.setElements(usedGrammars)
//            table.addElement(XtextGrammarFileInfo("LLL", null))
        }
        getUiPath(file.path)
    }

    private fun setLanguageTextField(langName: String) {
        languageName = langName
        languageNameTextField.text = langName
    }

    private fun allTalesFound(): Boolean {
        return !usedGrammars.filter { it.file == null }.any()
    }

    private fun findUsedGrammars(grammarFile: XtextFile) {
        val grammar = PsiTreeUtil.findChildOfType(grammarFile, XtextGrammar::class.java) ?: return
        val usedGrammars = grammar.referenceGrammarGrammarIDList
        usedGrammars.forEach {
            val grammarName = it.text
            if (helper.containsInKnownGrammars(grammarName)) {
                this.usedGrammars.add(XtextGrammarFileInfo(grammarName, helper.getKnownGrammar(grammarName)))
                findUsedGrammars(helper.getKnownGrammar(grammarName)!!)
            } else this.usedGrammars.add(XtextGrammarFileInfo(grammarName, null))
        }
    }

    private fun getUiPath(path: String): String =
        FileUtil.getLocationRelativeToUserHome(FileUtil.toSystemDependentName(path.trim()), false)


}