// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.impl.*;

public interface XtextTypes {

  IElementType ABSTRACT_METAMODEL_DECLARATION = new XtextElementType("ABSTRACT_METAMODEL_DECLARATION");
  IElementType ABSTRACT_NEGATED_TOKEN = new XtextElementType("ABSTRACT_NEGATED_TOKEN");
    IElementType ABSTRACT_RULE = new XtextElementType("ABSTRACT_RULE");
  IElementType ABSTRACT_TERMINAL = new XtextElementType("ABSTRACT_TERMINAL");
  IElementType ABSTRACT_TOKEN = new XtextElementType("ABSTRACT_TOKEN");
  IElementType ABSTRACT_TOKEN_WITH_CARDINALITY = new XtextElementType("ABSTRACT_TOKEN_WITH_CARDINALITY");
  IElementType ACTION = new XtextElementType("ACTION");
  IElementType ALTERNATIVES = new XtextElementType("ALTERNATIVES");
  IElementType ANNOTATION = new XtextElementType("ANNOTATION");
  IElementType ASSIGNABLE_ALTERNATIVES = new XtextElementType("ASSIGNABLE_ALTERNATIVES");
  IElementType ASSIGNABLE_TERMINAL = new XtextElementType("ASSIGNABLE_TERMINAL");
  IElementType ASSIGNMENT = new XtextElementType("ASSIGNMENT");
  IElementType ATOM = new XtextElementType("ATOM");
    IElementType CARET_EOF = new XtextElementType("CARET_EOF");
  IElementType CHARACTER_RANGE = new XtextElementType("CHARACTER_RANGE");
  IElementType CONDITIONAL_BRANCH = new XtextElementType("CONDITIONAL_BRANCH");
  IElementType CONJUNCTION = new XtextElementType("CONJUNCTION");
  IElementType CROSS_REFERENCE = new XtextElementType("CROSS_REFERENCE");
  IElementType CROSS_REFERENCEABLE_TERMINAL = new XtextElementType("CROSS_REFERENCEABLE_TERMINAL");
  IElementType DISJUNCTION = new XtextElementType("DISJUNCTION");
  IElementType ENUM_LITERALS = new XtextElementType("ENUM_LITERALS");
  IElementType ENUM_LITERAL_DECLARATION = new XtextElementType("ENUM_LITERAL_DECLARATION");
  IElementType ENUM_RULE = new XtextElementType("ENUM_RULE");
  IElementType GENERATED_METAMODEL = new XtextElementType("GENERATED_METAMODEL");
    IElementType GRAMMAR = new XtextElementType("GRAMMAR");
  IElementType GRAMMAR_ID = new XtextElementType("GRAMMAR_ID");
  IElementType GROUP = new XtextElementType("GROUP");
  IElementType KEYWORD = new XtextElementType("KEYWORD");
  IElementType LITERAL_CONDITION = new XtextElementType("LITERAL_CONDITION");
  IElementType NAMED_ARGUMENT = new XtextElementType("NAMED_ARGUMENT");
  IElementType NEGATED_TOKEN = new XtextElementType("NEGATED_TOKEN");
  IElementType NEGATION = new XtextElementType("NEGATION");
  IElementType PARAMETER = new XtextElementType("PARAMETER");
  IElementType PARAMETER_REFERENCE = new XtextElementType("PARAMETER_REFERENCE");
  IElementType PARENTHESIZED_ASSIGNABLE_ELEMENT = new XtextElementType("PARENTHESIZED_ASSIGNABLE_ELEMENT");
  IElementType PARENTHESIZED_CONDITION = new XtextElementType("PARENTHESIZED_CONDITION");
  IElementType PARENTHESIZED_ELEMENT = new XtextElementType("PARENTHESIZED_ELEMENT");
  IElementType PARENTHESIZED_TERMINAL_ELEMENT = new XtextElementType("PARENTHESIZED_TERMINAL_ELEMENT");
  IElementType PARSER_RULE = new XtextElementType("PARSER_RULE");
  IElementType PREDICATED_GROUP = new XtextElementType("PREDICATED_GROUP");
  IElementType PREDICATED_KEYWORD = new XtextElementType("PREDICATED_KEYWORD");
  IElementType PREDICATED_RULE_CALL = new XtextElementType("PREDICATED_RULE_CALL");
  IElementType REFERENCED_METAMODEL = new XtextElementType("REFERENCED_METAMODEL");
  IElementType REFERENCE_ABSTRACT_METAMODEL_DECLARATION = new XtextElementType("REFERENCE_ABSTRACT_METAMODEL_DECLARATION");
  IElementType REFERENCE_ABSTRACT_RULE_RULE_ID = new XtextElementType("REFERENCE_ABSTRACT_RULE_RULE_ID");
  IElementType REFERENCE_ECORE_E_CLASSIFIER = new XtextElementType("REFERENCE_ECORE_E_CLASSIFIER");
  IElementType REFERENCE_ECORE_E_ENUM_LITERAL = new XtextElementType("REFERENCE_ECORE_E_ENUM_LITERAL");
  IElementType REFERENCE_ECORE_E_PACKAGE_STRING = new XtextElementType("REFERENCE_ECORE_E_PACKAGE_STRING");
  IElementType REFERENCE_GRAMMAR_GRAMMAR_ID = new XtextElementType("REFERENCE_GRAMMAR_GRAMMAR_ID");
  IElementType REFERENCE_PARAMETER_ID = new XtextElementType("REFERENCE_PARAMETER_ID");
  IElementType RULE_CALL = new XtextElementType("RULE_CALL");
    IElementType RULE_FROM_CARET_EOF_BRANCH_1 = new XtextElementType("RULE_FROM_CARET_EOF_BRANCH_1");
    IElementType RULE_FROM_CONDITIONAL_BRANCH_BRANCH_2 = new XtextElementType("RULE_FROM_CONDITIONAL_BRANCH_BRANCH_2");
    IElementType RULE_FROM_LITERAL_CONDITION_BRANCH_1 = new XtextElementType("RULE_FROM_LITERAL_CONDITION_BRANCH_1");
    IElementType RULE_FROM_NEGATION_BRANCH_2 = new XtextElementType("RULE_FROM_NEGATION_BRANCH_2");
    IElementType RULE_FROM_WILDCARD_BRANCH_1 = new XtextElementType("RULE_FROM_WILDCARD_BRANCH_1");
  IElementType RULE_ID = new XtextElementType("RULE_ID");
  IElementType RULE_NAME_AND_PARAMS = new XtextElementType("RULE_NAME_AND_PARAMS");
  IElementType TERMINAL_ALTERNATIVES = new XtextElementType("TERMINAL_ALTERNATIVES");
  IElementType TERMINAL_GROUP = new XtextElementType("TERMINAL_GROUP");
  IElementType TERMINAL_RULE = new XtextElementType("TERMINAL_RULE");
  IElementType TERMINAL_RULE_CALL = new XtextElementType("TERMINAL_RULE_CALL");
  IElementType TERMINAL_TOKEN = new XtextElementType("TERMINAL_TOKEN");
  IElementType TERMINAL_TOKEN_ELEMENT = new XtextElementType("TERMINAL_TOKEN_ELEMENT");
  IElementType TYPE_REF = new XtextElementType("TYPE_REF");
  IElementType UNORDERED_GROUP = new XtextElementType("UNORDERED_GROUP");
  IElementType UNTIL_TOKEN = new XtextElementType("UNTIL_TOKEN");
  IElementType VALID_ID = new XtextElementType("VALID_ID");
  IElementType WILDCARD = new XtextElementType("WILDCARD");

    IElementType ACX_MARK_KEYWORD = new XtextTokenType("!");
    IElementType AMPERSAND_KEYWORD = new XtextTokenType("&");
  IElementType ANY_OTHER = new XtextTokenType("ANY_OTHER");
    IElementType ASTERISK_KEYWORD = new XtextTokenType("*");
    IElementType AS_KEYWORD = new XtextTokenType("as");
    IElementType AT_SIGN_KEYWORD = new XtextTokenType("@");
    IElementType COLONS_KEYWORD = new XtextTokenType("::");
    IElementType COLON_KEYWORD = new XtextTokenType(":");
    IElementType COMMA_KEYWORD = new XtextTokenType(",");
    IElementType CURRENT_KEYWORD = new XtextTokenType("current");
    IElementType DOT_KEYWORD = new XtextTokenType(".");
    IElementType ENUM_KEYWORD = new XtextTokenType("enum");
    IElementType EOF_KEY_KEYWORD = new XtextTokenType("EOF");
    IElementType EQUALS_KEYWORD = new XtextTokenType("=");
    IElementType FALSE_KEYWORD = new XtextTokenType("false");
    IElementType FRAGMENT_KEYWORD = new XtextTokenType("fragment");
    IElementType GENERATE_KEYWORD = new XtextTokenType("generate");
    IElementType GRAMMAR_KEYWORD = new XtextTokenType("grammar");
    IElementType HIDDEN_KEYWORD = new XtextTokenType("hidden");
  IElementType ID = new XtextTokenType("ID");
    IElementType IMPORT_KEYWORD = new XtextTokenType("import");
  IElementType INT = new XtextTokenType("INT");
    IElementType L_ANGLE_BRACKET_KEYWORD = new XtextTokenType("<");
    IElementType L_BRACE_KEYWORD = new XtextTokenType("{");
    IElementType L_BRACKET_KEYWORD = new XtextTokenType("(");
    IElementType L_SQUARE_BRACKET_KEYWORD = new XtextTokenType("[");
  IElementType ML_COMMENT = new XtextTokenType("ML_COMMENT");
    IElementType PIPE_KEYWORD = new XtextTokenType("|");
    IElementType PLUS_EQUALS_KEYWORD = new XtextTokenType("+=");
    IElementType PLUS_KEYWORD = new XtextTokenType("+");
    IElementType PRED_KEYWORD = new XtextTokenType("=>");
    IElementType QUES_EQUALS_KEYWORD = new XtextTokenType("?=");
    IElementType QUES_MARK_KEYWORD = new XtextTokenType("?");
    IElementType RANGE_KEYWORD = new XtextTokenType("..");
    IElementType RETURNS_KEYWORD = new XtextTokenType("returns");
    IElementType R_ANGLE_BRACKET_KEYWORD = new XtextTokenType(">");
    IElementType R_BRACE_KEYWORD = new XtextTokenType("}");
    IElementType R_BRACKET_KEYWORD = new XtextTokenType(")");
    IElementType R_SQUARE_BRACKET_KEYWORD = new XtextTokenType("]");
    IElementType SEMICOLON_KEYWORD = new XtextTokenType(";");
  IElementType SL_COMMENT = new XtextTokenType("SL_COMMENT");
  IElementType STRING = new XtextTokenType("STRING");
    IElementType TERMINAL_KEYWORD = new XtextTokenType("terminal");
    IElementType TRUE_KEYWORD = new XtextTokenType("true");
    IElementType WEAK_PRED_KEYWORD = new XtextTokenType("->");
    IElementType WITH_KEYWORD = new XtextTokenType("with");

    class TokenMetaInfo {

        public static boolean isRegexpToken(IElementType token) {

            String debugName = token.toString();
            if (debugName.equals("ID")) {

                return true;

            }
            if (debugName.equals("INT")) {

                return true;

            }
            if (debugName.equals("STRING")) {

                return true;

            }
            if (debugName.equals("ML_COMMENT")) {

                return true;

            }
            if (debugName.equals("SL_COMMENT")) {

                return true;

            }
            if (debugName.equals("ANY_OTHER")) {

                return true;

            }

            return false;

        }

    }

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ABSTRACT_METAMODEL_DECLARATION) {
        return new XtextAbstractMetamodelDeclarationImpl(node);
      }
      else if (type == ABSTRACT_NEGATED_TOKEN) {
        return new XtextAbstractNegatedTokenImpl(node);
      } else if (type == ABSTRACT_RULE) {
          return new XtextAbstractRuleImpl(node);
      } else if (type == ABSTRACT_TERMINAL) {
          return new XtextAbstractTerminalImpl(node);
      } else if (type == ABSTRACT_TOKEN) {
          return new XtextAbstractTokenImpl(node);
      } else if (type == ABSTRACT_TOKEN_WITH_CARDINALITY) {
          return new XtextAbstractTokenWithCardinalityImpl(node);
      } else if (type == ACTION) {
          return new XtextActionImpl(node);
      } else if (type == ALTERNATIVES) {
          return new XtextAlternativesImpl(node);
      } else if (type == ANNOTATION) {
          return new XtextAnnotationImpl(node);
      } else if (type == ASSIGNABLE_ALTERNATIVES) {
          return new XtextAssignableAlternativesImpl(node);
      } else if (type == ASSIGNABLE_TERMINAL) {
          return new XtextAssignableTerminalImpl(node);
      } else if (type == ASSIGNMENT) {
          return new XtextAssignmentImpl(node);
      } else if (type == ATOM) {
          return new XtextAtomImpl(node);
      } else if (type == CARET_EOF) {
          return new XtextCaretEOFImpl(node);
      } else if (type == CHARACTER_RANGE) {
          return new XtextCharacterRangeImpl(node);
      } else if (type == CONDITIONAL_BRANCH) {
          return new XtextConditionalBranchImpl(node);
      } else if (type == CONJUNCTION) {
          return new XtextConjunctionImpl(node);
      } else if (type == CROSS_REFERENCE) {
          return new XtextCrossReferenceImpl(node);
      } else if (type == CROSS_REFERENCEABLE_TERMINAL) {
          return new XtextCrossReferenceableTerminalImpl(node);
      } else if (type == DISJUNCTION) {
          return new XtextDisjunctionImpl(node);
      } else if (type == ENUM_LITERALS) {
          return new XtextEnumLiteralsImpl(node);
      } else if (type == ENUM_LITERAL_DECLARATION) {
          return new XtextEnumLiteralDeclarationImpl(node);
      } else if (type == ENUM_RULE) {
          return new XtextEnumRuleImpl(node);
      } else if (type == GENERATED_METAMODEL) {
          return new XtextGeneratedMetamodelImpl(node);
      } else if (type == GRAMMAR) {
          return new XtextGrammarImpl(node);
      } else if (type == GRAMMAR_ID) {
          return new XtextGrammarIDImpl(node);
      } else if (type == GROUP) {
          return new XtextGroupImpl(node);
      } else if (type == KEYWORD) {
          return new XtextKeywordImpl(node);
      } else if (type == LITERAL_CONDITION) {
          return new XtextLiteralConditionImpl(node);
      } else if (type == NAMED_ARGUMENT) {
          return new XtextNamedArgumentImpl(node);
      } else if (type == NEGATED_TOKEN) {
          return new XtextNegatedTokenImpl(node);
      } else if (type == NEGATION) {
          return new XtextNegationImpl(node);
      } else if (type == PARAMETER) {
          return new XtextParameterImpl(node);
      } else if (type == PARAMETER_REFERENCE) {
          return new XtextParameterReferenceImpl(node);
      } else if (type == PARENTHESIZED_ASSIGNABLE_ELEMENT) {
          return new XtextParenthesizedAssignableElementImpl(node);
      } else if (type == PARENTHESIZED_CONDITION) {
          return new XtextParenthesizedConditionImpl(node);
      } else if (type == PARENTHESIZED_ELEMENT) {
          return new XtextParenthesizedElementImpl(node);
      } else if (type == PARENTHESIZED_TERMINAL_ELEMENT) {
          return new XtextParenthesizedTerminalElementImpl(node);
      } else if (type == PARSER_RULE) {
          return new XtextParserRuleImpl(node);
      } else if (type == PREDICATED_GROUP) {
          return new XtextPredicatedGroupImpl(node);
      } else if (type == PREDICATED_KEYWORD) {
          return new XtextPredicatedKeywordImpl(node);
      } else if (type == PREDICATED_RULE_CALL) {
          return new XtextPredicatedRuleCallImpl(node);
      } else if (type == REFERENCED_METAMODEL) {
          return new XtextReferencedMetamodelImpl(node);
      } else if (type == REFERENCE_ABSTRACT_METAMODEL_DECLARATION) {
          return new XtextREFERENCEAbstractMetamodelDeclarationImpl(node);
      } else if (type == REFERENCE_ABSTRACT_RULE_RULE_ID) {
          return new XtextREFERENCEAbstractRuleRuleIDImpl(node);
      } else if (type == REFERENCE_ECORE_E_CLASSIFIER) {
          return new XtextREFERENCEEcoreEClassifierImpl(node);
      } else if (type == REFERENCE_ECORE_E_ENUM_LITERAL) {
          return new XtextREFERENCEEcoreEEnumLiteralImpl(node);
      } else if (type == REFERENCE_ECORE_E_PACKAGE_STRING) {
          return new XtextREFERENCEEcoreEPackageSTRINGImpl(node);
      } else if (type == REFERENCE_GRAMMAR_GRAMMAR_ID) {
          return new XtextREFERENCEGrammarGrammarIDImpl(node);
      } else if (type == REFERENCE_PARAMETER_ID) {
          return new XtextREFERENCEParameterIDImpl(node);
      } else if (type == RULE_CALL) {
          return new XtextRuleCallImpl(node);
      } else if (type == RULE_FROM_CARET_EOF_BRANCH_1) {
          return new XtextRuleFromCaretEOFBranch1Impl(node);
      } else if (type == RULE_FROM_CONDITIONAL_BRANCH_BRANCH_2) {
          return new XtextRuleFromConditionalBranchBranch2Impl(node);
      } else if (type == RULE_FROM_LITERAL_CONDITION_BRANCH_1) {
          return new XtextRuleFromLiteralConditionBranch1Impl(node);
      } else if (type == RULE_FROM_NEGATION_BRANCH_2) {
          return new XtextRuleFromNegationBranch2Impl(node);
      } else if (type == RULE_FROM_WILDCARD_BRANCH_1) {
          return new XtextRuleFromWildcardBranch1Impl(node);
      } else if (type == RULE_ID) {
          return new XtextRuleIDImpl(node);
      } else if (type == RULE_NAME_AND_PARAMS) {
          return new XtextRuleNameAndParamsImpl(node);
      } else if (type == TERMINAL_ALTERNATIVES) {
          return new XtextTerminalAlternativesImpl(node);
      } else if (type == TERMINAL_GROUP) {
          return new XtextTerminalGroupImpl(node);
      } else if (type == TERMINAL_RULE) {
          return new XtextTerminalRuleImpl(node);
      } else if (type == TERMINAL_RULE_CALL) {
          return new XtextTerminalRuleCallImpl(node);
      } else if (type == TERMINAL_TOKEN) {
          return new XtextTerminalTokenImpl(node);
      } else if (type == TERMINAL_TOKEN_ELEMENT) {
          return new XtextTerminalTokenElementImpl(node);
      } else if (type == TYPE_REF) {
          return new XtextTypeRefImpl(node);
      } else if (type == UNORDERED_GROUP) {
          return new XtextUnorderedGroupImpl(node);
      } else if (type == UNTIL_TOKEN) {
          return new XtextUntilTokenImpl(node);
      } else if (type == VALID_ID) {
          return new XtextValidIDImpl(node);
      } else if (type == WILDCARD) {
          return new XtextWildcardImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
