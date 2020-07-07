package com.intellij.xtext

import com.intellij.entityLanguage.entity.EntityParserDefinition
import com.intellij.testFramework.ParsingTestCase
import org.xtext.example.entity.entity.EntityPackage
import java.io.IOException


open class EntityParsingTestCase() : ParsingTestCase("", "entity", EntityParserDefinition()) {

    override fun skipSpaces(): Boolean {
        return false;
    }

    override fun includeRanges(): Boolean {
        return true;
    }

    override fun getTestDataPath(): String {
        return "/Users/pavel/work/xtextGradle/XtextLanguageGradle/src/test/resources/testData/parsing"
    }

    fun testSimple() {
        val name = testName
        try {
            val text = loadFile("$name.$myFileExt")
            myFile = createPsiFile(name, text)
            ensureParsed(myFile)
            val root = myFile.node
            val ep = EntityPackage.eINSTANCE.entity

        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }


}