package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class CachedValueProviderGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateCachedValueProvider() {
        val file = createOrFindFile(extension.capitalize() + "CachedValueProvider.kt", myGenDir + "/inspection")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.inspection
            |
            |import com.intellij.psi.PsiFile
            |import com.intellij.psi.util.CachedValueProvider
            |import $packageDir.emf.${extensionCapitalized}EmfCreator
            |import com.intellij.xtextLanguage.xtext.bridge.BridgeResult
            |
            |class ${extensionCapitalized}CachedValueProvider(val psiFile: PsiFile) : CachedValueProvider<BridgeResult> {
            |    override fun compute(): CachedValueProvider.Result<BridgeResult> {
            |        val bridgeCreator = ${extensionCapitalized}EmfCreator()
            |        val result = bridgeCreator.createBridge(psiFile)
            |        
            |        return CachedValueProvider.Result.create(result, psiFile)
            |    }
            |}


        """.trimMargin("|")
        )
        out.close()

    }
}
