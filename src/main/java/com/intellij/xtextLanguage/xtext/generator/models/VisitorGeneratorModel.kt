package com.intellij.xtextLanguage.xtext.generator.models

interface VisitorGeneratorModel {

    var rules: MutableList<ModelRule>


    class ModelRule() {
        var name: String = ""
        var uniqueName: String? = null
        var ruleCalls = listOf<String>()
        var ruleCallsList = listOf<String>()

    }

}