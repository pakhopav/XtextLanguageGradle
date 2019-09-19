package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class BnfGeneratorOld {
    String extention;
    String myGenDir = "src/main/java/com/intellij/xtextLanguage/xtext/grammar/";
    XtextFileModel myFileModel;
    File myBnfFile;
    PrintWriter out;


    public BnfGeneratorOld(String extention, XtextFileModel fileModel) {
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
        generateEnumRules();
        generateReferences();

        out.close();
    }

    private void generateReferences() {
        if (myFileModel.getMyReferences().size() == 0) return;
        else {
            for (ReferenceElement element : myFileModel.getMyReferences()) {
                out.print(element.getName());
                out.print("::=");
                out.print(element.getReferenceType() + "\n");
            }
        }
    }

    private void generateRules() {
        for (ParserRule rule : myFileModel.getMyParserRules()) {
            out.print(rule.getName());
            out.print(" ::= ");
            out.print(getRuleAlternativesAsString(rule.getMyRule()) + "\n");


        }
    }

    private void generateEnumRules() {
        for (EnumRule rule : myFileModel.getMyEnumRules()) {
            out.print(rule.getName());
            out.print(" ::= ");
            out.print(getRuleAlternativesAsString(rule.getMyRule()) + "\n");


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
            out.print(rule.getName());
            out.print(" = \"regexp:");
            out.print(getRegexpAsString(rule.getMyRule()));
            out.print("\"\n");
        }

        out.print("    ]\n");


    }
    public String getRuleAlternativesAsString(XtextParserRule rule) {
        String result = "";
        if (rule == null) return null;
        List<XtextConditionalBranch> branches = rule.getAlternatives().getConditionalBranchList();
        for (XtextConditionalBranch b : branches) {
            result = writebrunch(b, result);
            result += "| ";
        }
        result = result.substring(0, result.length() - 2);
        return result;
    }


    protected String writebrunch(XtextConditionalBranch branch, String result) {
        if (branch == null) return null;
        XtextUnorderedGroup unorderedGroup = branch.getUnorderedGroup();
        if (unorderedGroup == null) return null;
        List<XtextGroup> groups = unorderedGroup.getGroupList();
        if (groups == null || groups.size() == 0) return null;
        List<XtextAbstractToken> tokens = new LinkedList<>();
        for (XtextGroup group : groups) {
            tokens.addAll(group.getAbstractTokenList());
        }
        if (tokens.size() == 0) return null;
        List<XtextAbstractTokenWithCardinality> abstractTokens = new LinkedList<>();
        for (XtextAbstractToken token : tokens) {
            if (token.getAbstractTokenWithCardinality() != null) {
                abstractTokens.add(token.getAbstractTokenWithCardinality());
            }
        }
        if (abstractTokens.size() == 0) return null;
        for (XtextAbstractTokenWithCardinality element : abstractTokens) {
            if (element.getAbstractTerminal() != null) {
                XtextAbstractTerminal xtextAbstractTerminal = element.getAbstractTerminal();

                if (xtextAbstractTerminal.getKeyword() != null) {
                    XtextKeyword keyword = xtextAbstractTerminal.getKeyword();
                    result += keyword.getText() + " ";
                } else if (xtextAbstractTerminal.getRuleCall() != null) {
                    XtextRuleCall xtextRuleCall = xtextAbstractTerminal.getRuleCall();
                    result += xtextRuleCall.getREFERENCEAbstractRuleRuleID().getText() + " ";
                    // ??? ('<' NamedArgument (',' NamedArgument)* '>')?
                } else if (xtextAbstractTerminal.getParenthesizedElement() != null) {
                    XtextParenthesizedElement xtextParenthesizedElement = xtextAbstractTerminal.getParenthesizedElement();
                    result += "(";
                    List<XtextConditionalBranch> branches = xtextParenthesizedElement.getAlternatives().getConditionalBranchList();
                    for (XtextConditionalBranch b : branches) {
                        result = writebrunch(b, result);
                        result += "| ";
                    }
                    result = result.substring(0, result.length() - 2);
                    result += ") ";
                } else if (xtextAbstractTerminal.getPredicatedKeyword() != null) {
                    XtextPredicatedKeyword xtextPredicatedKeyword = xtextAbstractTerminal.getPredicatedKeyword();
                    result += xtextPredicatedKeyword.getText() + " ";
                    // ??? ('<' NamedArgument (',' NamedArgument)* '>')?
                } else if (xtextAbstractTerminal.getPredicatedRuleCall() != null) {
                    XtextPredicatedRuleCall xtextPredicatedRuleCall = xtextAbstractTerminal.getPredicatedRuleCall();
                    result += xtextPredicatedRuleCall.getREFERENCEAbstractRuleRuleID().getText() + " ";
                    // ??? ('<' NamedArgument (',' NamedArgument)* '>')?
                } else if (xtextAbstractTerminal.getPredicatedGroup() != null) {
                    XtextPredicatedGroup xtextPredicatedGroup = xtextAbstractTerminal.getPredicatedGroup();
                    result += "(";
                    List<XtextConditionalBranch> branches = xtextPredicatedGroup.getAlternatives().getConditionalBranchList();
                    for (XtextConditionalBranch b : branches) {
                        result = writebrunch(b, result);
                        result += "| ";
                    }
                    result = result.substring(0, result.length() - 2);
                    result += ") ";
                }
            } else if (element.getAssignment() != null) {
                XtextAssignment assignment = element.getAssignment();
                // ??? ('=>' | '->')? ValidID ('+='|'='|'?=')
                XtextAssignableTerminal terminal = assignment.getAssignableTerminal();
                result = writeAssigmentElement(terminal, result);
            }
            if (element.getAsterisk() != null) {
                result += element.getAsterisk().getText() + " ";
            } else if (element.getPlus() != null) {
                result += element.getPlus().getText() + " ";
            } else if (element.getQuesMark() != null) {
                result += element.getQuesMark().getText() + " ";
            }

        }
        return result;
    }

    protected String writeAssigmentElement(XtextAssignableTerminal element, String result) {

        if (element.getKeyword() != null) {
            XtextKeyword keyword = element.getKeyword();
            result += keyword.getText() + " ";
        } else if (element.getRuleCall() != null) {
            XtextRuleCall ruleCall = element.getRuleCall();
            result += ruleCall.getREFERENCEAbstractRuleRuleID().getText() + " ";
            // ??? ('<' NamedArgument (',' NamedArgument)* '>')?
        } else if (element.getParenthesizedAssignableElement() != null) {
            XtextParenthesizedAssignableElement parenthesizedAssignableElement = element.getParenthesizedAssignableElement();
            result += "( ";
            List<XtextAssignableTerminal> terminals = parenthesizedAssignableElement.getAssignableAlternatives().getAssignableTerminalList();
            for (XtextAssignableTerminal terminal : terminals) {
                result = writeAssigmentElement(terminal, result);
                result += "| ";
            }
            result = result.substring(0, result.length() - 1);
            result += ")";
        } else if (element.getCrossReference() != null) {
            XtextCrossReference crossReference = element.getCrossReference();

            // ???
            String newRef = "REFERENCE_" + crossReference.getTypeRef().getText();
            if (crossReference.getCrossReferenceableTerminal() != null) {
                newRef += "_" + crossReference.getCrossReferenceableTerminal().getText();
            }
            result += newRef + " ";
        }

        return result;
    }

    public String getRegexpAsString(XtextTerminalRule rule) {
        String regexp = "";
        String leafText = "";
        PsiElement colon = rule.getColon();
        PsiElement leaf = PsiTreeUtil.nextLeaf(colon);
        while (leaf != null) {
            if (PsiTreeUtil.isAncestor(rule, leaf, true)) {
                String type = leaf.getNode().getElementType().toString();
                if (type.equals("STRING")) {
                    leafText = leaf.getText().replace("\'", "");
                    leafText = avoidRegexpSymbols(leafText);
                    regexp += leafText;
                } else if (type.equals("!")) {
                    regexp = "";
                    break;
                } else if (type.equals("->")) {
                    regexp += "(?s).*";
                } else if (type.equals("WHITE_SPACE")) {
                    regexp += "";
                } else if (type.equals(";")) {
                    break;
                } else if (type.equals("ID")) {
                    regexp += "ref_" + leaf.getText();


                } else if (type.equals("..")) {
                    char ch = regexp.charAt(regexp.length() - 1);
                    regexp = regexp.substring(0, regexp.length() - 1);
                    regexp += "[" + ch + "-";
                    leaf = PsiTreeUtil.nextLeaf(leaf);
                    if (leaf.getNode().getElementType().toString().equals("WHITE_SPACE")) {
                        leaf = PsiTreeUtil.nextLeaf(leaf);
                    }
                    if (leaf.getNode().getElementType().toString().equals("STRING")) {
                        leafText = leaf.getText().replace("\'", "");
                        leafText = avoidRegexpSymbols(leafText);
                        regexp += leafText;
                        regexp += "]";
                    }


                } else {
                    regexp += leaf.getText();
                }
                leaf = PsiTreeUtil.nextLeaf(leaf);
            } else break;

        }
        return regexp;
    }

    public String avoidRegexpSymbols(String string) {
        String newString = "";
        char[] arr = string.toCharArray();
        for (char ch : arr) {
            if (ch == '*' || ch == '.' || ch == '"') {
                newString += '\\';
                newString += ch;
            } else newString += ch;
        }
        return newString;
    }

    protected String getRuleAlternativesAsString(XtextEnumRule myRule) {
        String result = "";

        return result;
    }

    public <T extends XtextAbstractRule> String[] getAnotations(T rule) {
        String[] anotations;
        XtextAnnotation[] xtextAnnotations = PsiTreeUtil.getChildrenOfType(rule, XtextAnnotation.class);
        if (xtextAnnotations != null && xtextAnnotations.length > 0) {
            anotations = new String[xtextAnnotations.length];
            int i = 0;
            for (XtextAnnotation xtextAnnotation : xtextAnnotations) {
                anotations[i] = xtextAnnotation.getText();
                i++;
            }
            return anotations;
        }
        return null;
    }
}
