package com.intellij.xtextLanguage.xtext.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class BnfGenerator {
    String extention;
    String myGenDir = "src/main/java/com/intellij/xtextLanguage/xtext/grammar/";
    XtextFileModel myFileModel;
    File myBnfFile;
    PrintWriter out;


    public BnfGenerator(String extention, XtextFileModel fileModel) {
        this.extention = extention;
        myFileModel = fileModel;
        myBnfFile = new File(myGenDir + extention + ".bnf");
        try {
            myBnfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void generate() throws IOException {
        out = new PrintWriter(new FileOutputStream(myBnfFile));


        generateTokens();
        generateAttributes();
        generateRules();
        generateReferences();

        out.close();
    }

    private void generateReferences() {
        if (myFileModel.myReferences.size() == 0) return;
        else {
            for (ReferenceElement element : myFileModel.myReferences) {
                out.print(element.name);
                out.print("::=");
                out.print(element.referenceType + "\n");
            }
        }
    }

    private void generateRules() {
        for (ParserRule rule : myFileModel.getMyParserRules()) {
            out.print(rule.name);
            out.print(" ::= ");
            out.print(rule.alternatives + "\n");

        }
    }

    private void generateAttributes() {
        out.print(
                "  parserClass=\"com.intellij.xtextLanguage.xtext.parser.XtextParser\"\n" +
                        "\n" +
                        "  extends=\"com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl\"\n" +
                        "  psiClassPrefix=\"Xtext\"\n" +
                        "  psiImplClassSuffix=\"Impl\"\n" +
                        "  psiPackage=\"com.intellij.xtextLanguage.xtext.psi\"\n" +
                        "  psiImplPackage=\"com.intellij.xtextLanguage.xtext.impl\"\n" +
                        "\n" +
                        "  elementTypeHolderClass=\"com.intellij.xtextLanguage.xtext.psi.XtextTypes\"\n" +
                        "  elementTypeClass=\"com.intellij.xtextLanguage.xtext.psi.XtextElementType\"\n" +
                        "  tokenTypeClass=\"com.intellij.xtextLanguage.xtext.psi.XtextTokenType\"\n" +
                        "  psiImplUtilClass=\"com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil\"\n" +
                        "  parserUtilClass= \"com.intellij.xtextLanguage.xtext.parserUtilBase.GeneratedParserUtilBaseCopy\"\n" +
                        "  generateTokenAccessors=true\n" +
                        "  generateTokens=true\n" +
                        "\n" +
                        "}");
    }

    private void generateTokens() {
        out.print("{\n" +
                "    tokens = [\n");
        for (TerminalRule rule : myFileModel.getMyTerminalRules()) {
            out.print(rule.name);
            out.print(" = \"regexp:");
            out.print(rule.regexp);
            out.print("\"\n");

        }

        out.print("    ]\n");


    }


}
