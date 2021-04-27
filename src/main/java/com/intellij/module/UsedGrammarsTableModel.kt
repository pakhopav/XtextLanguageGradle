package com.intellij.module

import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel
import java.awt.Color
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer

class UsedGrammarsTableModel : ListTableModel<XtextGrammarFileInfo>(NameColumnInfo(), PathColumnInfo()) {


    class NameColumnInfo : ColumnInfo<XtextGrammarFileInfo, String>("Name") {
        override fun valueOf(item: XtextGrammarFileInfo?): String? {
            return item?.grammarName
        }
    }

    class PathColumnInfo : ColumnInfo<XtextGrammarFileInfo, String>("Path") {
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
                return component
            }

        }

        override fun valueOf(item: XtextGrammarFileInfo?): String? {
            return item?.filePath
        }

        override fun isCellEditable(item: XtextGrammarFileInfo?): Boolean {
            return item?.file == null
        }

        override fun getCustomizedRenderer(o: XtextGrammarFileInfo?, renderer: TableCellRenderer?): TableCellRenderer {
            return if (o?.file == null) unresolvedGrammarRenderer else renderer ?: DefaultTableCellRenderer()
        }

        override fun getRenderer(item: XtextGrammarFileInfo?): TableCellRenderer? {
            return if (item?.file == null) unresolvedGrammarRenderer else DefaultTableCellRenderer()
        }
    }
}