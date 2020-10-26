package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeDuplicateRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class TreeDuplicateRuleImpl : TreeParserRuleImpl, TreeDuplicateRule {
    override val originRuleName: String

    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor, originRuleName: String) : super(psiRule, returnType) {
        this.originRuleName = originRuleName
    }

    constructor(name: String, returnType: EmfClassDescriptor, originRuleName: String) : super(name, returnType) {
        this.originRuleName = originRuleName

    }
}
