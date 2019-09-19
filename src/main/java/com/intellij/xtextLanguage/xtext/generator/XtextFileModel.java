package com.intellij.xtextLanguage.xtext.generator;

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
    private XtextFile myFile;
    private XtextGeneratedMetamodel[] myGeneratedMetamodels;
    private XtextReferencedMetamodel[] myReferencedMetamodels;


    public XtextFileModel(XtextFile myFile) {
        this.myFile = myFile;
        myGeneratedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextGeneratedMetamodel.class);
        myReferencedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextReferencedMetamodel.class);
        XtextParserRule[] parserRules = PsiTreeUtil.getChildrenOfType(myFile, XtextParserRule.class);
        if (parserRules != null) {
            for (XtextParserRule rule : parserRules) {
                myParserRules.add(new ParserRule(rule));
            }
        }
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

    private void registerReferences(XtextParserRule rule) {
        if (rule == null) return;
        List<XtextConditionalBranch> branches = rule.getAlternatives().getConditionalBranchList();
        for (XtextConditionalBranch b : branches) {
            if (b == null) return;
            XtextUnorderedGroup unorderedGroup = b.getUnorderedGroup();
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
                        String referenceName = "REFERENCE_" + crossReference.getTypeRef().getText();
                        String referenseType = "ID";
                        if (crossReference.getCrossReferenceableTerminal() != null) {
                            referenceName += "_" + crossReference.getCrossReferenceableTerminal().getText();
                            referenseType = crossReference.getCrossReferenceableTerminal().getText();
                        }
                        myReferences.add(new ReferenceElement(referenceName, referenseType));
                    }
                }
            }
        }


    }

}
