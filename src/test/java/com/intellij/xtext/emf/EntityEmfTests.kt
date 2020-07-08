package com.intellij.xtext.emf

import com.intellij.entityLanguage.entity.emf.EntityEmfVisitor
import com.intellij.entityLanguage.entity.psi.EntityDomainmodel
import com.intellij.psi.util.PsiTreeUtil
import junit.framework.TestCase

class EntityEmfTests : EntityEmfTestsBase("/emf") {

    fun testParsedFile() {
        val fileName = getCurrentInputFileName()
        fileName?.let {
            val file = myFixture.configureByFile(it)
            val dm = PsiTreeUtil.findChildOfAnyType(file, EntityDomainmodel::class.java)
            TestCase.assertNotNull(dm)
        }

    }

    fun testSimpleEntityEmfInstance() {
        val fileName = getCurrentInputFileName()
        fileName?.let {
            val file = myFixture.configureByFile(it)
            val dm = PsiTreeUtil.findChildOfAnyType(file, EntityDomainmodel::class.java)
            dm?.let {
                val myRes = EntityEmfVisitor.getEmfModel(it)
                val eclRes = getModelFromXmi("test.entity")
                print("sds")
            }

        }

    }

    fun testSimpleEntityWithExtends() {
        val fileName = getCurrentInputFileName()
        fileName?.let {
            val file = myFixture.configureByFile(it)
            val dm = PsiTreeUtil.findChildOfAnyType(file, EntityDomainmodel::class.java)
            dm?.let {
                val myRes = EntityEmfVisitor.getEmfModel(it)
                val eclRes = getModelFromXmi("simpleWithExtends.entity")
                print("sds")
            }

        }

    }


}