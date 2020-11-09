// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public class SmalljavaVisitor extends PsiElementVisitor {

    public void visitQualifiedName(@NotNull SmalljavaQualifiedName o) {
        visitPsiElement(o);
    }

    public void visitQualifiedNameWithWildcard(@NotNull SmalljavaQualifiedNameWithWildcard o) {
        visitPsiElement(o);
    }

    public void visitREFERENCESJClassQualifiedName(@NotNull SmalljavaREFERENCESJClassQualifiedName o) {
        visitPsiElement(o);
    }

    public void visitREFERENCESJMemberID(@NotNull SmalljavaREFERENCESJMemberID o) {
        visitPsiElement(o);
    }

    public void visitREFERENCESJSymbolID(@NotNull SmalljavaREFERENCESJSymbolID o) {
        visitPsiElement(o);
    }

    public void visitSJAccessLevel(@NotNull SmalljavaSJAccessLevel o) {
        visitPsiElement(o);
    }

    public void visitSJAssignment(@NotNull SmalljavaSJAssignment o) {
        visitPsiElement(o);
    }

    public void visitSJBlock(@NotNull SmalljavaSJBlock o) {
        visitPsiElement(o);
    }

    public void visitSJClass(@NotNull SmalljavaSJClass o) {
        visitPsiNameIdentifierOwner(o);
    }

    public void visitSJExpression(@NotNull SmalljavaSJExpression o) {
        visitPsiElement(o);
    }

    public void visitSJField(@NotNull SmalljavaSJField o) {
        visitSJMember(o);
    }

    public void visitSJIfBlock(@NotNull SmalljavaSJIfBlock o) {
        visitPsiElement(o);
    }

    public void visitSJIfBlock1(@NotNull SmalljavaSJIfBlock1 o) {
        visitSJIfBlock(o);
    }

    public void visitSJIfStatement(@NotNull SmalljavaSJIfStatement o) {
        visitPsiElement(o);
    }

    public void visitSJImport(@NotNull SmalljavaSJImport o) {
        visitPsiElement(o);
    }

    public void visitSJMember(@NotNull SmalljavaSJMember o) {
        visitPsiNameIdentifierOwner(o);
    }

    public void visitSJMethod(@NotNull SmalljavaSJMethod o) {
        visitSJMember(o);
    }

    public void visitSJMethodBody(@NotNull SmalljavaSJMethodBody o) {
        visitPsiElement(o);
    }

    public void visitSJParameter(@NotNull SmalljavaSJParameter o) {
        visitSJSymbol(o);
    }

    public void visitSJProgram(@NotNull SmalljavaSJProgram o) {
        visitPsiElement(o);
    }

    public void visitSJReturn(@NotNull SmalljavaSJReturn o) {
        visitPsiElement(o);
    }

    public void visitSJSelectionExpression(@NotNull SmalljavaSJSelectionExpression o) {
        visitPsiElement(o);
    }

    public void visitSJStatement(@NotNull SmalljavaSJStatement o) {
        visitPsiElement(o);
    }

    public void visitSJSymbol(@NotNull SmalljavaSJSymbol o) {
        visitPsiNameIdentifierOwner(o);
    }

    public void visitSJTerminalExpression(@NotNull SmalljavaSJTerminalExpression o) {
        visitPsiElement(o);
    }

    public void visitSJVariableDeclaration(@NotNull SmalljavaSJVariableDeclaration o) {
        visitSJSymbol(o);
    }

    public void visitPsiNameIdentifierOwner(@NotNull PsiNameIdentifierOwner o) {
        visitElement(o);
    }

    public void visitPsiElement(@NotNull PsiElement o) {
        visitElement(o);
    }

}
