package com.intellij.xtextLanguage.xtext.generator

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter


class BnfGenerator(val extention: String, val fileModel: XtextFileModel) {
    internal val myGenDir = "src/main/java/com/intellij/${extention.toLowerCase()}Language/${extention.toLowerCase()}"
    internal val packageDir = "com.intellij.${extention.toLowerCase()}Language.${extention.toLowerCase()}"
    val generatorUtil = BnfGeneratorUtil(fileModel)



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
        generateXmlExtentions()


    }

    private fun generateTerminalRules(out: PrintWriter) {
        out.print("    tokens = [\n")
        fileModel.myTerminalRules.forEach {
            if (generatorUtil.getRegexpAsString(it.myRule) != "") {
                out.print("      ${it.name} =\"regexp:${generatorUtil.getRegexpAsString(it.myRule)}\"\n")
            }

        }
        generateKeywordTokens(out)
        out.print("    ]\n")
    }

    private fun generateAttributes(out: PrintWriter) {
        out.print("""
          |    parserClass="$packageDir.parser.${extention}Parser"
        
          |    extends="$packageDir.psi.impl.${extention}PsiCompositeElementImpl"
          |    psiClassPrefix="${extention}"
          |    psiImplClassSuffix="Impl"
          |    psiPackage="$packageDir.psi"
          |    psiImplPackage="$packageDir.impl"

          |    elementTypeHolderClass="$packageDir.psi.${extention}Types"
          |    elementTypeClass="$packageDir.psi.${extention}ElementType"
          |    tokenTypeClass="$packageDir.psi.${extention}TokenType"
          |    parserUtilClass= "com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy"
          |    generateTokenAccessors=true
          |    generateTokens=true
          |    extraRoot(".*")= true
                """.trimMargin("|"))
        out.print("\n")
    }

    private fun generateKeywordTokens(out: PrintWriter) {
        fileModel.myKeywordsWithNames.keys.forEach { out.print("      ${fileModel.myKeywordsWithNames.get(it)} = ${it}\n") }
    }

    private fun generateReferences(out: PrintWriter) {
        fileModel.myReferences.forEach { out.print("${it.name} ::= ${it.referenceType}\n") }
    }

    private fun generateEnumRules(out: PrintWriter) {
        fileModel.myEnumRules.forEach { out.print("${it.name} ::= ${generatorUtil.getEnumRuleDeclarationsAsString(it.myRule)}\n") }

    }

    private fun generateRules(out: PrintWriter) {
        fileModel.myParserRules.forEach { out.print("${generatorUtil.nameWithCaret(it.name)} ::= ${generatorUtil.getRuleAlternativesAsString(it.myRule)}\n") }
    }

    private fun createFile(fileName: String, filePath: String): File {
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

    private fun generateBnfFile() {
        val file = createFile(extention + ".bnf", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("{\n")
        generateTerminalRules(out)
        generateAttributes(out)
        out.print("}\n")
        generateRules(out)
        generateEnumRules(out)
        generateReferences(out)
        out.close()
    }


    private fun generateLanguageFile() {
        val file = createFile(extention + "Language.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;

            |import com.intellij.lang.Language;

            |public class ${extention}Language extends Language {
            |    public static final ${extention}Language INSTANCE = new ${extention}Language();

            |    private ${extention}Language() {
            |        super("${extention}");

            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateFileTypeFile() {
        val file = createFile(extention + "FileType.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;

            |import com.intellij.openapi.fileTypes.LanguageFileType;
            |import org.jetbrains.annotations.*;
            |import javax.swing.*;

            |public class ${extention}FileType extends LanguageFileType {
            |    public static final ${extention}FileType INSTANCE = new ${extention}FileType();

            |    private ${extention}FileType() {
            |        super(${extention}Language.INSTANCE);
            |    }

            |    @NotNull
            |    @Override
            |    public String getName() {
            |        return "${extention} file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDescription() {
            |        return "${extention} language file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDefaultExtension() {
            |        return "${extention.toLowerCase()}";
            |    }
            
            |    @Nullable
            |    @Override
            |    public Icon getIcon() {
            |        return ${extention}Icons.FILE; 
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateIconsFile() {
        val file = createFile(extention + "Icons.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.util.IconLoader;
            
            |import javax.swing.*;
            
            |public class ${extention}Icons {
            |    public static final Icon FILE = IconLoader.getIcon("/icons/simpleIcon.png");
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateFileTypeFactoryFile() {
        val file = createFile(extention + "FileTypeFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.fileTypes.*;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention}FileTypeFactory extends FileTypeFactory {
            |    public ${extention}FileTypeFactory(){
            
            |    }
            |    @Override
            |    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
            |        fileTypeConsumer.consume(${extention}FileType.INSTANCE, "${extention.toLowerCase()}");
            |    }
            
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateTokenTypeFile() {
        val file = createFile(extention + "TokenType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.${extention}Language;
            |import org.jetbrains.annotations.NonNls;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention}TokenType extends IElementType {
            |    @NotNull
            |    private final String myDebugName;
            |    public ${extention}TokenType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extention}Language.INSTANCE);
            |        myDebugName = debugName;
            |    }
            |    public String getDebugName(){
            |        return myDebugName;
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateElementTypeFile() {
        val file = createFile(extention + "ElementType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            
            |import $packageDir.${extention}Language;
            |import com.intellij.psi.tree.IElementType;
            |import org.jetbrains.annotations.*;
            
            |public class ${extention}ElementType extends IElementType {
            |    private String debugName;
            |    public ${extention}ElementType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extention}Language.INSTANCE);
            |        this.debugName = debugName;
            |    }
            |    public String getDebugName(){
            |        return debugName;
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

    private fun generateLexerFile() {
        val file = createFile(extention + "Lexer.java", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.grammar;
            
            |import com.intellij.lexer.FlexAdapter;
            |import $packageDir._${extention}Lexer;
            |
            |public class ${extention}Lexer extends FlexAdapter {

            |    public ${extention}Lexer() {
            |        super(new _${extention}Lexer());
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }

    private fun generateFlexFile() {
        val file = createFile(extention + ".flex", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.lexer.FlexLexer;
            |import com.intellij.psi.tree.IElementType;
            |import static com.intellij.psi.TokenType.BAD_CHARACTER; // Pre-defined bad character token.
            |import static com.intellij.psi.TokenType.WHITE_SPACE; // Pre-defined whitespace character token.
            |import static $packageDir.psi.${extention}Types.*; // Note that is the class which is specified as `elementTypeHolderClass`
            
            |%%
            
            |%public
            |%class _${extention}Lexer // Name of the lexer class which will be generated.
            |%implements FlexLexer
            |%function advance
            |%type IElementType
            |%unicode
            |%{
            |    public _${extention}Lexer(){
            |        this((java.io.Reader)null);
            |    }
            |%}

        """.trimMargin("|"))
        fileModel.myTerminalRules.forEach {
            if (generatorUtil.getRegexpAsString(it.myRule) != "") {
                if (it.name.equals("WS")) out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
                else out.print("${it.name} =${generatorUtil.getFlexRegexp(generatorUtil.getRegexpAsString(it.myRule))}\n")
            }
            if (fileModel.getTerminalRuleByName("WS") == null) out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
        }
        out.print("%%\n<YYINITIAL> {\n")
        fileModel.myKeywordsWithNames.keys.forEach { out.print("\"${it.substring(1, it.length - 1)}\" {return ${fileModel.myKeywordsWithNames.get(it)};}\n") }
        fileModel.myTerminalRules.forEach {
            if (generatorUtil.getRegexpAsString(it.myRule) != "") {
                if (it.name.equals("WS")) out.print("{WS} {return WHITE_SPACE;}\n")
                else out.print("{${it.name}} {return ${it.name};}\n")
            }
            if (fileModel.getTerminalRuleByName("WS") == null) out.print("{WS} {return WHITE_SPACE;}\n")

        }
        out.print("}\n[^] { return BAD_CHARACTER; }")
        out.close()

    }

    private fun generateXmlExtentions() {
        val file = createFile(extention + "Plugin.xml", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |<extensions defaultExtensionNs="com.intellij">
            |    <fileTypeFactory implementation="${packageDir}.${extention}FileTypeFactory"/>
            |    <lang.parserDefinition language="${extention}" implementationClass="${packageDir}.${extention}ParserDefinition"/>
            |    <lang.syntaxHighlighterFactory language="${extention}" implementationClass="${packageDir}.${extention}SyntaxHighlighterFactory"/>
            |    <completion.contributor language="${extention}" implementationClass="${packageDir}.${extention}CompletionContributor"/>
            |</extensions>
        """.trimMargin("|"))
        out.close()

    }

    private fun generateLexerAdapterFile() {
        val file = createFile(extention + "LexerAdapter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lexer.FlexAdapter;
            |import java.io.Reader;
            
            |public class ${extention}LexerAdapter extends FlexAdapter {
            |    public ${extention}LexerAdapter() {
            |        super(new _${extention}Lexer((Reader) null));
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateRootFileFile() {
        val file = createFile(extention + "File.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            |import com.intellij.extapi.psi.PsiFileBase;
            |import com.intellij.openapi.fileTypes.FileType;
            |import com.intellij.psi.FileViewProvider;
            |import $packageDir.*;
            |import org.jetbrains.annotations.NotNull;
            |import javax.swing.*;
            
            |public class ${extention}File extends PsiFileBase {
            |    public ${extention}File(@NotNull FileViewProvider viewProvider) {
            |        super(viewProvider, ${extention}Language.INSTANCE);
            |    }
                
            |    @NotNull
            |    @Override
            |    public FileType getFileType() {
            |        return ${extention}FileType.INSTANCE;
            |    }
            
            |    @Override
            |    public String toString() {
            |        return "${extention} File";
            |    }
            
            |    @Override
            |    public Icon getIcon(int flags) {
            |        return super.getIcon(flags);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateParserDefinitionFile() {
        val file = createFile(extention + "ParserDefinition.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.lang.ParserDefinition;
            |import com.intellij.lang.PsiParser;
            |import com.intellij.lexer.Lexer;
            |import com.intellij.openapi.project.Project;
            |import com.intellij.psi.FileViewProvider;
            |import com.intellij.psi.PsiElement;
            |import com.intellij.psi.PsiFile;
            |import com.intellij.psi.TokenType;
            |import com.intellij.psi.tree.IFileElementType;
            |import com.intellij.psi.tree.TokenSet;
            |import $packageDir.parser.${extention}Parser;
            |import $packageDir.psi.${extention}Types;
            |import $packageDir.psi.${extention}File;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention}ParserDefinition implements ParserDefinition {
            |    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
            |    public static final TokenSet KEYWORDS = TokenSet.create(
            """.trimMargin("|"))

        fileModel.myKeywordsForParserDefinition.forEach {
            out.print("    ${extention}Types.${it}")
            if (it != fileModel.myKeywordsForParserDefinition.last()) out.print(",\n")

        }
        out.print(");\n")
        if (fileModel.getTerminalRuleByName("ML_COMMENT") != null && fileModel.getTerminalRuleByName("SL_COMMENT") != null) {
            out.print("    public static final TokenSet COMMENTS = TokenSet.create(${extention}Types.SL_COMMENT, ${extention}Types.ML_COMMENT);")
        } else out.print("    public static final TokenSet COMMENTS = null;")
        out.print("""
            |    public static final IFileElementType FILE = new IFileElementType(${extention}Language.INSTANCE);
            
            |    @NotNull
            |    @Override
            |    public Lexer createLexer(Project project) {
            |        return new ${extention}LexerAdapter();
            |    }
            
            |    @NotNull
            |    public TokenSet getWhitespaceTokens() {
            |         return WHITE_SPACES;
            |    }
            |    @NotNull
            |    public TokenSet getCommentTokens() {
            |        return COMMENTS;
            |    }
            
            
            
            |    @NotNull
            |    public TokenSet getStringLiteralElements() {
            |        return TokenSet.EMPTY;
            |    }
            
            |    @NotNull
            |    public PsiParser createParser(final Project project) {
            |        return new ${extention}Parser();
            |    }
            
            |    @Override
            |    public IFileElementType getFileNodeType() {
            |        return FILE;
            |    }
            
            |    public PsiFile createFile(FileViewProvider viewProvider) {
            |        return new ${extention}File(viewProvider);
            |    }
            
            |    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
            |        return SpaceRequirements.MAY;
            |    }
            
            |    @NotNull
            |    public PsiElement createElement(ASTNode node) {
            |        return ${extention}Types.Factory.createElement(node);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateCompositeElementFile() {
        val file = createFile(extention + "PsiCompositeElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
            |import com.intellij.extapi.psi.ASTWrapperPsiElement;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.psi.PsiReference;
            |import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention}PsiCompositeElementImpl extends ASTWrapperPsiElement {
            |    public ${extention}PsiCompositeElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            
            |    @NotNull
            |    @Override
            |    public PsiReference[] getReferences() {
            |        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateSyntaxHighlighterFile() {
        val file = createFile(extention + "SyntaxHighlighter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lexer.Lexer;
            |import com.intellij.openapi.editor.*;
            |import com.intellij.openapi.editor.colors.TextAttributesKey;
            |import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.psi.${extention}Types;
            |import org.jetbrains.annotations.NotNull;
            
            |import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
            
            |public class ${extention}SyntaxHighlighter extends SyntaxHighlighterBase {
            
            |    public static final TextAttributesKey KEYWORD =
            |        createTextAttributesKey("${extention.toUpperCase()}_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
            |    public static final TextAttributesKey VALUE =
            |        createTextAttributesKey("${extention.toUpperCase()}_VALUE", DefaultLanguageHighlighterColors.STRING);
            |    public static final TextAttributesKey COMMENT =
            |        createTextAttributesKey("${extention.toUpperCase()}_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
            |    public static final TextAttributesKey BAD_CHARACTER =
            |        createTextAttributesKey("${extention.toUpperCase()}_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
            
            |    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
            |    private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEYWORD};
            |    private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
            |    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
            |    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];
            
            |    @NotNull
            |    @Override
            |    public Lexer getHighlightingLexer() {
            |        return new ${extention}LexerAdapter();
            |    }
            
            |    @NotNull
            |    @Override
            |    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            |        if (${extention}ParserDefinition.KEYWORDS.contains(tokenType)) {
            |            return KEY_KEYS;""".trimMargin("|"))
        if (fileModel.getTerminalRuleByName("STRING") != null) {
            out.print("""
            |        } else if (tokenType.equals(${extention}Types.STRING)) {
            |            return VALUE_KEYS;
        """.trimMargin("|"))
        }
        out.print("""
            |        } else if (${extention}ParserDefinition.COMMENTS.contains(tokenType)) {
            |            return COMMENT_KEYS;
            |        }  else {
            |            return EMPTY_KEYS;
            |        }
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateSyntaxHighlighterFactoryFile() {
        val file = createFile(extention + "SyntaxHighlighterFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.openapi.fileTypes.*;
            |import com.intellij.openapi.project.Project;
            |import com.intellij.openapi.vfs.VirtualFile;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention}SyntaxHighlighterFactory extends SyntaxHighlighterFactory {
            |    @NotNull
            |    @Override
            |    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
            |        return new ${extention}SyntaxHighlighter();
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

    private fun generateCompletionContributorFile() {
        val file = createFile(extention + "CompletionContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.codeInsight.completion.CompletionContributor;
            |import com.intellij.codeInsight.completion.CompletionType;
            |import com.intellij.languageUtil.completion.KeywordCompletionProvider;
            |import $packageDir.psi.${extention}TokenType;
            |import $packageDir.psi.${extention}File;
            
            |import static com.intellij.patterns.PlatformPatterns.psiElement;
            
            
            |public class ${extention}CompletionContributor extends CompletionContributor {
            |public ${extention}CompletionContributor() {
            
            |extend(CompletionType.BASIC, psiElement().withLanguage(${extention}Language.INSTANCE)
            |,
            |new KeywordCompletionProvider<${extention}File, ${extention}TokenType>(${extention}Language.INSTANCE, ${extention}FileType.INSTANCE, ${extention}TokenType.class));
            
            |}
            |}


        """.trimMargin("|"))
        out.close()

    }

}



