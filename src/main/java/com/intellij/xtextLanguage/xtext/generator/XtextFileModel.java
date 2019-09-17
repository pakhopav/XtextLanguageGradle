package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.ArrayList;
import java.util.List;

public class XtextFileModel {

    public List<ReferenceElement> myReferences;
    private List<ParserRule> myParserRules;
    private List<TerminalRule> myTerminalRules;
    private List<EnumRule> myEnumRules;
    private XtextFile myFile;
    private XtextGeneratedMetamodel[] myGeneratedMetamodels;
    private XtextReferencedMetamodel[] myReferencedMetamodels;


    public XtextFileModel(XtextFile myFile) {
        this.myFile = myFile;
        myReferences = new ArrayList<>();
        myGeneratedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextGeneratedMetamodel.class);
        myReferencedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextReferencedMetamodel.class);
        XtextParserRule[] parserRules = PsiTreeUtil.getChildrenOfType(myFile, XtextParserRule.class);
        if (parserRules != null) {
            myParserRules = new ArrayList<>();
            for (XtextParserRule rule : parserRules) {
                myParserRules.add(new ParserRule(rule, this));
            }
        }
        XtextTerminalRule[] terminalRules = PsiTreeUtil.getChildrenOfType(myFile, XtextTerminalRule.class);
        if (terminalRules != null) {
            myTerminalRules = new ArrayList<>();
            for (XtextTerminalRule rule : terminalRules) {
                myTerminalRules.add(new TerminalRule(rule));
            }
        }
        XtextEnumRule[] enumRules = PsiTreeUtil.getChildrenOfType(myFile, XtextEnumRule.class);
        if (enumRules != null) {
            myEnumRules = new ArrayList<>();
            for (XtextEnumRule rule : enumRules) {
                myEnumRules.add(new EnumRule(rule));
            }
        }


    }

    public XtextFile getMyFile() {
        return myFile;
    }

    public List<ParserRule> getMyParserRules() {
        return myParserRules;
    }

    public List<TerminalRule> getMyTerminalRules() {
        return myTerminalRules;
    }

    public ParserRule getParserRuleByName(String name) {
        for (ParserRule rule : myParserRules) {
            if (rule.name.equals(name)) return rule;
        }
        return null;
    }

    public TerminalRule getTerminalRuleByName(String name) {
        for (TerminalRule rule : myTerminalRules) {
            if (rule.name.equals(name)) return rule;
        }
        return null;
    }


}
