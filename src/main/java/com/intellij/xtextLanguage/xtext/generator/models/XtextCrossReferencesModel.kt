package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserCrossReferenseElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule

class XtextCrossReferencesModel(val parserRules: List<ParserRule>) {
    val references: Set<ParserCrossReferenseElement>

    init {
        references = findAllCrossReferences()
        references.forEach { reference ->
            reference.targets = parserRules.filter {
                val returnType = if (it.returnTypeText.isNotEmpty()) it.returnTypeText else it.name
                returnType == reference.referenceTarget
            }
        }
    }

    fun findAllCrossReferences(): Set<ParserCrossReferenseElement> {
        val list = mutableSetOf<ParserCrossReferenseElement>()
        parserRules
                .flatMap {
                    it.alternativeElements
                            .filter { it is ParserCrossReferenseElement }
                            .map { it as ParserCrossReferenseElement }
                }.forEach {
                        list.add(it)
                }
        return list

    }

}