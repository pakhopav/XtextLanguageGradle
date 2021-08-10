package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextParserRule
import com.intellij.xtext.metamodel.model.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.model.elements.tree.TreeDuplicateRule

class TreeDuplicateRuleImpl : TreeParserRuleImpl, TreeDuplicateRule {
    override val originRuleName: String

    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor, originRuleName: String) : super(psiRule, returnType) {
        this.originRuleName = originRuleName
    }

    constructor(name: String, returnType: EmfClassDescriptor, originRuleName: String) : super(name, returnType) {
        this.originRuleName = originRuleName

    }
}
