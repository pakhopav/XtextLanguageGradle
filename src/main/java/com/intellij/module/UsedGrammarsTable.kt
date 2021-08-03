package com.intellij.module

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.AbstractTableCellEditor
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextGrammar
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID
import com.intellij.xtextLanguage.xtext.psi.XtextReferencedMetamodel
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.util.jar.JarFile
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.border.EmptyBorder
import javax.swing.table.DefaultTableCellRenderer


//ClasspathPanelImpl

class UsedGrammarsTable(val jarsTable: ImportedJarsTable) : JPanel(BorderLayout()) {
    val tableName = "Used Grammars:"
    private val myEntryTable: JBTable
    private val myTableModel: UsedGrammarsTableModel
    private val helper = XtextModuleBuilderHelper()
    private val errorMessageComponent = JLabel()

    init {
        val emptyBorder = EmptyBorder(0, 0, 0, 0)
        myTableModel = UsedGrammarsTableModel()
        myEntryTable = JBTable(myTableModel)
        myEntryTable.setShowGrid(false)
        myEntryTable.border = emptyBorder
//        myEntryTable.columnModel.getColumn(1).cellEditor = PathColumnCellEditor()
        myEntryTable.columnModel.getColumn(1).setCellEditor(object : AbstractTableCellEditor() {

            override fun getCellEditorValue(): Any {
                return 0
            }

            override fun getTableCellEditorComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                row: Int,
                column: Int
            ): Component {
                val defaultProject = ProjectManager.getInstance().defaultProject
                val tfwbb = TextFieldWithBrowseButton()
                tfwbb.text = if (value != null && value.equals("unresolved")) "" else value as String
                val currentGrammarInfo = myTableModel.items[row]
                tfwbb.addActionListener(
                    object : ComponentWithBrowseButton.BrowseFolderActionListener<TextFieldWithBrowseButton>(
                        "",
                        "",
                        tfwbb as ComponentWithBrowseButton<TextFieldWithBrowseButton>,
                        defaultProject,
                        FileChooserDescriptorFactory.createSingleLocalFileDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT as TextComponentAccessor<in TextFieldWithBrowseButton>
                    ) {
                        override fun onFileChosen(chosenFile: VirtualFile) {
                            currentGrammarInfo.filePath = chosenFile.path
                            tfwbb.text = chosenFile.path
                            try {
                                val xtextFile = PsiManager.getInstance(defaultProject).findFile(chosenFile) as XtextFile
                                val xtextFileName =
                                    PsiTreeUtil.findChildOfType(xtextFile, XtextGrammarID::class.java)?.text
                                if (xtextFileName != null && xtextFileName.equals(currentGrammarInfo.grammarName)) {
                                    currentGrammarInfo.file = xtextFile
                                    findUsedGrammars(xtextFile)
                                    setErrorMessage(" ")
                                    super.onFileChosen(chosenFile)
                                } else {
                                    if (xtextFileName == null) {
                                        setErrorMessage("grammar name cannot be found")
                                    } else {
                                        setErrorMessage("wrong grammar name")
                                    }
                                }
                            } catch (e: ClassCastException) {
                                setErrorMessage("wrong file format")
                            }


                        }
                    }
                )
                return tfwbb
            }

        })
        myEntryTable.setDefaultRenderer(Any::class.java, object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                val data = myTableModel.getRowValue(row)
                component.background = Color.WHITE
                component.foreground = Color.BLACK
                if (data.file == null) {
                    component.foreground = Color.RED
                }

                return component
            }
        })
        val scrollPane = JBScrollPane(myEntryTable)
        scrollPane.border = emptyBorder
        scrollPane.preferredSize = Dimension(1000, 500)
        add(scrollPane, BorderLayout.CENTER)
        add(JLabel(tableName), BorderLayout.NORTH)
        border = emptyBorder

        errorMessageComponent.foreground = Color.RED
        errorMessageComponent.text = " "
        add(errorMessageComponent, BorderLayout.SOUTH)

    }

    fun setErrorMessage(string: String) {
        errorMessageComponent.text = string
    }

    fun addElement(element: XtextGrammarFileInfo) {
        myTableModel.addRow(element)
    }

    fun setElements(elements: List<XtextGrammarFileInfo>) {
        myTableModel.items = elements
    }

    fun getElements(): List<XtextGrammarFileInfo> {
        return myTableModel.items
    }


    private fun findUsedGrammars(grammarFile: XtextFile) {
        findImportedModels(grammarFile)
        val grammar = PsiTreeUtil.findChildOfType(grammarFile, XtextGrammar::class.java) ?: return
        val usedGrammars = grammar.referenceGrammarGrammarIDList
        usedGrammars.forEach {
            val grammarName = it.text
            if (helper.containsInKnownGrammars(grammarName)) {
                addElement(XtextGrammarFileInfo(grammarName, helper.getKnownGrammar(grammarName)))
                findUsedGrammars(helper.getKnownGrammar(grammarName)!!)
            } else addElement(XtextGrammarFileInfo(grammarName, null))
        }
    }

    private fun findImportedModels(grammarFile: XtextFile) {
        val importedModels = PsiTreeUtil.findChildrenOfType(grammarFile, XtextReferencedMetamodel::class.java)
        importedModels.forEach {
            val modelName = it.referenceePackageSTRING.text.replace("\"", "")
            if (helper.containsInKnownModels(modelName)) {
                val file = helper.getKnownModel(modelName)!!
                val jarFile = JarFile(file)
                jarsTable.addElement(EcoreModelJarInfo(modelName, path = file.path, file = jarFile))
            } else {
                jarsTable.addElement(EcoreModelJarInfo(modelName))
            }
        }
    }
}