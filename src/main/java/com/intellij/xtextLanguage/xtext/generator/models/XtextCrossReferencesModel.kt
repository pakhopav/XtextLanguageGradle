package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule

class XtextCrossReferencesModel(val parserRules: List<ParserRule>) {
//    val references: Set<ParserCrossReferenceElement>
//
//    init {
//        references = findAllCrossReferences()
//        references.forEach { reference ->
//            reference.targetRuleNames = parserRules.filter {
//                val returnType = if (it.returnTypeText.isNotEmpty()) it.returnTypeText else it.name
//                returnType == reference.referenceTarget
//            }
//        }
//    }
//
//    fun findAllCrossReferences(): Set<ParserCrossReferenceElement> {
//        val list = mutableSetOf<ParserCrossReferenceElement>()
//        parserRules
//                .flatMap {
//                    it.alternativeElements
//                            .filter { it is ParserCrossReferenceElement }
//                            .map { it as ParserCrossReferenceElement }
//                }.forEach {
//                        list.add(it)
//                }
//        return list
//
//    }

}