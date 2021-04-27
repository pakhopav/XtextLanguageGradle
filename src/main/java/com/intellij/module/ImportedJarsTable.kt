package com.intellij.module

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.AbstractTableCellEditor
import com.intellij.xtextLanguage.xtext.psi.XtextFile
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

class ImportedJarsTable() : JPanel(BorderLayout()) {
    private val tableName = "Imported JARs:"
    private val myEntryTable: JBTable
    private val myTableModel: ImportedJarsTableModel
    private val helper = XtextModuleBuilderHelper()

    init {
        val emptyBorder = EmptyBorder(0, 0, 0, 0)


//        val jarPath = "/Users/pavel/work/xtextGradle/XtextLanguageGradle/libs/SimpleEmf.jar"
//        val jarFile = JarFile(jarPath)
//        val name = helper.getEcoreModelEPackage(jarFile)


        myTableModel = ImportedJarsTableModel()
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
                tfwbb.foreground = Color.RED
                val currentModelInfo = myTableModel.items[row]
                tfwbb.addActionListener(
                    object : ComponentWithBrowseButton.BrowseFolderActionListener<TextFieldWithBrowseButton>(
                        "", "", tfwbb as ComponentWithBrowseButton<TextFieldWithBrowseButton>, defaultProject,
                        FileChooserDescriptorFactory.createSingleLocalFileDescriptor(),
                        TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT as TextComponentAccessor<in TextFieldWithBrowseButton>
                    ) {
                        override fun onFileChosen(chosenFile: VirtualFile) {
                            val jarPath = chosenFile.path
                            currentModelInfo.path = jarPath
                            tfwbb.text = jarPath
                            try {
                                val jarFile = JarFile(jarPath)
                                val jarName = helper.getEcoreModelUri(jarFile)
                                if (currentModelInfo.uri == jarName) {
                                    currentModelInfo.file = jarFile
                                    tfwbb.foreground = Color.BLACK
                                }
                            } catch (e: Exception) {

                            }

                        }
                    }
                )
                tfwbb.isEditable = false
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
    }

    fun addElement(element: EcoreModelJarInfo) {
        myTableModel.addRow(element)
    }

    fun setElements(elements: List<EcoreModelJarInfo>) {
        myTableModel.items = elements
    }

    fun getElements(): List<EcoreModelJarInfo> {
        return myTableModel.items
    }

    private fun findImportedModels(grammarFile: XtextFile) {
        val importedModels = PsiTreeUtil.findChildrenOfType(grammarFile, XtextReferencedMetamodel::class.java)
        print("")
        importedModels.forEach {
            val modelName = it.referenceEcoreEPackageSTRING.text
            addElement(EcoreModelJarInfo(modelName))
        }
    }
}