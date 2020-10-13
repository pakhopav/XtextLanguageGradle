package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.File
import java.io.IOException

abstract class AbstractGenerator(val extension: String) {
    internal val extensionCapitalized = extension.capitalize()
    internal val myGenDir = "src/main/java/com/intellij/${extension.toLowerCase()}Language/${extension.toLowerCase()}"
    internal val packageDir = "com.intellij.${extension.toLowerCase()}Language.${extension.toLowerCase()}"


    fun createFile(fileName: String, filePath: String): File {
        val path = File(filePath)
        val file = File(filePath + "/" + fileName)
        try {
            path.mkdirs()
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
}