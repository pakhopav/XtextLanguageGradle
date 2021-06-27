package com.intellij.xtextLanguage.xtext.inspection

import com.intellij.psi.PsiFile
import com.intellij.psi.util.CachedValueProvider
import com.intellij.xtextLanguage.xtext.bridge.BridgeResult
import com.intellij.xtextLanguage.xtext.emf.XtextEmfCreator

class XtextCachedBridgeProvider(val psiFile: PsiFile) : CachedValueProvider<BridgeResult> {
    override fun compute(): CachedValueProvider.Result<BridgeResult> {
        val bridgeCreator = XtextEmfCreator()
        val result = bridgeCreator.createBridge(psiFile)

        return CachedValueProvider.Result.create(result, psiFile)
    }
}

