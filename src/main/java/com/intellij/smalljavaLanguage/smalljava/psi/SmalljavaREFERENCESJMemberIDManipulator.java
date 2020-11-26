package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.smalljavaLanguage.smalljava.impl.SmalljavaREFERENCESJMemberIDImpl;
import com.intellij.util.CharTable;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmalljavaREFERENCESJMemberIDManipulator extends AbstractElementManipulator<SmalljavaREFERENCESJMemberIDImpl> {
    @Nullable
    @Override
    public SmalljavaREFERENCESJMemberIDImpl handleContentChange(@NotNull SmalljavaREFERENCESJMemberIDImpl element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        CompositeElement attrNode = (CompositeElement) element.getNode();
        ASTNode valueNode = attrNode.getFirstChildNode();
        CharTable charTableByTree = SharedImplUtil.findCharTableByTree(attrNode);
        LeafElement newValueElement = Factory.createSingleLeafElement(SmalljavaTypes.ID, newContent, charTableByTree, element.getManager());
        attrNode.replaceChildInternal(valueNode, newValueElement);
        return element;
    }
}
