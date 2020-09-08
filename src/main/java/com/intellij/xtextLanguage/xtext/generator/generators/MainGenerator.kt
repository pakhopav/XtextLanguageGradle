package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.File
import java.io.IOException


open class MainGenerator(extension: String, val fileModel: XtextMainModel) : AbstractGenerator(extension) {

    @Throws(IOException::class)
    fun generate() {

        generateLanguageFile()
        generateFileTypeFile()
        generateIconsFile()
        generateFileTypeFactoryFile()
        generateTokenTypeFile()
        generateElementTypeFile()
        generateBnfFile()
        generateLexerFile()
        generateFlexFile()
        generateLexerAdapterFile()
        generateRootFileFile()
        generateParserDefinitionFile()
        generateCompositeElementFile()
        generateSyntaxHighlighterFile()
        generateSyntaxHighlighterFactoryFile()
        generateCompletionContributorFile()
        generateNamedElementFile()
        generatePsiImplUtilFile()
//        generateElementFactoryFile()
//        generateNameVisitorFile()
        generateReferenceContributorFile()
        generateReferenceFile()
        generateUtilFile()
        generateXmlExtentions()
        generateBridgeFiles()

    }


    companion object {
        fun createFile(fileName: String, filePath: String): File {
            val path = File(filePath)
            val file = File(filePath + "/" + fileName)
            try {
                path.mkdirs()
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return file
        }
    }


    private fun generateBnfFile() {
        val bnfGenerator = BnfGenerator(extension, fileModel)
        bnfGenerator.generateBnf()
    }


    private fun generateLanguageFile() {
        val generator = LanguageFileGenerator(extension)
        generator.generateLanguageFile()
    }

    private fun generateFileTypeFile() {
        val generator = FileTypeGenerator(extension)
        generator.generateFileTypeFile()
    }

    private fun generateIconsFile() {
        val generator = IconsFileGenerator(extension)
        generator.generateIconsFile()
    }

    private fun generateFileTypeFactoryFile() {
        val generator = FileTypeFactoryFileGenerator(extension)
        generator.generateFileTypeFactoryFile()
    }

    private fun generateTokenTypeFile() {
        val generator = TokenTupeFileGenerator(extension)
        generator.generateTokenTypeFile()
    }

    private fun generateElementTypeFile() {
        val generator = ElementTypeFileGenerator(extension)
        generator.generateElementTypeFile()
    }

    private fun generateLexerFile() {
        val generator = LanguageFileGenerator(extension)
        generator.generateLanguageFile()
    }

    private fun generateFlexFile() {
        val generator = FlexFileGenerator(extension, fileModel)
        generator.generateFlexFile()
    }

    private fun generateXmlExtentions() {
        val generator = XmlExtentionsGenerator(extension)
        generator.generateXmlExtentions()
    }

    private fun generateLexerAdapterFile() {
        val generator = LexerAdapterFileGenerator(extension)
        generator.generateLexerAdapterFile()
    }

    private fun generateRootFileFile() {
        val generator = RootFileGenerator(extension)
        generator.generateRootFileFile()
    }

    private fun generateParserDefinitionFile() {
        val generator = ParserDefinitionFileGenerator(extension, fileModel)
        generator.generateParserDefinitionFile()

    }

    private fun generateCompositeElementFile() {
        val generator = CompositeElementFileGenerator(extension)
        generator.generateCompositeElementFile()
    }

    private fun generateSyntaxHighlighterFile() {
        val generator = SyntaxHighlighterFileGenerator(extension, fileModel)
        generator.generateSyntaxHighlighterFile()
    }

    private fun generateSyntaxHighlighterFactoryFile() {
        val generator = SyntaxHighlighterFactoryFileGenerator(extension)
        generator.generateSyntaxHighlighterFactoryFile()
    }

    private fun generateCompletionContributorFile() {
        val generator = CompletionContributorFileGenerator(extension)
        generator.generateCompletionContributorFile()
    }

    private fun generateNamedElementFile() {
        val generator = NamedElementFileGenerator(extension)
        generator.genenerateNamedElementFile()
    }

    private fun generatePsiImplUtilFile() {
        val generator = PsiImplUtilFileGenerator(extension, fileModel.parserRules, fileModel.crossReferences)
        generator.geneneratePsiImplUtilFile()
//        val generator2 = PsiImplUtilFileGenerator2(extension,fileModel.parserRules,  fileModel.crossReferences)
//        generator2.geneneratePsiImplUtilFile()
    }

//    private fun generateElementFactoryFile() {
//        val file = createFile(extention + "ElementFactory.java", myGenDir + "/psi")
//        val out = PrintWriter(FileOutputStream(file))
//        out.print("""
//            |package $packageDir.psi;
//
//            |public abstract class ${extention}ElementFactory  {
//            |
//            |}
//        """.trimMargin("|"))
//        out.close()
//
//    }

//    private fun generateNameVisitorFile() {
//        val visitorGenerator = VisitorGenerator(extension, fileModel.visitorGeneratorModel)
//        visitorGenerator.generateNameVisitor()
//
//    }

    private fun generateReferenceContributorFile() {
        val generator = ReferenceContributorFileGenerator(extension, fileModel.crossReferences)
        generator.generateReferenceContributorFile()
    }

    private fun generateReferenceFile() {
        val generator = ReferenceFileGenerator(extension)
        generator.generateReferenceFile()
    }

    private fun generateUtilFile() {
        val generator = UtilFileGenerator(extension)
        generator.generateUtilFile()
    }

    private fun generateBridgeFiles() {
        val generator = EmfBridgeGenerator(extension, fileModel.bridgeModel)
        generator.generateAllBridgeRuleFiles()
    }

}



