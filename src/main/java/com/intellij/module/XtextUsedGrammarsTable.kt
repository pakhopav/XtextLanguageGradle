package com.intellij.module

import com.intellij.execution.util.ListTableWithButtons
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.AnActionButtonRunnable
import com.intellij.util.ui.ListTableModel
import java.awt.Color
import java.awt.Component
import java.awt.GridLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer

class XtextUsedGrammarsTable(val label: String = "") : ListTableWithButtons<XtextGrammarFileInfo>() {


    override fun createListModel(): ListTableModel<*> {
        return ListTableModel<XtextGrammarFileInfo>(NameColumnInfo())
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

    override fun getComponent(): JComponent {
        return XtextUsedGrammarsTablePanel(super.getComponent(), label)
    }


    override fun createAddAction(): AnActionButtonRunnable? {
        return null
    }

    override fun createRemoveAction(): AnActionButtonRunnable? {
        return null
    }

    protected class NameColumnInfo() : ElementsColumnInfoBase<XtextGrammarFileInfo>("") {

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
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JComponent
                component.foreground = Color.RED
                val rowWithBB = ComponentWithBrowseButton(component, { e -> print(e) })
                rowWithBB.setButtonIcon(IconLoader.getIcon("/icons/simpleIcon.png", this::class.java))
                return rowWithBB
            }
        }

        override fun getCustomizedRenderer(o: XtextGrammarFileInfo?, renderer: TableCellRenderer?): TableCellRenderer {
            return if (o?.file == null) unresolvedGrammarRenderer else renderer ?: DefaultTableCellRenderer()

        }

        override fun valueOf(item: XtextGrammarFileInfo?): String? {
            return item?.grammarName
        }

        override fun getDescription(element: XtextGrammarFileInfo?): String? {
            return element?.file?.originalFile?.virtualFile?.path
        }

    }

    protected class XtextUsedGrammarsTablePanel(component: JComponent, label: String) : JPanel(GridLayout(2, 1)) {
        init {
            add(JLabel(label))
            add(component);
        }

    }

}