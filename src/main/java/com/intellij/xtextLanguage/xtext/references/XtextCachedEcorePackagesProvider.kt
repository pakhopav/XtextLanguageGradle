package com.intellij.xtextLanguage.xtext.references

import com.intellij.psi.PsiFile
import com.intellij.psi.util.CachedValueProvider

//class XtextCachedEcorePackagesProvider(val xtextFile: PsiFile) : CachedValueProvider<Map<String, String>> {
class XtextCachedEcorePackagesProvider(val xtextFile: PsiFile, val map: Map<String, String>?) :
    CachedValueProvider<Map<String, String>> {


    override fun compute(): CachedValueProvider.Result<Map<String, String>> {
        var mapToCache = map
        if (mapToCache == null) {
            mapToCache = findPackages()
        }
        return CachedValueProvider.Result.create(mapToCache, xtextFile)
    }

    private fun findPackages(): Map<String, String> {
        return mapOf()
    }


}

