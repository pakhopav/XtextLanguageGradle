package com.intellij.xtext.language.reference

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.file.impl.JavaFileManager
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.xtext.persistence.XtextLanguageDependenciesManager
import com.intellij.xtext.util.JarUtil
import java.io.File
import java.io.IOException
import java.util.jar.JarFile
import kotlin.test.assertNotNull

class XtextEClassifierReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {
    private val key: String
    private val PRIMITIVE_TYPES = listOf("int", "byte", "short", "long", "double", "float", "boolean", "char")

    init {
        key = element.text.substring(textRange.startOffset, textRange.endOffset)
    }

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        var results: Array<ResolveResult>? = null
        try {
            results = _multiResolve()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return results!!
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return _getVariants()
    }

    @Throws(IOException::class)
    private fun _multiResolve(): Array<ResolveResult>? {
        val results = mutableListOf<ResolveResult>()
        val project = myElement!!.project
        val map = XtextLanguageDependenciesManager.getInstance(project).getPackages()
        val finder = JavaFileManager.getInstance(project)
        map.entries.forEach {
            val jarFile = JarFile(File("${project.basePath}/${it.value}"))
            assertNotNull(jarFile)
            val allNames = JarUtil.getEObjectAndEDatatypeNames(jarFile, it.key)
            if (allNames.keys.contains(key)) {
                allNames.get(key)?.let {
                    if (PRIMITIVE_TYPES.contains(it)) {
                        results.add(PsiElementResolveResult(myElement))
                    } else {
                        finder.findClass(it, GlobalSearchScope.allScope(project))?.let {
                            results.add(PsiElementResolveResult(it))
                        }
                    }
                }
                val basePath = JarUtil.getBasePackageOfJar(jarFile, it.key)
                finder.findClass("$basePath.$key", GlobalSearchScope.allScope(project))?.let {
                    results.add(PsiElementResolveResult(it))
                }
            }
        }


        return results.toTypedArray()
    }

    fun _getVariants(): Array<Any> {
        val variants = mutableListOf<LookupElement>()
        val project = myElement!!.project
        val map = XtextLanguageDependenciesManager.getInstance(project).getPackages()
        map.entries.forEach {
            val jarFile = JarFile(File("${project.basePath}/${it.value}"))
            assertNotNull(jarFile)
            val allNames = JarUtil.getAllClassifiersNames(jarFile, it.key)
            variants.addAll(allNames.map { LookupElementBuilder.create(it) })
        }
        return variants.toTypedArray()
    }


}