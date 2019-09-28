package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class XtextFileModel {

    private List<ReferenceElement> myReferences = new ArrayList<>();
    private List<ParserRule> myParserRules = new ArrayList<>();
    private List<XtextParserRule> xtextParserRules = new ArrayList<>();
    private List<TerminalRule> myTerminalRules = new ArrayList<>();
    private List<EnumRule> myEnumRules = new ArrayList<>();
    private List<String> myKeywords = new ArrayList<>();
    private XtextREFERENCEGrammarGrammarID[] myImportedGrammars = null;
    private XtextFile myFile;
    private BnfGeneratorUtil generatorUtil = new BnfGeneratorUtil(this);
    private XtextGeneratedMetamodel[] myGeneratedMetamodels;
    private XtextReferencedMetamodel[] myReferencedMetamodels;
    private XtextAbstractRule[] myAbstractRules;
    public List<PsiFile> myImportedGrammarsFiles = new ArrayList<>();


    public XtextFileModel(XtextFile myFile) {
        this.myFile = myFile;
        myGeneratedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextGeneratedMetamodel.class);
        myReferencedMetamodels = PsiTreeUtil.getChildrenOfType(myFile, XtextReferencedMetamodel.class);
        myImportedGrammars = PsiTreeUtil.getChildrenOfType(myFile, XtextREFERENCEGrammarGrammarID.class);
        myAbstractRules = PsiTreeUtil.getChildrenOfType(myFile, XtextAbstractRule.class);
        findRules();

        myParserRules = generatorUtil.culParserRules(xtextParserRules);

        for (ParserRule parserRule : myParserRules) {
            registerReferences(parserRule.getMyRule());
            registerKeYWords(parserRule.getMyRule());

        }


    }

    private void findRules() {
        for (XtextAbstractRule abstractRule : myAbstractRules) {
            if (abstractRule.getEnumRule() != null) {
                myEnumRules.add(new EnumRule(abstractRule.getEnumRule()));
            } else if (abstractRule.getParserRule() != null) {
                xtextParserRules.add(abstractRule.getParserRule());
            } else if (abstractRule.getTerminalRule() != null) {
                myTerminalRules.add(new TerminalRule(abstractRule.getTerminalRule()));
            }
        }
    }

    public List<String> getMyKeywords() {
        return myKeywords;
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

    private List<XtextAbstractTokenWithCardinality> getAbstractTokensfromAlternative(XtextAlternatives alternatives) {
        if (alternatives == null) return null;
        List<XtextConditionalBranch> branches = alternatives.getConditionalBranchList();
        List<XtextAbstractTokenWithCardinality> abstractTokens = new LinkedList<>();
        for (XtextConditionalBranch branch : branches) {
            if (branch == null) return null;
            XtextUnorderedGroup unorderedGroup = branch.getUnorderedGroup();
            if (unorderedGroup == null) return null;
            List<XtextGroup> groups = unorderedGroup.getGroupList();
            if (groups.size() == 0) return null;
            List<XtextAbstractToken> tokens = new LinkedList<>();
            for (XtextGroup group : groups) {
                tokens.addAll(group.getAbstractTokenList());
            }
            if (tokens.size() == 0) return null;

            for (XtextAbstractToken token : tokens) {
                if (token.getAbstractTokenWithCardinality() != null) {
                    abstractTokens.add(token.getAbstractTokenWithCardinality());
                }
            }
        }
        return abstractTokens;


    }

    private void registerKeYWords(XtextParserRule rule) {
        if (rule != null) {
            String name = rule.getRuleNameAndParams().getText();
            registerKeYWords(rule.getAlternatives());
        }
    }

    private void registerKeYWords(XtextAlternatives alternatives) {
        List<XtextAbstractTokenWithCardinality> abstractTokens = getAbstractTokensfromAlternative(alternatives);
        if (abstractTokens == null) return;
        for (XtextAbstractTokenWithCardinality element : abstractTokens) {
            if (element.getAssignment() != null) {
                registerKeYWords(new ArrayList<>(Arrays.asList(element.getAssignment().getAssignableTerminal())));
            } else if (element.getAbstractTerminal() != null) {
                if (element.getAbstractTerminal().getKeyword() != null) {
                    String keyword = element.getAbstractTerminal().getKeyword().getText();
                    if (!myKeywords.contains(keyword)) myKeywords.add(keyword);
                } else if (element.getAbstractTerminal().getPredicatedKeyword() != null) {
                    String keyword = element.getAbstractTerminal().getPredicatedKeyword().getText();
                    if (!myKeywords.contains(keyword)) myKeywords.add(keyword);
                } else if (element.getAbstractTerminal().getParenthesizedElement() != null) {
                    registerKeYWords(element.getAbstractTerminal().getParenthesizedElement().getAlternatives());
                } else if (element.getAbstractTerminal().getPredicatedGroup() != null) {
                    registerKeYWords(element.getAbstractTerminal().getPredicatedGroup().getAlternatives());
                }
            }
        }
    }

    private void registerKeYWords(List<XtextAssignableTerminal> terminals) {
        for (XtextAssignableTerminal terminal : terminals) {
            if (terminal.getKeyword() != null) {
                String keyword = terminal.getKeyword().getText();
                if (!myKeywords.contains(keyword)) myKeywords.add(keyword);
            } else if (terminal.getParenthesizedAssignableElement() != null) {
                registerKeYWords(terminal.getParenthesizedAssignableElement().getAssignableAlternatives().getAssignableTerminalList());
            }
        }
    }

    private void registerReferences(XtextAlternatives alternatives) {
        List<XtextAbstractTokenWithCardinality> abstractTokens = getAbstractTokensfromAlternative(alternatives);
        if (abstractTokens == null) return;
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
