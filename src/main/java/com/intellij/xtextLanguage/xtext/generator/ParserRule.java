package com.intellij.xtextLanguage.xtext.generator;

import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.LinkedList;
import java.util.List;

public class ParserRule {
    public XtextParserRule myRule;
    public XtextFileModel myModel;
    public String name;
    public boolean isFragment;
    public String returnType;
    public String alternatives;
    public String[] anotations;

    public ParserRule(XtextParserRule rule, XtextFileModel model) {
        myRule = rule;
        myModel = model;
        name = rule.getName();
        isFragment = rule.getFragment() != null;
        returnType = rule.getReturns() != null ? rule.getReturns().getNextSibling().getNextSibling().getText() : null;
        alternatives = getRuleAlternativesAsString(myRule);
        XtextAnnotation[] xtextAnnotations = PsiTreeUtil.getChildrenOfType(myRule, XtextAnnotation.class);
        if (xtextAnnotations != null && xtextAnnotations.length > 0) {
            anotations = new String[xtextAnnotations.length];
            int i = 0;
            for (XtextAnnotation xtextAnnotation : xtextAnnotations) {
                anotations[i] = xtextAnnotation.getText();
                i++;
            }
        }
        registerReferences(myRule);

    }

    private void registerReferences(XtextParserRule rule) {
        if (rule == null) return;
        List<XtextConditionalBranch> branches = rule.getAlternatives().getConditionalBranchList();
        for (XtextConditionalBranch b : branches) {
            if (b == null) return;
            XtextUnorderedGroup unorderedGroup = b.getUnorderedGroup();
            if (unorderedGroup == null) return;
            List<XtextGroup> groups = unorderedGroup.getGroupList();
            if (groups == null || groups.size() == 0) return;
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
                        myModel.myReferences.add(new ReferenceElement(referenceName, referenseType));
                    }
                }
            }
        }


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
}
