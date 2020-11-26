package com.intellij.smalljavaLanguage.smalljava.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.util.CharTable;

import java.util.Optional;

public class SmalljavaPsiImplUtil {
    public static PsiElement setName(SmalljavaSJClass element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(SmalljavaTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(SmalljavaTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(SmalljavaSJClass element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(SmalljavaSJClass element) {
        if (element.getId() != null) {
            return element.getId();
        }
        return null;
    }

    public static PsiElement setName(SmalljavaSJMember element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(SmalljavaTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(SmalljavaTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(SmalljavaSJMember element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(SmalljavaSJMember element) {
        if (element instanceof SmalljavaSJField) {
            SmalljavaSJField sJField = (SmalljavaSJField) element;
            if (sJField.getId() != null) {
                return sJField.getId();
            }
        }
        if (element instanceof SmalljavaSJMethod) {
            SmalljavaSJMethod sJMethod = (SmalljavaSJMethod) element;
            if (sJMethod.getId() != null) {
                return sJMethod.getId();
            }
        }
        return null;
    }

    public static PsiElement setName(SmalljavaSJSymbol element, String newName) {
        ASTNode elementNode = element.getNode();
        ASTNode oldValueElement = elementNode.findChildByType(SmalljavaTypes.ID);
        if (oldValueElement != null) {
            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
            LeafElement newValueElement = Factory.createSingleLeafElement(SmalljavaTypes.ID, newName, charTableByTree, element.getManager());
            elementNode.replaceChild(oldValueElement, newValueElement);
        }
        return element;
    }

    public static String getName(SmalljavaSJSymbol element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(SmalljavaSJSymbol element) {
        if (element instanceof SmalljavaSJVariableDeclaration) {
            SmalljavaSJVariableDeclaration sJVariableDeclaration = (SmalljavaSJVariableDeclaration) element;
            if (sJVariableDeclaration.getId() != null) {
                return sJVariableDeclaration.getId();
            }
        }
        if (element instanceof SmalljavaSJParameter) {
            SmalljavaSJParameter sJParameter = (SmalljavaSJParameter) element;
            if (sJParameter.getId() != null) {
                return sJParameter.getId();
            }
        }
        return null;
    }
}    
