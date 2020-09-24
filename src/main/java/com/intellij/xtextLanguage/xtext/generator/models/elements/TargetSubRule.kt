package com.intellij.xtextLanguage.xtext.generator.models.elements

class TargetSubRule(val name: String, private val nameElements: List<String>) {


    fun getGeneratorString(extension: String): String {
        val sb = StringBuilder()
        sb.append("        if(element instanceof $extension$name){\n")
        if (nameElements.size == 1) {
            sb.append("            return (($extension$name)element).get${nameElements[0]}();\n        }")
        } else {
            nameElements.forEach {
                sb.append("            if((($extension$name)element).get$it() != null) return (($extension$name)element).get$it();\n")
            }
            sb.append("        }")
        }

        return sb.toString()
    }
}