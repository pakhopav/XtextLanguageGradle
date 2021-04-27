package com.intellij.module

import com.intellij.execution.util.ListTableWithButtons
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.ui.TextComponentAccessor
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.AnActionButtonRunnable
import com.intellij.util.ui.ListTableModel
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.beans.PropertyChangeListener
import javax.swing.*
import javax.swing.border.Border
import javax.swing.border.EmptyBorder
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer

class XtextUsedGrammarsTable(val label: String = "") : ListTableWithButtons<XtextGrammarFileInfo>() {


    override fun createListModel(): ListTableModel<*> {
        return ListTableModel<XtextGrammarFileInfo>(NameColumnInfo(this), PathColumnInfo())
    }


    override fun createElement(): XtextGrammarFileInfo {
        return XtextGrammarFileInfo("", null)
    }

    override fun isEmpty(element: XtextGrammarFileInfo?): Boolean {
        element?.let {
            return it.grammarName.isEmpty() && it.file == null
        } ?: return false
    }

    override fun cloneElement(variable: XtextGrammarFileInfo?): XtextGrammarFileInfo {
        return variable?.clone() as XtextGrammarFileInfo //Might throw NPE
    }

    override fun canDeleteElement(selection: XtextGrammarFileInfo?): Boolean {
        return true
    }

    fun addElement(element: XtextGrammarFileInfo) {
        addNewElement(element)
    }

    fun setElements(elements: List<XtextGrammarFileInfo>) {
        setValues(elements)
    }

    fun getMyItems(): List<XtextGrammarFileInfo> {
        return elements
    }

    fun getItemByName(name: String): XtextGrammarFileInfo? {
        return elements.filter { it.grammarName == name }.firstOrNull()
    }


    override fun getComponent(): JComponent {
        val superComponent = super.getComponent()
        return XtextUsedGrammarsTablePanel(superComponent, label)
    }


    override fun createAddAction(): AnActionButtonRunnable? {
        return null
    }

    override fun createRemoveAction(): AnActionButtonRunnable? {
        return null
    }

    val myFileChosen: ((chosenFile: VirtualFile) -> String) = { file: VirtualFile ->
        var xtextFile: XtextFile? = null
        var correctFile = true
        try {
            xtextFile = PsiManager.getInstance(ProjectManager.getInstance().defaultProject).findFile(file) as XtextFile
        } catch (e: ClassCastException) {
            correctFile = false
        }
        if (correctFile && xtextFile != null) {
            val grammarName =
                PsiTreeUtil.findChildOfType(xtextFile, XtextGrammarID::class.java)?.validIDList?.last()?.text!!
            addElement(XtextGrammarFileInfo(grammarName, xtextFile))
        }
        FileUtil.getLocationRelativeToUserHome(FileUtil.toSystemDependentName(file.path.trim()), false)
    }

    protected class PathColumnInfo : ElementsColumnInfoBase<XtextGrammarFileInfo>("File Path") {
        override fun valueOf(item: XtextGrammarFileInfo?): String? {
            return item?.filePath ?: "unresolved"
        }

        override fun isCellEditable(item: XtextGrammarFileInfo?): Boolean {
//            if(item?.file == null) return true
            return false
        }

        override fun getDescription(element: XtextGrammarFileInfo?): String? {
            if (element?.file == null) return "Please choose file manually"
            return null
        }

        val unresolvedGrammarDefaultRenderer = object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                val component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                val panel = JPanel(BorderLayout())
                panel.add(JButton(object : Action {
                    override fun actionPerformed(e: ActionEvent?) {
                        print("sad")
                    }

                    override fun getValue(key: String?): Any {
                        return 2
                    }

                    override fun putValue(key: String?, value: Any?) {
                    }

                    override fun setEnabled(b: Boolean) {
                    }

                    override fun isEnabled(): Boolean {
                        return true
                    }

                    override fun addPropertyChangeListener(listener: PropertyChangeListener?) {
                    }

                    override fun removePropertyChangeListener(listener: PropertyChangeListener?) {
                    }

                }), "Center")
                panel.addMouseListener(object : MouseListener {
                    override fun mouseClicked(e: MouseEvent?) {
                        print("sdas")
                    }

                    override fun mousePressed(e: MouseEvent?) {
                        print("sdas")
                    }

                    override fun mouseReleased(e: MouseEvent?) {
                        TODO("Not yet implemented")
                    }

                    override fun mouseEntered(e: MouseEvent?) {
                        print("sdas")
                    }

                    override fun mouseExited(e: MouseEvent?) {
                        TODO("Not yet implemented")
                    }

                })
                return panel
            }
        }
        val unresolvedGrammarRenderer = object : TableCellRenderer {
            override fun getTableCellRendererComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                val tfwbb = TextFieldWithBrowseButton()
                tfwbb.textField.foreground = Color.RED
                tfwbb.text = value as String
                val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
                tfwbb.addActionListener(
                    object : ComponentWithBrowseButton.BrowseFolderActionListener<JTextField>(
                        null,
                        null,
                        tfwbb,
                        null,
                        fileChooserDescriptor,
                        TextComponentAccessor.TEXT_FIELD_WHOLE_TEXT
                    ) {}
                )
                return tfwbb
            }
        }

        override fun getCustomizedRenderer(o: XtextGrammarFileInfo?, renderer: TableCellRenderer?): TableCellRenderer {
            return if (o?.file == null) unresolvedGrammarRenderer else renderer ?: DefaultTableCellRenderer()

        }


        protected class UnresolvedPathCellRenderer : TextFieldWithBrowseButton(), TableCellRenderer {
            private val unselectedForeground: Color? = null
            private val unselectedBackground: Color? = null

            override fun getTableCellRendererComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
//                if (table == null) {
//                    return this
//                }
//
//                var fg: Color? = null
//                var bg: Color? = null
//                var tIsSelected = false
//                val dropLocation = table.dropLocation
//                if (dropLocation != null && !dropLocation.isInsertRow
//                    && !dropLocation.isInsertColumn
//                    && dropLocation.row == row && dropLocation.column == column
//                ) {
//                    fg = DefaultLookup.getColor(this, ui, "Table.dropCellForeground")
//                    bg = DefaultLookup.getColor(this, ui, "Table.dropCellBackground")
//                    tIsSelected = true
//                }
//
//                if (isSelected || tIsSelected) {
//                    super.setForeground(fg ?: table.selectionForeground)
//                    super.setBackground(bg ?: table.selectionBackground)
//                } else {
//                    var background = if (unselectedBackground != null) unselectedBackground else table.background
//                    if (background == null || background is UIResource) {
//                        val alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor")
//                        if (alternateColor != null && row % 2 != 0) {
//                            background = alternateColor
//                        }
//                    }
//                    super.setForeground(if (unselectedForeground != null) unselectedForeground else table.foreground)
//                    super.setBackground(background)
//                }
//
//                font = table.font
//
//                if (hasFocus) {
//                    var border: Border? = null
//                    if (isSelected) {
//                        border = DefaultLookup.getBorder(this, ui, "Table.focusSelectedCellHighlightBorder")
//                    }
//                    if (border == null) {
//                        border = DefaultLookup.getBorder(this, ui, "Table.focusCellHighlightBorder")
//                    }
//                    setBorder(border)
//                    if (!isSelected && table.isCellEditable(row, column)) {
//                        var col: Color?
//                        col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground")
//                        if (col != null) {
//                            super.setForeground(col)
//                        }
//                        col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground")
//                        if (col != null) {
//                            super.setBackground(col)
//                        }
//                    }
//                } else {
//                    border = getNoFocusBorder()
//                }

                this.textField.text = value as String

                return this
            }

            private fun getNoFocusBorder(): Border? {
                return EmptyBorder(1, 1, 1, 1)
            }

        }

    }

    protected class NameColumnInfo(val myTable: XtextUsedGrammarsTable) :
        ElementsColumnInfoBase<XtextGrammarFileInfo>("Grammar Name") {

        val unresolvedGrammarRenderer = object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable?,
                value: Any?,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                val component =
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JLabel
                component.foreground = Color.RED
                val item = myTable.getItemByName(value as String)
                val rowWithBB = ComponentWithBrowseButton(component, null)
                val fileChooserDescriptor = FileChooserDescriptorFactory.createSingleLocalFileDescriptor()
                rowWithBB.addActionListener(
                    object : ComponentWithBrowseButton.BrowseFolderActionListener<JLabel>(
                        "",
                        null,
                        rowWithBB,
                        ProjectManager.getInstance().defaultProject,
                        fileChooserDescriptor,
                        nameColumnInfoAccessor
                    ) {
                        public override fun onFileChosen(chosenFile: VirtualFile) {
                            var xtextFile: XtextFile? = null
                            var correctFile = true
                            var grammarName = ""

                            try {
                                xtextFile = PsiManager.getInstance(ProjectManager.getInstance().defaultProject)
                                    .findFile(chosenFile) as XtextFile
                                grammarName = PsiTreeUtil.findChildOfType(
                                    xtextFile,
                                    XtextGrammarID::class.java
                                )?.validIDList?.last()?.text!!
                                if (!grammarName.equals(value)) correctFile = false
                            } catch (e: ClassCastException) {
                                correctFile = false
                            }
                            if (correctFile && xtextFile != null) {
                                item?.file = xtextFile
                            }
//                            FileUtil.getLocationRelativeToUserHome(FileUtil.toSystemDependentName(chosenFile.path.trim()), false)
                        }
                    }
                )
                return rowWithBB
                return component
            }

        }


        val nameColumnInfoAccessor = object : TextComponentAccessor<JLabel> {
            override fun getText(component: JLabel?): String {
                return component?.text ?: ""
            }

            override fun setText(component: JLabel?, text: String) {
                component?.text = text
            }

        }


//        override fun getCustomizedRenderer(o: XtextGrammarFileInfo?, renderer: TableCellRenderer?): TableCellRenderer {
//            return if (o?.file == null) unresolvedGrammarRenderer else renderer ?: DefaultTableCellRenderer()
//
//        }

        override fun valueOf(item: XtextGrammarFileInfo?): String? {
            return item?.grammarName
        }

        override fun getDescription(element: XtextGrammarFileInfo?): String? {
            return element?.file?.originalFile?.virtualFile?.path
        }

    }

    protected class XtextUsedGrammarsTablePanel(component: JComponent, label: String) : JPanel(BorderLayout()) {
        init {
//            add(JLabel(label), BorderLayout.NORTH)
            add(component, BorderLayout.CENTER)
        }

    }

}