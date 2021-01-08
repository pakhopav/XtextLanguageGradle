package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class CachedValueProviderGenerator(extension: String) : AbstractGenerator(extension) {
    fun generateCachedValueProvider() {
        val file = createFile(extension.capitalize() + "CachedValueProvider.kt", myGenDir + "/inspection")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.inspection
            |
            |import com.intellij.psi.PsiFile
            |import com.intellij.psi.util.CachedValueProvider
            |import com.intellij.${extension}Language.${extension}.emf.${extensionCapitalized}EmfCreator
            |import com.intellij.xtextLanguage.xtext.emf.BridgeResult
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
