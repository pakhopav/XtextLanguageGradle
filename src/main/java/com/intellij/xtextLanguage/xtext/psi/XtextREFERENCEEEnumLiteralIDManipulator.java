package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.util.CharTable;
import com.intellij.util.IncorrectOperationException;
import com.intellij.xtextLanguage.xtext.impl.XtextREFERENCEEEnumLiteralIDImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextREFERENCEEEnumLiteralIDManipulator extends AbstractElementManipulator<XtextREFERENCEEEnumLiteralIDImpl> {
    @Nullable
    @Override
    public XtextREFERENCEEEnumLiteralIDImpl handleContentChange(@NotNull XtextREFERENCEEEnumLiteralIDImpl element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        CompositeElement attrNode = (CompositeElement) element.getNode();
        ASTNode valueNode = attrNode.getFirstChildNode();
        CharTable charTableByTree = SharedImplUtil.findCharTableByTree(attrNode);
        LeafElement newValueElement = Factory.createSingleLeafElement(XtextTypes.ID, newContent, charTableByTree, element.getManager());
        attrNode.replaceChildInternal(valueNode, newValueElement);
        return element;
    }
}
