package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserCrossReferenseElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule

class XtextCrossReferencesModel(val parserRules: List<ParserRule>) {
    val references: List<ParserCrossReferenseElement>

    init {
        references = findAllCrossReferences()
        references.forEach {
            val reference = it
            it.targets = parserRules.filter { it.returnType == reference.referenceTarget.text }


        }
    }

    fun findAllCrossReferences(): List<ParserCrossReferenseElement> {
        val list = mutableListOf<ParserCrossReferenseElement>()
        parserRules
                .flatMap {
                    it.alternativeElements
                            .filter { it is ParserCrossReferenseElement }
                            .map { it as ParserCrossReferenseElement }
                }.forEach {
                    if (!list.map { it.name }.contains(it.name)) {
                        list.add(it)
                    }
                }
        return list

    }

}