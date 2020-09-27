package com.intellij.xtextLanguage.xtext.generator.models.elements

open class ParserRule : ModelRule() {
    override lateinit var name: String
    override lateinit var returnTypeText: String
    var bnfExtensionsStrings = mutableListOf<String>()
    override val alternativeElements = mutableListOf<RuleElement>()
    lateinit var root: TreeRoot

    override var isDataTypeRule = false
    var isReferenced = false
    var isReferencedDelegateRule = false
    var isPrivate = false


    fun getRuleElementsList(): List<RuleElement> {
        val resultList = mutableListOf<RuleElement>()
        visitNode(root, resultList)
        return resultList
    }

    private fun visitNode(node: TreeNode, resultList: MutableList<RuleElement>) {
        if (node is TreeLeaf) {
            resultList.add(node.ruleElement)
        } else {
            node.children.forEach {
                visitNode(it, resultList)
            }
        }
    }

    fun copy(): ParserRule {
        val copy = ParserRule()
        copy.name = name
        copy.returnTypeText = returnTypeText
        copy.alternativeElements.addAll(alternativeElements)
        copy.isDataTypeRule = isDataTypeRule
        copy.bnfExtensionsStrings = bnfExtensionsStrings
        copy.isPrivate = isPrivate
        copy.isReferenced = isReferenced
        copy.isReferencedDelegateRule = isReferencedDelegateRule
        return copy
    }


}