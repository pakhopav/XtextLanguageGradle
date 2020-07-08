package com.intellij.entityLanguage.entity.psi

import com.intellij.psi.PsiElement

class EntityNameVisitor {
    fun visitDomainmodel(node: EntityDomainmodel): PsiElement? {
        return null
    }

    fun visitType(node: EntityType): PsiElement? {
        node.dataType?.let { visitDataType(it)?.let { return@visitType it } }
        node.entity?.let { visitEntity(it)?.let { return@visitType it } }
        return null
    }

    fun visitDataType(node: EntityDataType): PsiElement? {
        return node.id
    }

    fun visitEntity(node: EntityEntity): PsiElement? {
        return node.id
    }

    fun visitFeature(node: EntityFeature): PsiElement? {
        node.id?.let { return@visitFeature it }
        return null
    }
}