package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.util.CharTable;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.Optional;
            
public class XtextPsiImplUtil {
    public static PsiElement setName(XtextGrammar element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(XtextTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(XtextTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(XtextGrammar element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextGrammar element) {
        if (element.getGrammarID() != null) {
            return element.getGrammarID();
        }
        return null;
    }
    public static PsiElement setName(XtextAbstractRule element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(XtextTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(XtextTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(XtextAbstractRule element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextAbstractRule element) {
        if (element instanceof XtextParserRule) {
            XtextParserRule parserRule = (XtextParserRule) element;
            if (parserRule.getValidID() != null) {
                return parserRule.getValidID();
            }
        }
        if (element instanceof XtextTerminalRule) {
            XtextTerminalRule terminalRule = (XtextTerminalRule) element;
            if (terminalRule.getValidID() != null) {
                return terminalRule.getValidID();
            }
        }
        if (element instanceof XtextEnumRule) {
            XtextEnumRule enumRule = (XtextEnumRule) element;
            if (enumRule.getValidID() != null) {
                return enumRule.getValidID();
            }
        }
        return null;
    }
    public static PsiElement setName(XtextAbstractMetamodelDeclaration element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(XtextTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(XtextTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(XtextAbstractMetamodelDeclaration element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextAbstractMetamodelDeclaration element) {
        if (element instanceof XtextGeneratedMetamodel) {
            XtextGeneratedMetamodel generatedMetamodel = (XtextGeneratedMetamodel) element;
            if (generatedMetamodel.getValidID1() != null) {//Changed to ValidID1 not ValidID
                return generatedMetamodel.getValidID1();
            }
        }
        if (element instanceof XtextReferencedMetamodel) {
            XtextReferencedMetamodel referencedMetamodel = (XtextReferencedMetamodel) element;
            if (referencedMetamodel.getValidID() != null) {
                return referencedMetamodel.getValidID();
            }
        }
        return null;
    }
    public static PsiElement setName(XtextParameter element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(XtextTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(XtextTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(XtextParameter element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextParameter element) {
        if (element.getId() != null) {
            return element.getId();
        }
        return null;
    }
}    
