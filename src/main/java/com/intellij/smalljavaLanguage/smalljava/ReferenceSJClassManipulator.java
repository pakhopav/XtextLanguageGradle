package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.AbstractElementManipulator;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.Factory;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.smalljavaLanguage.smalljava.impl.SmalljavaREFERENCESJClassQualifiedNameImpl;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes;
import com.intellij.util.CharTable;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReferenceSJClassManipulator extends AbstractElementManipulator<SmalljavaREFERENCESJClassQualifiedNameImpl> {
    @Nullable
    @Override
    public SmalljavaREFERENCESJClassQualifiedNameImpl handleContentChange(@NotNull SmalljavaREFERENCESJClassQualifiedNameImpl element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
        final CompositeElement attrNode = (CompositeElement) element.getNode();
        final ASTNode valueNode = attrNode.getFirstChildNode();
        final CharTable charTableByTree = SharedImplUtil.findCharTableByTree(attrNode);
        final LeafElement newValueElement = Factory.createSingleLeafElement(SmalljavaTypes.ID, newContent, charTableByTree, element.getManager());
        attrNode.replaceChildInternal(valueNode, newValueElement);
        return element;
    }
}
