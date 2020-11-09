// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.smalljavaLanguage.smalljava.impl.*;

public interface SmalljavaTypes {

    IElementType QUALIFIED_NAME = new SmalljavaElementType("QUALIFIED_NAME");
    IElementType QUALIFIED_NAME_WITH_WILDCARD = new SmalljavaElementType("QUALIFIED_NAME_WITH_WILDCARD");
    IElementType REFERENCE_SJ_CLASS_QUALIFIED_NAME = new SmalljavaElementType("REFERENCE_SJ_CLASS_QUALIFIED_NAME");
    IElementType REFERENCE_SJ_MEMBER_ID = new SmalljavaElementType("REFERENCE_SJ_MEMBER_ID");
    IElementType REFERENCE_SJ_SYMBOL_ID = new SmalljavaElementType("REFERENCE_SJ_SYMBOL_ID");
    IElementType SJ_ACCESS_LEVEL = new SmalljavaElementType("SJ_ACCESS_LEVEL");
    IElementType SJ_ASSIGNMENT = new SmalljavaElementType("SJ_ASSIGNMENT");
    IElementType SJ_BLOCK = new SmalljavaElementType("SJ_BLOCK");
    IElementType SJ_CLASS = new SmalljavaElementType("SJ_CLASS");
    IElementType SJ_EXPRESSION = new SmalljavaElementType("SJ_EXPRESSION");
    IElementType SJ_FIELD = new SmalljavaElementType("SJ_FIELD");
    IElementType SJ_IF_BLOCK = new SmalljavaElementType("SJ_IF_BLOCK");
    IElementType SJ_IF_BLOCK_1 = new SmalljavaElementType("SJ_IF_BLOCK_1");
    IElementType SJ_IF_STATEMENT = new SmalljavaElementType("SJ_IF_STATEMENT");
    IElementType SJ_IMPORT = new SmalljavaElementType("SJ_IMPORT");
    IElementType SJ_MEMBER = new SmalljavaElementType("SJ_MEMBER");
    IElementType SJ_METHOD = new SmalljavaElementType("SJ_METHOD");
    IElementType SJ_METHOD_BODY = new SmalljavaElementType("SJ_METHOD_BODY");
    IElementType SJ_PARAMETER = new SmalljavaElementType("SJ_PARAMETER");
    IElementType SJ_PROGRAM = new SmalljavaElementType("SJ_PROGRAM");
    IElementType SJ_RETURN = new SmalljavaElementType("SJ_RETURN");
    IElementType SJ_SELECTION_EXPRESSION = new SmalljavaElementType("SJ_SELECTION_EXPRESSION");
    IElementType SJ_STATEMENT = new SmalljavaElementType("SJ_STATEMENT");
    IElementType SJ_SYMBOL = new SmalljavaElementType("SJ_SYMBOL");
    IElementType SJ_TERMINAL_EXPRESSION = new SmalljavaElementType("SJ_TERMINAL_EXPRESSION");
    IElementType SJ_VARIABLE_DECLARATION = new SmalljavaElementType("SJ_VARIABLE_DECLARATION");

    IElementType ANY_OTHER = new SmalljavaTokenType("ANY_OTHER");
    IElementType CLASS_KEYWORD = new SmalljavaTokenType("class");
    IElementType COMMA_KEYWORD = new SmalljavaTokenType(",");
    IElementType DOT_KEYWORD = new SmalljavaTokenType(".");
    IElementType ELSE_KEYWORD = new SmalljavaTokenType("else");
    IElementType EQUALS_KEYWORD = new SmalljavaTokenType("=");
    IElementType EXTENDS_KEYWORD = new SmalljavaTokenType("extends");
    IElementType FALSE_KEYWORD = new SmalljavaTokenType("false");
    IElementType ID = new SmalljavaTokenType("ID");
    IElementType IF_KEYWORD = new SmalljavaTokenType("if");
    IElementType IMPORT_KEYWORD = new SmalljavaTokenType("import");
    IElementType INT = new SmalljavaTokenType("INT");
    IElementType KEYWORD_1 = new SmalljavaTokenType(".*");
    IElementType L_BRACE_KEYWORD = new SmalljavaTokenType("{");
    IElementType L_BRACKET_KEYWORD = new SmalljavaTokenType("(");
    IElementType ML_COMMENT = new SmalljavaTokenType("ML_COMMENT");
    IElementType NEW_KEYWORD = new SmalljavaTokenType("new");
    IElementType NULL_KEYWORD = new SmalljavaTokenType("null");
    IElementType PACKAGE_KEYWORD = new SmalljavaTokenType("package");
    IElementType PRIVATE_KEYWORD = new SmalljavaTokenType("private");
    IElementType PROTECTED_KEYWORD = new SmalljavaTokenType("protected");
    IElementType PUBLIC_KEYWORD = new SmalljavaTokenType("public");
    IElementType RETURN_KEYWORD = new SmalljavaTokenType("return");
    IElementType R_BRACE_KEYWORD = new SmalljavaTokenType("}");
    IElementType R_BRACKET_KEYWORD = new SmalljavaTokenType(")");
    IElementType SEMICOLON_KEYWORD = new SmalljavaTokenType(";");
    IElementType SL_COMMENT = new SmalljavaTokenType("SL_COMMENT");
    IElementType STRING = new SmalljavaTokenType("STRING");
    IElementType SUPER_KEYWORD = new SmalljavaTokenType("super");
    IElementType THIS_KEYWORD = new SmalljavaTokenType("this");
    IElementType TRUE_KEYWORD = new SmalljavaTokenType("true");
    IElementType WS = new SmalljavaTokenType("WS");

    class Factory {
        public static PsiElement createElement(ASTNode node) {
            IElementType type = node.getElementType();
            if (type == QUALIFIED_NAME) {
                return new SmalljavaQualifiedNameImpl(node);
            } else if (type == QUALIFIED_NAME_WITH_WILDCARD) {
                return new SmalljavaQualifiedNameWithWildcardImpl(node);
            } else if (type == REFERENCE_SJ_CLASS_QUALIFIED_NAME) {
                return new SmalljavaREFERENCESJClassQualifiedNameImpl(node);
            } else if (type == REFERENCE_SJ_MEMBER_ID) {
                return new SmalljavaREFERENCESJMemberIDImpl(node);
            } else if (type == REFERENCE_SJ_SYMBOL_ID) {
                return new SmalljavaREFERENCESJSymbolIDImpl(node);
            } else if (type == SJ_ACCESS_LEVEL) {
                return new SmalljavaSJAccessLevelImpl(node);
            } else if (type == SJ_ASSIGNMENT) {
                return new SmalljavaSJAssignmentImpl(node);
            } else if (type == SJ_BLOCK) {
                return new SmalljavaSJBlockImpl(node);
            } else if (type == SJ_CLASS) {
                return new SmalljavaSJClassImpl(node);
            } else if (type == SJ_EXPRESSION) {
                return new SmalljavaSJExpressionImpl(node);
            } else if (type == SJ_FIELD) {
                return new SmalljavaSJFieldImpl(node);
            } else if (type == SJ_IF_BLOCK) {
                return new SmalljavaSJIfBlockImpl(node);
            } else if (type == SJ_IF_BLOCK_1) {
                return new SmalljavaSJIfBlock1Impl(node);
            } else if (type == SJ_IF_STATEMENT) {
                return new SmalljavaSJIfStatementImpl(node);
            } else if (type == SJ_IMPORT) {
                return new SmalljavaSJImportImpl(node);
            } else if (type == SJ_METHOD) {
                return new SmalljavaSJMethodImpl(node);
            } else if (type == SJ_METHOD_BODY) {
                return new SmalljavaSJMethodBodyImpl(node);
            } else if (type == SJ_PARAMETER) {
                return new SmalljavaSJParameterImpl(node);
            } else if (type == SJ_PROGRAM) {
                return new SmalljavaSJProgramImpl(node);
            } else if (type == SJ_RETURN) {
                return new SmalljavaSJReturnImpl(node);
            } else if (type == SJ_SELECTION_EXPRESSION) {
                return new SmalljavaSJSelectionExpressionImpl(node);
            } else if (type == SJ_STATEMENT) {
                return new SmalljavaSJStatementImpl(node);
            } else if (type == SJ_TERMINAL_EXPRESSION) {
                return new SmalljavaSJTerminalExpressionImpl(node);
            } else if (type == SJ_VARIABLE_DECLARATION) {
                return new SmalljavaSJVariableDeclarationImpl(node);
            }
            throw new AssertionError("Unknown element type: " + type);
        }
    }
}
