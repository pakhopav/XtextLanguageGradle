package com.intellij.smalljavaLanguage.smalljava.inspection

import com.intellij.psi.PsiFile
import com.intellij.psi.util.CachedValueProvider
import com.intellij.smalljavaLanguage.smalljava.emf.SmalljavaEmfCreator
import com.intellij.xtextLanguage.xtext.emf.BridgeResult

class SmalljavaCachedValueProvider(val psiFile: PsiFile) : CachedValueProvider<BridgeResult> {
    override fun compute(): CachedValueProvider.Result<BridgeResult> {
        val bridgeCreator = SmalljavaEmfCreator()
        val result = bridgeCreator.createBridge(psiFile)

        return CachedValueProvider.Result.create(result, psiFile)
    }
}