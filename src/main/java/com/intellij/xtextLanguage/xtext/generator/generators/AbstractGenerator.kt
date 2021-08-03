package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.File
import java.io.IOException

abstract class AbstractGenerator(val extension: String, rootPath: String = "src/main/java/com/intellij/") {
    internal val extensionCapitalized = extension.capitalize()
    internal val myGenDir = "$rootPath${extension.toLowerCase()}Language/${extension.toLowerCase()}"
    internal val packageDir = "${getGroupIdPart(rootPath)}${extension.toLowerCase()}Language.${extension.toLowerCase()}"


    fun createOrFindFile(fileName: String, filePath: String): File {
        val path = File(filePath)
        val file = File(filePath + "/" + fileName)
        if (!file.exists()) {
            try {
                path.mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return file
    }

    protected fun getGroupIdPart(rootPath: String): String {
        val separator = File.separator
        val groupPart = rootPath.substringAfter("java$separator").replace(separator, ".")
        return if (groupPart.length > 1) groupPart else ""
    }
}