package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.DuplicateRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class DuplicateRuleImpl : TreeParserRuleImpl, DuplicateRule {
    override val originRuleName: String

    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor, originRuleName: String) : super(psiRule, returnType) {
        this.originRuleName = originRuleName
    }

    constructor(name: String, returnType: EmfClassDescriptor, originRuleName: String) : super(name, returnType) {
        this.originRuleName = originRuleName

    }
}
