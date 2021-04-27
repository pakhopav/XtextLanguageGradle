package com.intellij.module

import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.ListTableModel

class ImportedJarsTableModel : ListTableModel<EcoreModelJarInfo>(NameColumnInfo(), PathColumnInfo()) {


    class NameColumnInfo : ColumnInfo<EcoreModelJarInfo, String>("Name") {
        override fun valueOf(item: EcoreModelJarInfo?): String? {
            return item?.uri?.replace("http://", "")
        }
    }

    class PathColumnInfo : ColumnInfo<EcoreModelJarInfo, String>("Path") {

        override fun valueOf(item: EcoreModelJarInfo?): String? {
            return item?.path ?: "unresolved"
        }

        override fun isCellEditable(item: EcoreModelJarInfo?): Boolean {
            return item?.file == null
        }
    }
}