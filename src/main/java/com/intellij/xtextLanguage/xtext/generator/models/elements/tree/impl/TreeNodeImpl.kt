package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality

abstract class TreeNodeImpl(protected val psiElement: PsiElement, val parent: TreeNode?) : TreeNode {
    protected val _children = mutableListOf<TreeNode>()
    override val children: List<TreeNode>
        get() = _children

    protected var specificString: String? = null

    fun addChild(child: TreeNodeImpl) {
        _children.add(child)
    }

    fun replaceChild(toReplace: TreeNode, newNode: TreeNode) {
        val index = _children.indexOf(toReplace)
        if (index != -1) {
            _children.remove(toReplace)
            _children.add(index, newNode)
        }
    }

    override fun setSpecificBnfString(string: String) {
        specificString = string
    }

    protected fun getCardinalityOfPsiElement(): Cardinality {
        val psiParentWithCardinality = PsiTreeUtil.getParentOfType(psiElement, XtextAbstractTokenWithCardinality::class.java)
        psiParentWithCardinality?.let {
            psiParentWithCardinality.asteriskKeyword?.let { return Cardinality.ASTERISK }
            psiParentWithCardinality.quesMarkKeyword?.let { return Cardinality.QUES }
            psiParentWithCardinality.plusKeyword?.let { return Cardinality.PLUS }
        }
        return Cardinality.NONE
    }

    override fun filterNodesInSubtree(condition: (node: TreeNode) -> Boolean): List<TreeNode> {
        val filteredNodes = mutableListOf<TreeNode>()
        this.children.forEach {
            filteredNodes.addAll(it.filterNodesInSubtree(condition))
        }
        if (condition(this)) filteredNodes.add(this)
        return filteredNodes
    }

    fun copy(): TreeNodeImpl {
        val copy = TreeNodeCopy(psiElement, parent, { getBnfString() })
        specificString?.let { copy.setSpecificBnfString(it) }
        _children.forEach { copy.addChild((it as TreeNodeImpl).copy()) }
        return copy
    }

    class TreeNodeCopy(psiElement: PsiElement,
                       parent: TreeNode?,
                       val getBnfStringFun: () -> String) : TreeNodeImpl(psiElement, parent) {
        override val cardinality: Cardinality
            get() = getCardinalityOfPsiElement()

        override fun getBnfString(): String {
            return getBnfStringFun()
        }

    }
}