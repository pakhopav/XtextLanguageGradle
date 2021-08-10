package com.intellij.xtext.generator

import com.intellij.xtext.metamodel.MetaContext
import com.intellij.xtext.metamodel.elements.names.NameGenerator
import com.intellij.xtext.metamodel.elements.tree.TreeCrossReference
import com.intellij.xtext.metamodel.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtext.metamodel.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class ManipulatorsGenerator(extension: String, val context: MetaContext, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateAll() {
        val parserRules = context.rules.filterIsInstance<TreeParserRule>()
        val crossReferences = parserRules.flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }
            .distinctBy { it.getBnfName() }
        crossReferences.forEach {
            generateManipulator(it)
        }
    }

    fun generateManipulator(crossReference: TreeCrossReference) {
        val referenceName = NameGenerator.toGKitClassName(crossReference.getBnfName())
        val file = createOrFindFile("$extensionCapitalized${referenceName}Manipulator.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            |
            |import com.intellij.lang.ASTNode;
            |import com.intellij.openapi.util.TextRange;
            |import com.intellij.psi.AbstractElementManipulator;
            |import com.intellij.psi.impl.source.tree.CompositeElement;
            |import com.intellij.psi.impl.source.tree.Factory;
            |import com.intellij.psi.impl.source.tree.LeafElement;
            |import com.intellij.psi.impl.source.tree.SharedImplUtil;
            |import com.intellij.util.CharTable;
            |import com.intellij.util.IncorrectOperationException;
            |import org.jetbrains.annotations.NotNull;
            |import org.jetbrains.annotations.Nullable;
            |import $packageDir.impl.$extensionCapitalized${referenceName}Impl;
            |
            |public class $extensionCapitalized${referenceName}Manipulator extends AbstractElementManipulator<$extensionCapitalized${referenceName}Impl> {
            |    @Nullable
            |    @Override
            |    public $extensionCapitalized${referenceName}Impl handleContentChange(@NotNull $extensionCapitalized${referenceName}Impl element, @NotNull TextRange range, String newContent) throws IncorrectOperationException {
            |        CompositeElement attrNode = (CompositeElement) element.getNode();
            |        ASTNode valueNode = attrNode.getFirstChildNode();
            |        CharTable charTableByTree = SharedImplUtil.findCharTableByTree(attrNode);
            |        LeafElement newValueElement = Factory.createSingleLeafElement(${extensionCapitalized}Types.ID, newContent, charTableByTree, element.getManager());
            |        attrNode.replaceChildInternal(valueNode, newValueElement);
            |        return element;
            |    }
            |}

        """.trimMargin("|"))
        out.close()
    }
}