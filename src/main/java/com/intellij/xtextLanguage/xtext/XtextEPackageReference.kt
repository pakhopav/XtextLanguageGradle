package com.intellij.xtextLanguage.xtext

import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.psi.*
import com.intellij.xtextLanguage.xtext.references.XtextLanguageDependenciesManager
import java.io.File
import java.io.IOException

class XtextEPackageReference(element: PsiElement, textRange: TextRange) :
    PsiReferenceBase<PsiElement?>(element, textRange), PsiPolyVariantReference {
    private val key: String

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
        val jarPath = map.get(key.replace("\"", "").replace("'", ""))
        jarPath?.let {
            LocalFileSystem.getInstance().findFileByIoFile(File("${project.basePath}/$it"))?.let {
                PsiManager.getInstance(project).findFile(it)?.let {
                    results.add(PsiElementResolveResult(it))
                }
            }

        }

        return results.toTypedArray()
    }

    fun _getVariants(): Array<Any> {
        val variants = mutableListOf<LookupElement>()
        val map = XtextLanguageDependenciesManager.getInstance(myElement!!.project).getPackages()
        variants.addAll(map.keys.map { LookupElementBuilder.create("\"$it\"") })
        return variants.toTypedArray()
    }


}