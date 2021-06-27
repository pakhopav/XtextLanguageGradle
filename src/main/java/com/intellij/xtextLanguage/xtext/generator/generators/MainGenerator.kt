package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import java.io.File
import java.io.IOException


open class MainGenerator(val extension: String, val context: MetaContext, val rootPath: String) {

    constructor(extension: String, context: MetaContext) : this(extension, context, "src/main/java/com/intellij/")

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
        generateReferenceContributorFile()
        generateReferenceFile()
        generateUtilFile()
        generateXmlExtentions()
        generateBridgeFiles()
//        generateElementFactory()
        generateManipulators()
        generateRefactoringSupportProvider()
        generateWordScanner()
        generateUsagesProvider()
        generateCachedValueProvider()
        generateInspection()
        generateReferencesInspection()
        generateValidator()

    }

    @Throws(IOException::class)
    fun generateOnlyEmf() {
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
        val bnfGenerator = BnfGenerator(extension, context, rootPath)
        bnfGenerator.generateBnf()
    }


    private fun generateLanguageFile() {
        val generator = LanguageFileGenerator(extension, rootPath)
        generator.generateLanguageFile()
    }

    private fun generateFileTypeFile() {
        val generator = FileTypeGenerator(extension, rootPath)
        generator.generateFileTypeFile()
    }

    private fun generateIconsFile() {
        val generator = IconsFileGenerator(extension, rootPath)
        generator.generateIconsFile()
    }

    private fun generateFileTypeFactoryFile() {
        val generator = FileTypeFactoryFileGenerator(extension, rootPath)
        generator.generateFileTypeFactoryFile()
    }

    private fun generateTokenTypeFile() {
        val generator = TokenTypeFileGenerator(extension, rootPath)
        generator.generateTokenTypeFile()
    }

    private fun generateElementTypeFile() {
        val generator = ElementTypeFileGenerator(extension, rootPath)
        generator.generateElementTypeFile()
    }

    private fun generateLexerFile() {
        val generator = LanguageFileGenerator(extension, rootPath)
        generator.generateLanguageFile()
    }

    private fun generateFlexFile() {
        val generator = FlexFileGenerator(extension, context, rootPath)
        generator.generateFlexFile()
    }

    private fun generateXmlExtentions() {
        val generator = XmlExtensionsGenerator(extension, context, rootPath)
        generator.generateXmlExtensions()
    }

    private fun generateLexerAdapterFile() {
        val generator = LexerAdapterFileGenerator(extension, rootPath)
        generator.generateLexerAdapterFile()
    }

    private fun generateRootFileFile() {
        val generator = RootFileGenerator(extension, rootPath)
        generator.generateRootFileFile()
    }

    private fun generateParserDefinitionFile() {
        val generator = ParserDefinitionFileGenerator(extension, context, rootPath)
        generator.generateParserDefinitionFile()

    }

    private fun generateCompositeElementFile() {
        val generator = CompositeElementFileGenerator(extension, rootPath)
        generator.generateCompositeElementFile()
    }

    private fun generateSyntaxHighlighterFile() {
        val generator = SyntaxHighlighterFileGenerator(extension, context, rootPath)
        generator.generateSyntaxHighlighterFile()
    }

    private fun generateSyntaxHighlighterFactoryFile() {
        val generator = SyntaxHighlighterFactoryFileGenerator(extension, rootPath)
        generator.generateSyntaxHighlighterFactoryFile()
    }

    private fun generateCompletionContributorFile() {
        val generator = CompletionContributorFileGenerator(extension, rootPath)
        generator.generateCompletionContributorFile()
    }

    private fun generateNamedElementFile() {
        val generator = NamedElementFileGenerator(extension, rootPath)
        generator.genenerateNamedElementFile()
    }

    private fun generatePsiImplUtilFile() {
        val generator = PsiImplUtilFileGenerator(extension, context, rootPath)
        generator.geneneratePsiImplUtilFile()
    }
    private fun generateReferenceContributorFile() {
        val generator = ReferenceContributorFileGenerator(extension, context, rootPath)
        generator.generateReferenceContributorFile()
    }

    private fun generateReferenceFile() {
        val generator = ReferenceFileGenerator(extension, rootPath)
        generator.generateReferenceFile()
    }

    private fun generateUtilFile() {
        val generator = UtilFileGenerator(extension, rootPath)
        generator.generateUtilFile()
    }

    private fun generateBridgeFiles() {
        val generator = EmfBridgeGenerator(extension, context, rootPath)
        generator.generateAll()
    }

    private fun generateElementFactory() {
        val generator = ElementFactoryGenerator(extension, context, rootPath)
        generator.generateElementFactoryFile()
    }

    private fun generateManipulators() {
        val generator = ManipulatorsGenerator(extension, context, rootPath)
        generator.generateAll()
    }

    private fun generateRefactoringSupportProvider() {
        val generator = RefactoringSupportProviderGenerator(extension, context, rootPath)
        generator.generateRefactoringSupportProvider()

    }

    private fun generateWordScanner() {
        val generator = WordScannerGenerator(extension, rootPath)
        generator.generateWordScanner()
    }

    private fun generateUsagesProvider() {
        val generator = FindUsagesProviderGenerator(extension, context, rootPath)
        generator.generateUsagesProvider()
    }

    private fun generateCachedValueProvider() {
        val generator = CachedValueProviderGenerator(extension, rootPath)
        generator.generateCachedValueProvider()
    }

    private fun generateInspection() {
        val generator = InspectionGenerator(extension, rootPath)
        generator.generateInspection()
    }

    private fun generateReferencesInspection() {
        val generator = ReferencesInspectionGenerator(extension, rootPath)
        generator.generateReferencesInspection()
    }

    private fun generateValidator() {
        val generator = ValidatorGenerator(extension, rootPath)
        generator.generateValidator()
    }
}



