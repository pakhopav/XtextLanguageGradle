package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.File
import java.io.IOException


open class Generator(val extention: String, val fileModel: XtextMainModel) {
    internal val myGenDir = "src/main/java/com/intellij/${extention.toLowerCase()}Language/${extention.toLowerCase()}"
    internal val packageDir = "com.intellij.${extention.toLowerCase()}Language.${extention.toLowerCase()}"



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
        genenerateNamedElementFile()
        geneneratePsiImplUtilFile()
//        generateElementFactoryFile()
        generateNameVisitorFile()
        generateReferenceContributorFile()
        generateReferenceFile()
        generateUtilFile()
        generateXmlExtentions()


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
        val bnfGenerator = BnfGenerator(extention, fileModel)
        bnfGenerator.generateBnf()
    }


    private fun generateLanguageFile() {
        val generator = LanguageFileGenerator(extention, fileModel)
        generator.generateLanguageFile()
    }

    private fun generateFileTypeFile() {
        val generator = FileTypeGenerator(extention, fileModel)
        generator.generateFileTypeFile()
    }

    private fun generateIconsFile() {
        val generator = IconsFileGenerator(extention, fileModel)
        generator.generateIconsFile()
    }

    private fun generateFileTypeFactoryFile() {
        val generator = FileTypeFactoryFileGenerator(extention, fileModel)
        generator.generateFileTypeFactoryFile()
    }

    private fun generateTokenTypeFile() {
        val generator = TokenTupeFileGenerator(extention, fileModel)
        generator.generateTokenTypeFile()
    }

    private fun generateElementTypeFile() {
        val generator = ElementTypeFileGenerator(extention, fileModel)
        generator.generateElementTypeFile()
    }

    private fun generateLexerFile() {
        val generator = LanguageFileGenerator(extention, fileModel)
        generator.generateLanguageFile()
    }

    private fun generateFlexFile() {
        val generator = FlexFileGenerator(extention, fileModel)
        generator.generateFlexFile()
    }

    private fun generateXmlExtentions() {
        val generator = XmlExtentionsGenerator(extention, fileModel)
        generator.generateXmlExtentions()
    }

    private fun generateLexerAdapterFile() {
        val generator = LexerAdapterFileGenerator(extention, fileModel)
        generator.generateLexerAdapterFile()
    }

    private fun generateRootFileFile() {
        val generator = RootFileGenerator(extention, fileModel)
        generator.generateRootFileFile()
    }

    private fun generateParserDefinitionFile() {
        val generator = ParserDefinitionFileGenerator(extention, fileModel)
        generator.generateParserDefinitionFile()

    }

    private fun generateCompositeElementFile() {
        val generator = CompositeElementFileGenerator(extention, fileModel)
        generator.generateCompositeElementFile()
    }

    private fun generateSyntaxHighlighterFile() {
        val generator = SyntaxHighlighterFileGenerator(extention, fileModel)
        generator.generateSyntaxHighlighterFile()
    }

    private fun generateSyntaxHighlighterFactoryFile() {
        val generator = SyntaxHighlighterFactoryFileGenerator(extention, fileModel)
        generator.generateSyntaxHighlighterFactoryFile()
    }

    private fun generateCompletionContributorFile() {
        val generator = CompletionContributorFileGenerator(extention, fileModel)
        generator.generateCompletionContributorFile()
    }

    private fun genenerateNamedElementFile() {
        val generator = NamedElementFileGenerator(extention, fileModel)
        generator.genenerateNamedElementFile()
    }

    private fun geneneratePsiImplUtilFile() {
        val generator = PsiImplUtilFileGenerator(extention, fileModel)
        generator.geneneratePsiImplUtilFile()
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

    private fun generateNameVisitorFile() {
        val visitorGenerator = VisitorGenerator(extention, fileModel)
        visitorGenerator.generateNameVisitor()

    }

    private fun generateReferenceContributorFile() {
        val generator = ReferenceContributorFileGenerator(extention, fileModel)
        generator.generateReferenceContributorFile()
    }

    private fun generateReferenceFile() {
        val generator = ReferenceFileGenerator(extention, fileModel)
        generator.generateReferenceFile()
    }

    private fun generateUtilFile() {
        val generator = UtilFileGenerator(extention, fileModel)
        generator.generateUtilFile()
    }

}



