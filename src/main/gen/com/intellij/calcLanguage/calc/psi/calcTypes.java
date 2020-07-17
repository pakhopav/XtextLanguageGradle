// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.impl.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public interface calcTypes {

  IElementType ABSTRACT_DEFINITION = new calcElementType("ABSTRACT_DEFINITION");
  IElementType ADDITION = new calcElementType("ADDITION");
  IElementType DECLARED_PARAMETER = new calcElementType("DECLARED_PARAMETER");
  IElementType DEFINITION = new calcElementType("DEFINITION");
  IElementType EVALUATION = new calcElementType("EVALUATION");
  IElementType EXPRESSION = new calcElementType("EXPRESSION");
  IElementType IMPORT = new calcElementType("IMPORT");
  IElementType MODULE = new calcElementType("MODULE");
  IElementType MULTIPLICATION = new calcElementType("MULTIPLICATION");
  IElementType PRIMARY_EXPRESSION = new calcElementType("PRIMARY_EXPRESSION");
  IElementType PRIMARY_EXPRESSION_1 = new calcElementType("PRIMARY_EXPRESSION_1");
  IElementType PRIMARY_EXPRESSION_2 = new calcElementType("PRIMARY_EXPRESSION_2");
  IElementType PRIMARY_EXPRESSION_3 = new calcElementType("PRIMARY_EXPRESSION_3");
  IElementType REFERENCE_ABSTRACT_DEFINITION_ID = new calcElementType("REFERENCE_ABSTRACT_DEFINITION_ID");
  IElementType REFERENCE_MODULE_ID = new calcElementType("REFERENCE_MODULE_ID");
  IElementType STATEMENT = new calcElementType("STATEMENT");

  IElementType ANY_OTHER = new calcTokenType("ANY_OTHER");
  IElementType ASTERISK_KEYWORD = new calcTokenType("*");
  IElementType COLON_KEYWORD = new calcTokenType(":");
  IElementType COMMA_KEYWORD = new calcTokenType(",");
  IElementType DEF_KEYWORD = new calcTokenType("def");
  IElementType ID = new calcTokenType("ID");
  IElementType IMPORT_KEYWORD = new calcTokenType("import");
  IElementType INT = new calcTokenType("INT");
  IElementType KEYWORD_0 = new calcTokenType("-");
  IElementType KEYWORD_1 = new calcTokenType("/");
  IElementType L_BRACKET_KEYWORD = new calcTokenType("(");
  IElementType ML_COMMENT = new calcTokenType("ML_COMMENT");
  IElementType MODULE_KEYWORD = new calcTokenType("module");
  IElementType NUMBER = new calcTokenType("NUMBER");
  IElementType PLUS_KEYWORD = new calcTokenType("+");
  IElementType R_BRACKET_KEYWORD = new calcTokenType(")");
  IElementType SEMICOLON_KEYWORD = new calcTokenType(";");
  IElementType SL_COMMENT = new calcTokenType("SL_COMMENT");
  IElementType STRING = new calcTokenType("STRING");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ABSTRACT_DEFINITION) {
        return new calcAbstractDefinitionImpl(node);
      } else if (type == ADDITION) {
        return new calcAdditionImpl(node);
      } else if (type == DECLARED_PARAMETER) {
        return new calcDeclaredParameterImpl(node);
      } else if (type == DEFINITION) {
        return new calcDefinitionImpl(node);
      } else if (type == EVALUATION) {
        return new calcEvaluationImpl(node);
      } else if (type == EXPRESSION) {
        return new calcExpressionImpl(node);
      } else if (type == IMPORT) {
        return new calcImportImpl(node);
      } else if (type == MODULE) {
        return new calcModuleImpl(node);
      } else if (type == MULTIPLICATION) {
        return new calcMultiplicationImpl(node);
      } else if (type == PRIMARY_EXPRESSION) {
        return new calcPrimaryExpressionImpl(node);
      } else if (type == PRIMARY_EXPRESSION_1) {
        return new calcPrimaryExpression1Impl(node);
      } else if (type == PRIMARY_EXPRESSION_2) {
        return new calcPrimaryExpression2Impl(node);
      } else if (type == PRIMARY_EXPRESSION_3) {
        return new calcPrimaryExpression3Impl(node);
      } else if (type == REFERENCE_ABSTRACT_DEFINITION_ID) {
        return new calcREFERENCEAbstractDefinitionIDImpl(node);
      } else if (type == REFERENCE_MODULE_ID) {
        return new calcREFERENCEModuleIDImpl(node);
      } else if (type == STATEMENT) {
        return new calcStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
