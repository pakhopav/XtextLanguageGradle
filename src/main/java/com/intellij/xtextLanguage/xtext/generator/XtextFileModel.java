package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XtextFileModel {

    private List<ReferenceElement> myReferences = new ArrayList<>();
    private List<ParserRule> myParserRules = new ArrayList<>();
    private List<TerminalRule> myTerminalRules = new ArrayList<>();
    private List<EnumRule> myEnumRules = new ArrayList<>();
    private XtextREFERENCEGrammarGrammarID[] myImportedGrammars = null;
    private XtextFile myFile;
    private BnfGeneratorUtil generatorUtil = new BnfGeneratorUtil(this);
    private XtextGeneratedMetamodel[] myGeneratedMetamodels;
    private XtextReferencedMetamodel[] myReferencedMetamodels;
    public List<PsiFile> myImportedGrammarsFiles = new ArrayList<>();


    public XtextFileModel(XtextFile myFile) {
        this.myFile = myFile;
        myGeneratedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextGeneratedMetamodel.class);
        myReferencedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextReferencedMetamodel.class);
        myImportedGrammars = PsiTreeUtil.getChildrenOfType(myFile, XtextREFERENCEGrammarGrammarID.class);
        XtextParserRule[] parserRules = PsiTreeUtil.getChildrenOfType(myFile, XtextParserRule.class);
        if (parserRules == null) {
            parserRules = new XtextParserRule[0];
        }
        myParserRules = generatorUtil.culParserRules(parserRules);
        XtextTerminalRule[] terminalRules = PsiTreeUtil.getChildrenOfType(myFile, XtextTerminalRule.class);
        if (terminalRules != null) {
            for (XtextTerminalRule rule : terminalRules) {
                myTerminalRules.add(new TerminalRule(rule));
            }
        }
        XtextEnumRule[] enumRules = PsiTreeUtil.getChildrenOfType(myFile, XtextEnumRule.class);
        if (enumRules != null) {
            for (XtextEnumRule rule : enumRules) {
                myEnumRules.add(new EnumRule(rule));
            }
        }
        for (ParserRule parserRule : myParserRules) {
            registerReferences(parserRule.getMyRule());
        }

    }


    public XtextREFERENCEGrammarGrammarID[] getMyImportedGrammars() {
        return myImportedGrammars;
    }

    public XtextFile getMyFile() {
        return myFile;
    }

    public List<EnumRule> getMyEnumRules() {
        return myEnumRules;
    }

    public List<ParserRule> getMyParserRules() {
        return myParserRules;
    }

    public List<ReferenceElement> getMyReferences() {
        return myReferences;
    }

    public List<TerminalRule> getMyTerminalRules() {
        return myTerminalRules;
    }

    public ParserRule getParserRuleByName(String name) {
        for (ParserRule rule : myParserRules) {
            if (rule.getName().equals(name)) return rule;
        }
        return null;
    }

    public TerminalRule getTerminalRuleByName(String name) {
        return myTerminalRules.stream()
                .filter(it -> name.equals(it.getName()))
                .findFirst().orElse(null);
    }

    public PsiFile getImportedGrammarFileByName(String name) {
        return myImportedGrammarsFiles.stream()
                .filter(it -> name.equals(it.getName()))
                .findFirst().orElse(null);
    }

    private void registerReferences(XtextAlternatives alternatives) {
        if (alternatives == null) return;
        List<XtextConditionalBranch> branches = alternatives.getConditionalBranchList();
        for (XtextConditionalBranch branch : branches) {
            if (branch == null) return;
            XtextUnorderedGroup unorderedGroup = branch.getUnorderedGroup();
            if (unorderedGroup == null) return;
            List<XtextGroup> groups = unorderedGroup.getGroupList();
            if (groups.size() == 0) return;
            List<XtextAbstractToken> tokens = new LinkedList<>();
            for (XtextGroup group : groups) {
                tokens.addAll(group.getAbstractTokenList());
            }
            if (tokens.size() == 0) return;
            List<XtextAbstractTokenWithCardinality> abstractTokens = new LinkedList<>();
            for (XtextAbstractToken token : tokens) {
                if (token.getAbstractTokenWithCardinality() != null) {
                    abstractTokens.add(token.getAbstractTokenWithCardinality());
                }
            }
            if (abstractTokens.size() == 0) return;
            for (XtextAbstractTokenWithCardinality element : abstractTokens) {
                if (element.getAssignment() != null) {
                    XtextAssignment assignment = element.getAssignment();
                    XtextAssignableTerminal terminal = assignment.getAssignableTerminal();
                    if (terminal.getCrossReference() != null) {
                        XtextCrossReference crossReference = terminal.getCrossReference();
                        String referenceName = "REFERENCE_" + crossReference.getTypeRef().getText().replace("::", "-");
                        String referenseType = "ID";
                        if (crossReference.getCrossReferenceableTerminal() != null) {
                            referenceName += "_" + crossReference.getCrossReferenceableTerminal().getText();
                            referenseType = crossReference.getCrossReferenceableTerminal().getText();
                        }
                        ReferenceElement newReferece = new ReferenceElement(referenceName, referenseType);
                        if (!existsAlready(newReferece)) myReferences.add(newReferece);
                    }
                } else if (element.getAbstractTerminal() != null) {
                    if (element.getAbstractTerminal().getParenthesizedElement() != null) {
                        registerReferences(element.getAbstractTerminal().getParenthesizedElement().getAlternatives());
                    } else if (element.getAbstractTerminal().getPredicatedGroup() != null) {
                        registerReferences(element.getAbstractTerminal().getPredicatedGroup().getAlternatives());
                    }
                }
            }
        }
    }

    private boolean existsAlready(ReferenceElement element) {
        for (ReferenceElement referenceElement : myReferences) {
            if (referenceElement.equals(element)) return true;
        }
        return false;
    }
    private void registerReferences(XtextParserRule rule) {
        if (rule == null) return;
        registerReferences(rule.getAlternatives());
    }

}
