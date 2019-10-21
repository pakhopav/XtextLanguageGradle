package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter


class Generator(val extention: String, val fileModel: XtextMainModel) {
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
        val bnfGenerator = BnfGenerator(fileModel, extention)
        bnfGenerator.generateBnf()
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
        fileModel.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
            } else {
                out.print("${it.name} =")
                it.alterntiveElements.forEach {
                    out.print(it.getFlexName())
                }
                out.print("\n")
            }
            if (fileModel.ruleResolver.getTerminalRuleByName("WS") == null) out.print("WS=[ \\t\\n\\x0B\\f\\r]+\n")
        }
        out.print("%%\n<YYINITIAL> {\n")
        fileModel.keywordModel.keywords.forEach { out.print("\"${it.keyword.substring(1, it.keyword.length - 1)}\" {return ${it.name};}\n") }
        fileModel.terminalRules.forEach {
            if (it.name.equals("WS")) {
                out.print("{WS} {return WHITE_SPACE;}\n")
            } else {
                out.print("{${it.name}} {return ${it.name};}\n")
            }
            if (fileModel.ruleResolver.getTerminalRuleByName("WS") == null) out.print("{WS} {return WHITE_SPACE;}\n")

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
            |    <psi.referenceContributor language="${extention}" implementation="${packageDir}.${extention}ReferenceContributor"/>
            |  
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

        fileModel.keywordModel.keywordsForParserDefinitionFile.forEach {
            out.print("    ${extention}Types.${it}")
            if (it != fileModel.keywordModel.keywordsForParserDefinitionFile.last()) out.print(",\n")

        }
        out.print(");\n")
        if (fileModel.ruleResolver.getTerminalRuleByName("ML_COMMENT") != null && fileModel.ruleResolver.getTerminalRuleByName("SL_COMMENT") != null) {
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
        if (fileModel.ruleResolver.getTerminalRuleByName("STRING") != null) {
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

    private fun genenerateNamedElementFile() {
        val file = createFile(extention + "NamedElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
            
            |import com.intellij.lang.ASTNode;
            |import org.jetbrains.annotations.NotNull;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            
            |public abstract class ${extention}NamedElementImpl extends ${extention}PsiCompositeElementImpl implements PsiNameIdentifierOwner {
            |    public ${extention}NamedElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }

    private fun geneneratePsiImplUtilFile() {
        val file = createFile(extention + "PsiImplUtil.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
                        
            |import com.intellij.psi.*;
            |import $packageDir.psi.*;
            |import com.intellij.psi.util.PsiTreeUtil;
            
            |import java.util.Optional;
            
            |public class ${extention}PsiImplUtil {
            |    static ${extention}NameVisitor nameVisitor = new ${extention}NameVisitor();

        """.trimMargin("|"))
        fileModel.parserRules.stream()
                .filter { it.isReferenced == true }
                .forEach {
                    out.print("""
                       
                        |public static PsiElement setName(${extention}${it.name} element, String newName) {
                        |    //TODO
                        |    return element;
                        |}
                        
                        |public static String getName(${extention}${it.name} element) {
                        |    return Optional.ofNullable(getNameIdentifier(element))
                        |        .map(PsiElement::getText)
                        |        .orElse(null);
                        |}
                        |    
                        |public static PsiElement getNameIdentifier(${extention}${it.name} element) {
                        |    return nameVisitor.visit${it.name}(element);
                        |}
                        
                    """.trimMargin("|"))

                }


        out.print("}")
        out.close()

    }

    private fun generateElementFactoryFile() {
        val file = createFile(extention + "ElementFactory.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
          
            |public abstract class ${extention}ElementFactory  {
            |  
            |}
        """.trimMargin("|"))
        out.close()

    }

    private fun generateNameVisitorFile() {
        val visitorGenerator = VisitorGenerator(fileModel.visitorGeneratorModel, extention)
        visitorGenerator.generateNameVisitor()

    }

    private fun generateReferenceContributorFile() {
        val file = createFile(extention + "ReferenceContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.util.TextRange;
            |import com.intellij.patterns.PlatformPatterns;
            |import com.intellij.psi.*;
            |import com.intellij.util.ProcessingContext;
            |import $packageDir.psi.*;
            |import org.jetbrains.annotations.NotNull;
            
            |import java.util.ArrayList;
            |import java.util.Arrays;
            |import java.util.Collection;
            
            |public class ${extention}ReferenceContributor extends PsiReferenceContributor {
            |@Override
            |public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
            
        """.trimMargin("|"))
        fileModel.referencesModel.references.forEach {
            if (it.targets.size == 0) return@forEach
            val referenceName = it.name.replace("_", "")
            out.print("""
            |registrar.registerReferenceProvider(PlatformPatterns.psiElement(${extention}${referenceName}.class).withLanguage(${extention}Language.INSTANCE),
            |    new PsiReferenceProvider() {
            |        @NotNull
            |        @Override
            |        public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
            |                                                     @NotNull ProcessingContext context){
            |            ${extention}${referenceName} reference = (${extention}${referenceName}) element;
            |            String value = reference.getText();
            |            ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>)Arrays.asList(
            """.trimMargin("|"))
            val targets = it.targets
            targets.forEach {
                out.print("${extention}${it.name}.class")
                if (it !== targets.last()) out.print(", ")
            }
            out.print("));\n")
            out.print("""
            |            return new PsiReference[]{
            |                new ${extention}Reference(element, new TextRange(0, value.length()), list)};
            |            }
            |        });
            """.trimMargin("|"))
        }
        out.print("    }\n}")


        out.close()

    }

    private fun generateReferenceFile() {
        val file = createFile(extention + "Reference.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.codeInsight.lookup.LookupElement;
            |import com.intellij.codeInsight.lookup.LookupElementBuilder;
            |import com.intellij.openapi.util.TextRange;
            |import com.intellij.psi.*;
            |import org.jetbrains.annotations.NotNull;
            |import org.jetbrains.annotations.Nullable;
            
            |import java.util.ArrayList;
            |import java.util.List;
            
            |public class ${extention}Reference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
            |    private String key;
            |    private List<Class<? extends PsiNameIdentifierOwner>> tClasses;
            
            |    public ${extention}Reference(@NotNull PsiElement element, TextRange textRange, List<Class<? extends PsiNameIdentifierOwner>> tclasses) {
            |        super(element, textRange);
            |        key = element.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
            |        this.tClasses = tclasses;
            |    }
            
            |    @NotNull
            |    @Override
            |    public ResolveResult[] multiResolve(boolean incompleteCode) {
            |        return MultiResolve(incompleteCode, tClasses);
            |    }
            
            |    @Nullable
            |    @Override
            |    public PsiElement resolve() {
            |        ResolveResult[] resolveResults = multiResolve(false);
            |        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
            |    }
            
            |    @NotNull
            |    @Override
            |    public Object[] getVariants() {
            |    return ${extention}GetVariants(tClasses);
            |    }
            
            |    public  ResolveResult[] MultiResolve(boolean incompleteCode, final List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |        PsiFile file = myElement.getContainingFile();
            |        List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
            |        classes.forEach(it -> {
            |            elements.addAll((ArrayList)${extention}Util.findElementsInCurrentFile(file, it, key));
            |        });
            |        List<ResolveResult> results = new ArrayList<>();
            |        elements.forEach(it ->{
            |            results.add(new PsiElementResolveResult(it));
            |        });
            
            |        return results.toArray(new ResolveResult[results.size()]);
            |    }
            
            |    public Object[] ${extention}GetVariants( List<Class<? extends PsiNameIdentifierOwner>> classes) {
            |    PsiFile file = myElement.getContainingFile();
            |    List<? extends PsiNameIdentifierOwner> elements = new ArrayList<>();
            |    classes.forEach(it ->{
            |        elements.addAll((ArrayList)${extention}Util.findElementsInCurrentFile(file, it));
            |    });
            |    List<LookupElement> variants = new ArrayList<LookupElement>();
            |    elements.forEach(it ->{
            |        if (it.getName() != null && it.getName().length() > 0) {
            |            variants.add(LookupElementBuilder.create(it).
            |                 withIcon(${extention}Icons.FILE).
            |                 withTypeText(it.getContainingFile().getName())
            |                 );
            |        }
            |    });
            
            |    return variants.toArray();
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }

    private fun generateUtilFile() {
        val file = createFile(extention + "Util.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.psi.PsiFile;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            |import com.intellij.psi.util.PsiTreeUtil;
            |import $packageDir.psi.${extention}File;
            
            |import java.util.ArrayList;
            |import java.util.Collections;
            |import java.util.List;
            
            |public class ${extention}Util {
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extention}File ${extention.decapitalize()}File = (${extention}File) file;
            |        if (${extention.decapitalize()}File != null) {
            |
            |           List<T> elements = new ArrayList (PsiTreeUtil.findChildrenOfType(${extention.decapitalize()}File, tClass));
            |
            |            for (T property : elements) {
            |                if (Id.equals(property.getName())) {
            |                    result.add(property);
            |                }
            |            }
            |
            |        }
            |
            |        return result ;
            |    }
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extention}File ${extention.decapitalize()}File = (${extention}File) file;
            |        if (${extention.decapitalize()}File != null) {
            |            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(${extention.decapitalize()}File, tClass));
            |                result.addAll(elements);
            |            
            |       }
            |
            |        return result;
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }

}



