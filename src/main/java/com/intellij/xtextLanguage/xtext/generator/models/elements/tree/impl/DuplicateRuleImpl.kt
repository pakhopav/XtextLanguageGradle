package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.DuplicateRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class DuplicateRuleImpl(psiRule: XtextParserRule, override val originRuleName: String) : TreeRootImpl(psiRule), DuplicateRule
