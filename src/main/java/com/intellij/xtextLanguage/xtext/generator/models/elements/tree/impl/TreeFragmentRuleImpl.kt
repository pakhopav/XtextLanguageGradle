package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeFragmentRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class TreeFragmentRuleImpl : TreeRootImpl, TreeFragmentRule {

    constructor(psiRule: XtextParserRule) : super(psiRule)

    constructor(name: String) : super(name)

    override fun getBnfString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append("private $name ::= ")
        _children.forEach {
            stringBuffer.append(it.getBnfString())
            stringBuffer.append(" ")
        }
        return stringBuffer.toString()
    }

}