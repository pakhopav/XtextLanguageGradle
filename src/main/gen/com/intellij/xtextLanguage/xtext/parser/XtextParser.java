// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy.*;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class XtextParser implements PsiParser, LightPsiParser {

    public ASTNode parse(IElementType t, PsiBuilder b) {
        parseLight(t, b);
        return b.getTreeBuilt();
    }

    public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[]{
            create_token_set_(KEYWORD, KEYWORD_1),
            create_token_set_(CONJUNCTION, CONJUNCTION_1),
            create_token_set_(NEGATION, NEGATION_1),
            create_token_set_(ABSTRACT_METAMODEL_DECLARATION, GENERATED_METAMODEL, REFERENCED_METAMODEL),
            create_token_set_(ABSTRACT_RULE, ENUM_RULE, PARSER_RULE, TERMINAL_RULE),
    };

    protected boolean parse_root_(IElementType t, PsiBuilder b) {
        return parse_root_(t, b, 0);
    }

    static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
        boolean r;
        if (t == ABSTRACT_METAMODEL_DECLARATION) {
            r = AbstractMetamodelDeclaration(b, l + 1);
        } else if (t == ABSTRACT_NEGATED_TOKEN) {
            r = AbstractNegatedToken(b, l + 1);
        } else if (t == ABSTRACT_RULE) {
            r = AbstractRule(b, l + 1);
        } else if (t == ABSTRACT_TERMINAL) {
            r = AbstractTerminal(b, l + 1);
        } else if (t == ABSTRACT_TOKEN) {
            r = AbstractToken(b, l + 1);
        } else if (t == ABSTRACT_TOKEN_WITH_CARDINALITY) {
            r = AbstractTokenWithCardinality(b, l + 1);
        } else if (t == ACTION) {
            r = Action(b, l + 1);
        } else if (t == ALTERNATIVES) {
            r = Alternatives(b, l + 1);
        } else if (t == ALTERNATIVES_SUFFIX_1) {
            r = AlternativesSuffix1(b, l + 1);
        } else if (t == ANNOTATION) {
            r = Annotation(b, l + 1);
        } else if (t == ASSIGNABLE_ALTERNATIVES) {
            r = AssignableAlternatives(b, l + 1);
        } else if (t == ASSIGNABLE_ALTERNATIVES_SUFFIX_1) {
            r = AssignableAlternativesSuffix1(b, l + 1);
        } else if (t == ASSIGNABLE_TERMINAL) {
            r = AssignableTerminal(b, l + 1);
        } else if (t == ASSIGNMENT) {
            r = Assignment(b, l + 1);
        } else if (t == ATOM) {
            r = Atom(b, l + 1);
        } else if (t == CHARACTER_RANGE) {
            r = CharacterRange(b, l + 1);
        } else if (t == CONDITIONAL_BRANCH) {
            r = ConditionalBranch(b, l + 1);
        } else if (t == CONJUNCTION) {
            r = Conjunction(b, l + 1);
        } else if (t == CONJUNCTION_1) {
            r = Conjunction1(b, l + 1);
        } else if (t == CROSS_REFERENCE) {
            r = CrossReference(b, l + 1);
        } else if (t == CROSS_REFERENCEABLE_TERMINAL) {
            r = CrossReferenceableTerminal(b, l + 1);
        } else if (t == DISJUNCTION) {
            r = Disjunction(b, l + 1);
        } else if (t == EOF) {
            r = EOF(b, l + 1);
        } else if (t == ENUM_LITERAL_DECLARATION) {
            r = EnumLiteralDeclaration(b, l + 1);
        } else if (t == ENUM_LITERALS) {
            r = EnumLiterals(b, l + 1);
        } else if (t == ENUM_LITERALS_SUFFIX_1) {
            r = EnumLiteralsSuffix1(b, l + 1);
        } else if (t == ENUM_RULE) {
            r = EnumRule(b, l + 1);
        } else if (t == GENERATED_METAMODEL) {
            r = GeneratedMetamodel(b, l + 1);
        } else if (t == GRAMMAR) {
            r = Grammar(b, l + 1);
        } else if (t == GRAMMAR_ID) {
            r = GrammarID(b, l + 1);
        } else if (t == GROUP) {
            r = Group(b, l + 1);
        } else if (t == GROUP_SUFFIX_1) {
            r = GroupSuffix1(b, l + 1);
        } else if (t == KEYWORD) {
            r = Keyword(b, l + 1);
        } else if (t == KEYWORD_1) {
            r = Keyword1(b, l + 1);
        } else if (t == LITERAL_CONDITION) {
            r = LiteralCondition(b, l + 1);
        } else if (t == NAMED_ARGUMENT) {
            r = NamedArgument(b, l + 1);
        } else if (t == NEGATED_TOKEN) {
            r = NegatedToken(b, l + 1);
        } else if (t == NEGATION) {
            r = Negation(b, l + 1);
        } else if (t == NEGATION_1) {
            r = Negation1(b, l + 1);
        } else if (t == PARAMETER) {
            r = Parameter(b, l + 1);
        } else if (t == PARAMETER_REFERENCE) {
            r = ParameterReference(b, l + 1);
        } else if (t == PARENTHESIZED_ASSIGNABLE_ELEMENT) {
            r = ParenthesizedAssignableElement(b, l + 1);
        } else if (t == PARENTHESIZED_CONDITION) {
            r = ParenthesizedCondition(b, l + 1);
        } else if (t == PARENTHESIZED_ELEMENT) {
            r = ParenthesizedElement(b, l + 1);
        } else if (t == PARENTHESIZED_TERMINAL_ELEMENT) {
            r = ParenthesizedTerminalElement(b, l + 1);
        } else if (t == PARSER_RULE) {
            r = ParserRule(b, l + 1);
        } else if (t == PREDICATED_GROUP) {
            r = PredicatedGroup(b, l + 1);
        } else if (t == PREDICATED_KEYWORD) {
            r = PredicatedKeyword(b, l + 1);
        } else if (t == PREDICATED_RULE_CALL) {
            r = PredicatedRuleCall(b, l + 1);
        } else if (t == REFERENCE_ABSTRACT_METAMODEL_DECLARATION_ID) {
            r = REFERENCE_AbstractMetamodelDeclaration_ID(b, l + 1);
        } else if (t == REFERENCE_ABSTRACT_RULE_RULE_ID) {
            r = REFERENCE_AbstractRule_RuleID(b, l + 1);
        } else if (t == REFERENCE_E_CLASSIFIER_ID) {
            r = REFERENCE_EClassifier_ID(b, l + 1);
        } else if (t == REFERENCE_E_ENUM_LITERAL_ID) {
            r = REFERENCE_EEnumLiteral_ID(b, l + 1);
        } else if (t == REFERENCE_E_PACKAGE_STRING) {
            r = REFERENCE_EPackage_STRING(b, l + 1);
        } else if (t == REFERENCE_GRAMMAR_GRAMMAR_ID) {
            r = REFERENCE_Grammar_GrammarID(b, l + 1);
        } else if (t == REFERENCE_PARAMETER_ID) {
            r = REFERENCE_Parameter_ID(b, l + 1);
        } else if (t == REFERENCED_METAMODEL) {
            r = ReferencedMetamodel(b, l + 1);
        } else if (t == RULE_CALL) {
            r = RuleCall(b, l + 1);
        } else if (t == RULE_ID) {
            r = RuleID(b, l + 1);
        } else if (t == TERMINAL_ALTERNATIVES) {
            r = TerminalAlternatives(b, l + 1);
        } else if (t == TERMINAL_ALTERNATIVES_SUFFIX_1) {
            r = TerminalAlternativesSuffix1(b, l + 1);
        } else if (t == TERMINAL_GROUP) {
            r = TerminalGroup(b, l + 1);
        } else if (t == TERMINAL_GROUP_SUFFIX_1) {
            r = TerminalGroupSuffix1(b, l + 1);
        } else if (t == TERMINAL_RULE) {
            r = TerminalRule(b, l + 1);
        } else if (t == TERMINAL_RULE_CALL) {
            r = TerminalRuleCall(b, l + 1);
        } else if (t == TERMINAL_TOKEN) {
            r = TerminalToken(b, l + 1);
        } else if (t == TERMINAL_TOKEN_ELEMENT) {
            r = TerminalTokenElement(b, l + 1);
        } else if (t == TYPE_REF) {
            r = TypeRef(b, l + 1);
        } else if (t == UNORDERED_GROUP) {
            r = UnorderedGroup(b, l + 1);
        } else if (t == UNORDERED_GROUP_SUFFIX_1) {
            r = UnorderedGroupSuffix1(b, l + 1);
        } else if (t == UNTIL_TOKEN) {
            r = UntilToken(b, l + 1);
        } else if (t == VALID_ID) {
            r = ValidID(b, l + 1);
        } else if (t == VALID_ID_1) {
            r = ValidID1(b, l + 1);
        } else if (t == WILDCARD) {
            r = Wildcard(b, l + 1);
        } else {
            r = XtextFile(b, l + 1);
        }
        return r;
    }

  /* ********************************************************** */
  // GeneratedMetamodel | ReferencedMetamodel
  public static boolean AbstractMetamodelDeclaration(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "AbstractMetamodelDeclaration")) return false;
      if (!nextTokenIs(b, "<abstract metamodel declaration>", GENERATE_KEYWORD, IMPORT_KEYWORD)) return false;
      boolean r;
      Marker m = enter_section_(b, l, _COLLAPSE_, ABSTRACT_METAMODEL_DECLARATION, "<abstract metamodel declaration>");
    r = GeneratedMetamodel(b, l + 1);
    if (!r) r = ReferencedMetamodel(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // NegatedToken | UntilToken
  public static boolean AbstractNegatedToken(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "AbstractNegatedToken")) return false;
      if (!nextTokenIs(b, "<abstract negated token>", ACX_MARK_KEYWORD, WEAK_PRED_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABSTRACT_NEGATED_TOKEN, "<abstract negated token>");
    r = NegatedToken(b, l + 1);
    if (!r) r = UntilToken(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ParserRule | TerminalRule | EnumRule
  public static boolean AbstractRule(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "AbstractRule")) return false;
      boolean r;
      Marker m = enter_section_(b, l, _COLLAPSE_, ABSTRACT_RULE, "<abstract rule>");
      r = ParserRule(b, l + 1);
      if (!r) r = TerminalRule(b, l + 1);
      if (!r) r = EnumRule(b, l + 1);
      exit_section_(b, l, m, r, false, null);
      return r;
  }

  /* ********************************************************** */
  // Keyword | RuleCall | ParenthesizedElement | PredicatedKeyword | PredicatedRuleCall | PredicatedGroup
  public static boolean AbstractTerminal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractTerminal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABSTRACT_TERMINAL, "<abstract terminal>");
    r = Keyword(b, l + 1);
    if (!r) r = RuleCall(b, l + 1);
    if (!r) r = ParenthesizedElement(b, l + 1);
    if (!r) r = PredicatedKeyword(b, l + 1);
    if (!r) r = PredicatedRuleCall(b, l + 1);
    if (!r) r = PredicatedGroup(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // AbstractTokenWithCardinality | Action
  public static boolean AbstractToken(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractToken")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABSTRACT_TOKEN, "<abstract token>");
    r = AbstractTokenWithCardinality(b, l + 1);
    if (!r) r = Action(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (Assignment | AbstractTerminal) ('?' | '*' | '+')?
  public static boolean AbstractTokenWithCardinality(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractTokenWithCardinality")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABSTRACT_TOKEN_WITH_CARDINALITY, "<abstract token with cardinality>");
    r = AbstractTokenWithCardinality_0(b, l + 1);
    r = r && AbstractTokenWithCardinality_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // Assignment | AbstractTerminal
  private static boolean AbstractTokenWithCardinality_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractTokenWithCardinality_0")) return false;
    boolean r;
    r = Assignment(b, l + 1);
    if (!r) r = AbstractTerminal(b, l + 1);
    return r;
  }

    // ('?' | '*' | '+')?
  private static boolean AbstractTokenWithCardinality_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractTokenWithCardinality_1")) return false;
    AbstractTokenWithCardinality_1_0(b, l + 1);
    return true;
  }

    // '?' | '*' | '+'
  private static boolean AbstractTokenWithCardinality_1_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "AbstractTokenWithCardinality_1_0")) return false;
      boolean r;
      r = consumeToken(b, QUES_MARK_KEYWORD);
      if (!r) r = consumeToken(b, ASTERISK_KEYWORD);
      if (!r) r = consumeToken(b, PLUS_KEYWORD);
      return r;
  }

  /* ********************************************************** */
  // '{' TypeRef ('.' ValidID ('=' | '+=') 'current')? '}'
  public static boolean Action(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Action")) return false;
      if (!nextTokenIs(b, L_BRACE_KEYWORD)) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, L_BRACE_KEYWORD);
      r = r && TypeRef(b, l + 1);
      r = r && Action_2(b, l + 1);
      r = r && consumeToken(b, R_BRACE_KEYWORD);
      exit_section_(b, m, ACTION, r);
      return r;
  }

    // ('.' ValidID ('=' | '+=') 'current')?
    private static boolean Action_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Action_2")) return false;
        Action_2_0(b, l + 1);
        return true;
    }

    // '.' ValidID ('=' | '+=') 'current'
    private static boolean Action_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Action_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, DOT_KEYWORD);
        r = r && ValidID(b, l + 1);
        r = r && Action_2_0_2(b, l + 1);
        r = r && consumeToken(b, CURRENT_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // '=' | '+='
    private static boolean Action_2_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Action_2_0_2")) return false;
        boolean r;
        r = consumeToken(b, EQUALS_KEYWORD);
        if (!r) r = consumeToken(b, PLUS_EQUALS_KEYWORD);
        return r;
    }

  /* ********************************************************** */
  // ConditionalBranch AlternativesSuffix1?
  public static boolean Alternatives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Alternatives")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ALTERNATIVES, "<alternatives>");
    r = ConditionalBranch(b, l + 1);
    r = r && Alternatives_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // AlternativesSuffix1?
    private static boolean Alternatives_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Alternatives_1")) return false;
        AlternativesSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('|' ConditionalBranch)+
    public static boolean AlternativesSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AlternativesSuffix1")) return false;
        if (!nextTokenIs(b, PIPE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AlternativesSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!AlternativesSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "AlternativesSuffix1", c)) break;
        }
        exit_section_(b, m, ALTERNATIVES_SUFFIX_1, r);
        return r;
  }

    // '|' ConditionalBranch
    private static boolean AlternativesSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AlternativesSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, PIPE_KEYWORD);
        r = r && ConditionalBranch(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '@' ID
    public static boolean Annotation(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Annotation")) return false;
        if (!nextTokenIs(b, AT_SIGN_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, AT_SIGN_KEYWORD, ID);
        exit_section_(b, m, ANNOTATION, r);
        return r;
    }

    /* ********************************************************** */
    // AssignableTerminal AssignableAlternativesSuffix1?
    public static boolean AssignableAlternatives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignableAlternatives")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ASSIGNABLE_ALTERNATIVES, "<assignable alternatives>");
    r = AssignableTerminal(b, l + 1);
    r = r && AssignableAlternatives_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
        return r;
    }

    // AssignableAlternativesSuffix1?
    private static boolean AssignableAlternatives_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignableAlternatives_1")) return false;
        AssignableAlternativesSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('|' AssignableTerminal)+
    public static boolean AssignableAlternativesSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignableAlternativesSuffix1")) return false;
        if (!nextTokenIs(b, PIPE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AssignableAlternativesSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!AssignableAlternativesSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "AssignableAlternativesSuffix1", c)) break;
        }
        exit_section_(b, m, ASSIGNABLE_ALTERNATIVES_SUFFIX_1, r);
        return r;
  }

    // '|' AssignableTerminal
    private static boolean AssignableAlternativesSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "AssignableAlternativesSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, PIPE_KEYWORD);
        r = r && AssignableTerminal(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

  /* ********************************************************** */
  // Keyword | RuleCall | ParenthesizedAssignableElement | CrossReference
  public static boolean AssignableTerminal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AssignableTerminal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ASSIGNABLE_TERMINAL, "<assignable terminal>");
    r = Keyword(b, l + 1);
    if (!r) r = RuleCall(b, l + 1);
    if (!r) r = ParenthesizedAssignableElement(b, l + 1);
    if (!r) r = CrossReference(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ('=>' | '->')? ValidID ('+=' | '=' | '?=') AssignableTerminal
  public static boolean Assignment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Assignment")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ASSIGNMENT, "<assignment>");
    r = Assignment_0(b, l + 1);
    r = r && ValidID(b, l + 1);
    r = r && Assignment_2(b, l + 1);
    r = r && AssignableTerminal(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // ('=>' | '->')?
  private static boolean Assignment_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Assignment_0")) return false;
    Assignment_0_0(b, l + 1);
    return true;
  }

    // '=>' | '->'
  private static boolean Assignment_0_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Assignment_0_0")) return false;
      boolean r;
      r = consumeToken(b, PRED_KEYWORD);
      if (!r) r = consumeToken(b, WEAK_PRED_KEYWORD);
      return r;
  }

    // '+=' | '=' | '?='
  private static boolean Assignment_2(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Assignment_2")) return false;
      boolean r;
      r = consumeToken(b, PLUS_EQUALS_KEYWORD);
      if (!r) r = consumeToken(b, EQUALS_KEYWORD);
      if (!r) r = consumeToken(b, QUES_EQUALS_KEYWORD);
      return r;
  }

  /* ********************************************************** */
  // ParameterReference | ParenthesizedCondition | LiteralCondition
  public static boolean Atom(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Atom")) return false;
      boolean r;
      Marker m = enter_section_(b, l, _NONE_, ATOM, "<atom>");
      r = ParameterReference(b, l + 1);
      if (!r) r = ParenthesizedCondition(b, l + 1);
      if (!r) r = LiteralCondition(b, l + 1);
      exit_section_(b, l, m, r, false, null);
      return r;
  }

    /* ********************************************************** */
    // Keyword ('..' Keyword1)?
    public static boolean CharacterRange(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "CharacterRange")) return false;
        if (!nextTokenIs(b, STRING)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Keyword(b, l + 1);
        r = r && CharacterRange_1(b, l + 1);
        exit_section_(b, m, CHARACTER_RANGE, r);
        return r;
    }

    // ('..' Keyword1)?
  private static boolean CharacterRange_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CharacterRange_1")) return false;
    CharacterRange_1_0(b, l + 1);
    return true;
  }

    // '..' Keyword1
  private static boolean CharacterRange_1_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "CharacterRange_1_0")) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, RANGE_KEYWORD);
      r = r && Keyword1(b, l + 1);
      exit_section_(b, m, null, r);
      return r;
  }

  /* ********************************************************** */
  // UnorderedGroup | '<' Disjunction '>' (AbstractToken)+
  public static boolean ConditionalBranch(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ConditionalBranch")) return false;
      boolean r;
      Marker m = enter_section_(b, l, _NONE_, CONDITIONAL_BRANCH, "<conditional branch>");
      r = UnorderedGroup(b, l + 1);
      if (!r) r = ConditionalBranch_1(b, l + 1);
      exit_section_(b, l, m, r, false, null);
      return r;
  }

    // '<' Disjunction '>' (AbstractToken)+
    private static boolean ConditionalBranch_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalBranch_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_ANGLE_BRACKET_KEYWORD);
        r = r && Disjunction(b, l + 1);
        r = r && consumeToken(b, R_ANGLE_BRACKET_KEYWORD);
        r = r && ConditionalBranch_1_3(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (AbstractToken)+
    private static boolean ConditionalBranch_1_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalBranch_1_3")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = ConditionalBranch_1_3_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!ConditionalBranch_1_3_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ConditionalBranch_1_3", c)) break;
        }
        exit_section_(b, m, null, r);
        return r;
    }

    // (AbstractToken)
    private static boolean ConditionalBranch_1_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConditionalBranch_1_3_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AbstractToken(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ConjunctionPrivate
    public static boolean Conjunction(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Conjunction")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, CONJUNCTION, "<conjunction>");
        r = ConjunctionPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ConjunctionPrivate
    public static boolean Conjunction1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Conjunction1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, CONJUNCTION_1, "<conjunction 1>");
        r = ConjunctionPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Negation ('&' Negation1)*
    static boolean ConjunctionPrivate(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConjunctionPrivate")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Negation(b, l + 1);
        r = r && ConjunctionPrivate_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('&' Negation1)*
    private static boolean ConjunctionPrivate_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConjunctionPrivate_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!ConjunctionPrivate_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ConjunctionPrivate_1", c)) break;
        }
        return true;
    }

    // '&' Negation1
    private static boolean ConjunctionPrivate_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ConjunctionPrivate_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, AMPERSAND_KEYWORD);
        r = r && Negation1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '[' TypeRef ('|' CrossReferenceableTerminal)? ']'
  public static boolean CrossReference(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "CrossReference")) return false;
      if (!nextTokenIs(b, L_SQUARE_BRACKET_KEYWORD)) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, L_SQUARE_BRACKET_KEYWORD);
      r = r && TypeRef(b, l + 1);
      r = r && CrossReference_2(b, l + 1);
      r = r && consumeToken(b, R_SQUARE_BRACKET_KEYWORD);
      exit_section_(b, m, CROSS_REFERENCE, r);
      return r;
  }

    // ('|' CrossReferenceableTerminal)?
  private static boolean CrossReference_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CrossReference_2")) return false;
    CrossReference_2_0(b, l + 1);
    return true;
  }

  // '|' CrossReferenceableTerminal
  private static boolean CrossReference_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CrossReference_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, PIPE_KEYWORD);
      r = r && CrossReferenceableTerminal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Keyword | RuleCall
  public static boolean CrossReferenceableTerminal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "CrossReferenceableTerminal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, CROSS_REFERENCEABLE_TERMINAL, "<cross referenceable terminal>");
    r = Keyword(b, l + 1);
    if (!r) r = RuleCall(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Conjunction ('|' Conjunction1)*
  public static boolean Disjunction(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Disjunction")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DISJUNCTION, "<disjunction>");
    r = Conjunction(b, l + 1);
    r = r && Disjunction_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // ('|' Conjunction1)*
  private static boolean Disjunction_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Disjunction_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Disjunction_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Disjunction_1", c)) break;
    }
    return true;
  }

    // '|' Conjunction1
  private static boolean Disjunction_1_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Disjunction_1_0")) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, PIPE_KEYWORD);
      r = r && Conjunction1(b, l + 1);
      exit_section_(b, m, null, r);
      return r;
  }

    /* ********************************************************** */
    // 'EOF'
    public static boolean EOF(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EOF")) return false;
        if (!nextTokenIs(b, EOF_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, EOF_KEYWORD);
        exit_section_(b, m, EOF, r);
        return r;
    }

    /* ********************************************************** */
    // REFERENCE_EEnumLiteral_ID ('=' Keyword)?
    public static boolean EnumLiteralDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumLiteralDeclaration")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_EEnumLiteral_ID(b, l + 1);
        r = r && EnumLiteralDeclaration_1(b, l + 1);
        exit_section_(b, m, ENUM_LITERAL_DECLARATION, r);
        return r;
    }

    // ('=' Keyword)?
  private static boolean EnumLiteralDeclaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumLiteralDeclaration_1")) return false;
    EnumLiteralDeclaration_1_0(b, l + 1);
    return true;
  }

  // '=' Keyword
  private static boolean EnumLiteralDeclaration_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumLiteralDeclaration_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, EQUALS_KEYWORD);
      r = r && Keyword(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // EnumLiteralDeclaration EnumLiteralsSuffix1?
  public static boolean EnumLiterals(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "EnumLiterals")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = EnumLiteralDeclaration(b, l + 1);
    r = r && EnumLiterals_1(b, l + 1);
    exit_section_(b, m, ENUM_LITERALS, r);
      return r;
  }

    // EnumLiteralsSuffix1?
    private static boolean EnumLiterals_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumLiterals_1")) return false;
        EnumLiteralsSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('|' EnumLiteralDeclaration)+
    public static boolean EnumLiteralsSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumLiteralsSuffix1")) return false;
        if (!nextTokenIs(b, PIPE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = EnumLiteralsSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!EnumLiteralsSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "EnumLiteralsSuffix1", c)) break;
        }
        exit_section_(b, m, ENUM_LITERALS_SUFFIX_1, r);
        return r;
  }

    // '|' EnumLiteralDeclaration
    private static boolean EnumLiteralsSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumLiteralsSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, PIPE_KEYWORD);
        r = r && EnumLiteralDeclaration(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // (Annotation)* 'enum' ValidID ('returns' TypeRef)? ':' EnumLiterals ';'
    public static boolean EnumRule(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumRule")) return false;
        if (!nextTokenIs(b, "<enum rule>", AT_SIGN_KEYWORD, ENUM_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, ENUM_RULE, "<enum rule>");
        r = EnumRule_0(b, l + 1);
        r = r && consumeToken(b, ENUM_KEYWORD);
        r = r && ValidID(b, l + 1);
        r = r && EnumRule_3(b, l + 1);
        r = r && consumeToken(b, COLON_KEYWORD);
        r = r && EnumLiterals(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (Annotation)*
    private static boolean EnumRule_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumRule_0")) return false;
        while (true) {
            int c = current_position_(b);
            if (!EnumRule_0_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "EnumRule_0", c)) break;
        }
        return true;
    }

    // (Annotation)
    private static boolean EnumRule_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumRule_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Annotation(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('returns' TypeRef)?
    private static boolean EnumRule_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumRule_3")) return false;
        EnumRule_3_0(b, l + 1);
        return true;
    }

    // 'returns' TypeRef
    private static boolean EnumRule_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "EnumRule_3_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, RETURNS_KEYWORD);
        r = r && TypeRef(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'generate' ValidID REFERENCE_EPackage_STRING ('as' ValidID1)?
    public static boolean GeneratedMetamodel(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GeneratedMetamodel")) return false;
        if (!nextTokenIs(b, GENERATE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, GENERATE_KEYWORD);
        r = r && ValidID(b, l + 1);
        r = r && REFERENCE_EPackage_STRING(b, l + 1);
        r = r && GeneratedMetamodel_3(b, l + 1);
    exit_section_(b, m, GENERATED_METAMODEL, r);
    return r;
  }

    // ('as' ValidID1)?
  private static boolean GeneratedMetamodel_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GeneratedMetamodel_3")) return false;
    GeneratedMetamodel_3_0(b, l + 1);
    return true;
  }

    // 'as' ValidID1
  private static boolean GeneratedMetamodel_3_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "GeneratedMetamodel_3_0")) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, AS_KEYWORD);
      r = r && ValidID1(b, l + 1);
      exit_section_(b, m, null, r);
      return r;
  }

  /* ********************************************************** */
  // 'grammar' GrammarID ('with' REFERENCE_Grammar_GrammarID (',' REFERENCE_Grammar_GrammarID)*)? ('hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')')? AbstractMetamodelDeclaration* (AbstractRule)+
  public static boolean Grammar(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "Grammar")) return false;
      if (!nextTokenIs(b, GRAMMAR_KEYWORD)) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, GRAMMAR_KEYWORD);
      r = r && GrammarID(b, l + 1);
      r = r && Grammar_2(b, l + 1);
      r = r && Grammar_3(b, l + 1);
      r = r && Grammar_4(b, l + 1);
      r = r && Grammar_5(b, l + 1);
      exit_section_(b, m, GRAMMAR, r);
      return r;
  }

    // ('with' REFERENCE_Grammar_GrammarID (',' REFERENCE_Grammar_GrammarID)*)?
  private static boolean Grammar_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_2")) return false;
    Grammar_2_0(b, l + 1);
    return true;
  }

    // 'with' REFERENCE_Grammar_GrammarID (',' REFERENCE_Grammar_GrammarID)*
  private static boolean Grammar_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, WITH_KEYWORD);
      r = r && REFERENCE_Grammar_GrammarID(b, l + 1);
    r = r && Grammar_2_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

    // (',' REFERENCE_Grammar_GrammarID)*
  private static boolean Grammar_2_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_2_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Grammar_2_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Grammar_2_0_2", c)) break;
    }
    return true;
  }

  // ',' REFERENCE_Grammar_GrammarID
  private static boolean Grammar_2_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_2_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, COMMA_KEYWORD);
      r = r && REFERENCE_Grammar_GrammarID(b, l + 1);
    exit_section_(b, m, null, r);
      return r;
  }

    // ('hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')')?
    private static boolean Grammar_3(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Grammar_3")) return false;
        Grammar_3_0(b, l + 1);
        return true;
    }

    // 'hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')'
    private static boolean Grammar_3_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Grammar_3_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, HIDDEN_KEYWORD, L_BRACKET_KEYWORD);
        r = r && Grammar_3_0_2(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)?
  private static boolean Grammar_3_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_3_0_2")) return false;
    Grammar_3_0_2_0(b, l + 1);
    return true;
  }

    // REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*
  private static boolean Grammar_3_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_3_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = REFERENCE_AbstractRule_RuleID(b, l + 1);
    r = r && Grammar_3_0_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

    // (',' REFERENCE_AbstractRule_RuleID)*
  private static boolean Grammar_3_0_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_3_0_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Grammar_3_0_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Grammar_3_0_2_0_1", c)) break;
    }
    return true;
  }

  // ',' REFERENCE_AbstractRule_RuleID
  private static boolean Grammar_3_0_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_3_0_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, COMMA_KEYWORD);
      r = r && REFERENCE_AbstractRule_RuleID(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

    // AbstractMetamodelDeclaration*
  private static boolean Grammar_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!AbstractMetamodelDeclaration(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Grammar_4", c)) break;
    }
    return true;
  }

    // (AbstractRule)+
  private static boolean Grammar_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_5")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Grammar_5_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!Grammar_5_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Grammar_5", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

    // (AbstractRule)
  private static boolean Grammar_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Grammar_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = AbstractRule(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ValidID ('.' ValidID)*
  public static boolean GrammarID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GrammarID")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GRAMMAR_ID, "<grammar id>");
    r = ValidID(b, l + 1);
    r = r && GrammarID_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // ('.' ValidID)*
  private static boolean GrammarID_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GrammarID_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!GrammarID_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "GrammarID_1", c)) break;
    }
    return true;
  }

  // '.' ValidID
  private static boolean GrammarID_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "GrammarID_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, DOT_KEYWORD);
      r = r && ValidID(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // AbstractToken GroupSuffix1?
  public static boolean Group(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Group")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, GROUP, "<group>");
    r = AbstractToken(b, l + 1);
    r = r && Group_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // GroupSuffix1?
    private static boolean Group_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Group_1")) return false;
        GroupSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // (AbstractToken)+
    public static boolean GroupSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GroupSuffix1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, GROUP_SUFFIX_1, "<group suffix 1>");
        r = GroupSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!GroupSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "GroupSuffix1", c)) break;
        }
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (AbstractToken)
    private static boolean GroupSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "GroupSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = AbstractToken(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // KeywordPrivate
    public static boolean Keyword(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Keyword")) return false;
        if (!nextTokenIs(b, STRING)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = KeywordPrivate(b, l + 1);
        exit_section_(b, m, KEYWORD, r);
        return r;
    }

    /* ********************************************************** */
    // KeywordPrivate
    public static boolean Keyword1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Keyword1")) return false;
        if (!nextTokenIs(b, STRING)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = KeywordPrivate(b, l + 1);
        exit_section_(b, m, KEYWORD_1, r);
        return r;
    }

    /* ********************************************************** */
    // STRING
    static boolean KeywordPrivate(PsiBuilder b, int l) {
        return consumeToken(b, STRING);
    }

    /* ********************************************************** */
    // 'true' | 'false'
    public static boolean LiteralCondition(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "LiteralCondition")) return false;
        if (!nextTokenIs(b, "<literal condition>", FALSE_KEYWORD, TRUE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, LITERAL_CONDITION, "<literal condition>");
        r = consumeToken(b, TRUE_KEYWORD);
        if (!r) r = consumeToken(b, FALSE_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

  /* ********************************************************** */
  // (REFERENCE_Parameter_ID '=')? (Disjunction)
  public static boolean NamedArgument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamedArgument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NAMED_ARGUMENT, "<named argument>");
    r = NamedArgument_0(b, l + 1);
    r = r && NamedArgument_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // (REFERENCE_Parameter_ID '=')?
    private static boolean NamedArgument_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NamedArgument_0")) return false;
        NamedArgument_0_0(b, l + 1);
        return true;
    }

    // REFERENCE_Parameter_ID '='
    private static boolean NamedArgument_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NamedArgument_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_Parameter_ID(b, l + 1);
        r = r && consumeToken(b, EQUALS_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (Disjunction)
    private static boolean NamedArgument_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "NamedArgument_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Disjunction(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // '!' TerminalTokenElement
  public static boolean NegatedToken(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "NegatedToken")) return false;
      if (!nextTokenIs(b, ACX_MARK_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, ACX_MARK_KEYWORD);
      r = r && TerminalTokenElement(b, l + 1);
      exit_section_(b, m, NEGATED_TOKEN, r);
      return r;
  }

    /* ********************************************************** */
    // NegationPrivate
    public static boolean Negation(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Negation")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, NEGATION, "<negation>");
        r = NegationPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // NegationPrivate
    public static boolean Negation1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Negation1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, NEGATION_1, "<negation 1>");
        r = NegationPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // Atom | '!' Negation
    static boolean NegationPrivate(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NegationPrivate")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Atom(b, l + 1);
        if (!r) r = NegationPrivate_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '!' Negation
    private static boolean NegationPrivate_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "NegationPrivate_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ACX_MARK_KEYWORD);
        r = r && Negation(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ID
    public static boolean Parameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Parameter")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, PARAMETER, r);
        return r;
    }

    /* ********************************************************** */
    // REFERENCE_Parameter_ID
    public static boolean ParameterReference(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParameterReference")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_Parameter_ID(b, l + 1);
        exit_section_(b, m, PARAMETER_REFERENCE, r);
        return r;
    }

  /* ********************************************************** */
  // '(' AssignableAlternatives ')'
  public static boolean ParenthesizedAssignableElement(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ParenthesizedAssignableElement")) return false;
      if (!nextTokenIs(b, L_BRACKET_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, L_BRACKET_KEYWORD);
      r = r && AssignableAlternatives(b, l + 1);
      r = r && consumeToken(b, R_BRACKET_KEYWORD);
      exit_section_(b, m, PARENTHESIZED_ASSIGNABLE_ELEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '(' Disjunction ')'
  public static boolean ParenthesizedCondition(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ParenthesizedCondition")) return false;
      if (!nextTokenIs(b, L_BRACKET_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, L_BRACKET_KEYWORD);
      r = r && Disjunction(b, l + 1);
      r = r && consumeToken(b, R_BRACKET_KEYWORD);
      exit_section_(b, m, PARENTHESIZED_CONDITION, r);
    return r;
  }

  /* ********************************************************** */
  // '(' Alternatives ')'
  public static boolean ParenthesizedElement(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ParenthesizedElement")) return false;
      if (!nextTokenIs(b, L_BRACKET_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, L_BRACKET_KEYWORD);
      r = r && Alternatives(b, l + 1);
      r = r && consumeToken(b, R_BRACKET_KEYWORD);
      exit_section_(b, m, PARENTHESIZED_ELEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // '(' TerminalAlternatives ')'
  public static boolean ParenthesizedTerminalElement(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ParenthesizedTerminalElement")) return false;
      if (!nextTokenIs(b, L_BRACKET_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, L_BRACKET_KEYWORD);
      r = r && TerminalAlternatives(b, l + 1);
      r = r && consumeToken(b, R_BRACKET_KEYWORD);
      exit_section_(b, m, PARENTHESIZED_TERMINAL_ELEMENT, r);
    return r;
  }

  /* ********************************************************** */
  // (Annotation)* ('fragment' RuleNameAndParams ('*' | ('returns' TypeRef)?) | RuleNameAndParams ('returns' TypeRef)?) ('hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')')? ':' Alternatives ';'
  public static boolean ParserRule(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ParserRule")) return false;
      boolean r;
      Marker m = enter_section_(b, l, _NONE_, PARSER_RULE, "<parser rule>");
      r = ParserRule_0(b, l + 1);
      r = r && ParserRule_1(b, l + 1);
      r = r && ParserRule_2(b, l + 1);
      r = r && consumeToken(b, COLON_KEYWORD);
      r = r && Alternatives(b, l + 1);
      r = r && consumeToken(b, SEMICOLON_KEYWORD);
      exit_section_(b, l, m, r, false, null);
      return r;
  }

    // (Annotation)*
    private static boolean ParserRule_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_0")) return false;
        while (true) {
            int c = current_position_(b);
            if (!ParserRule_0_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ParserRule_0", c)) break;
        }
        return true;
    }

    // (Annotation)
    private static boolean ParserRule_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Annotation(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'fragment' RuleNameAndParams ('*' | ('returns' TypeRef)?) | RuleNameAndParams ('returns' TypeRef)?
    private static boolean ParserRule_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = ParserRule_1_0(b, l + 1);
        if (!r) r = ParserRule_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'fragment' RuleNameAndParams ('*' | ('returns' TypeRef)?)
    private static boolean ParserRule_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, FRAGMENT_KEYWORD);
        r = r && RuleNameAndParams(b, l + 1);
        r = r && ParserRule_1_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '*' | ('returns' TypeRef)?
    private static boolean ParserRule_1_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_0_2")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ASTERISK_KEYWORD);
        if (!r) r = ParserRule_1_0_2_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('returns' TypeRef)?
    private static boolean ParserRule_1_0_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_0_2_1")) return false;
        ParserRule_1_0_2_1_0(b, l + 1);
        return true;
    }

    // 'returns' TypeRef
    private static boolean ParserRule_1_0_2_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_0_2_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, RETURNS_KEYWORD);
        r = r && TypeRef(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // RuleNameAndParams ('returns' TypeRef)?
    private static boolean ParserRule_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = RuleNameAndParams(b, l + 1);
        r = r && ParserRule_1_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('returns' TypeRef)?
    private static boolean ParserRule_1_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_1_1")) return false;
        ParserRule_1_1_1_0(b, l + 1);
        return true;
    }

    // 'returns' TypeRef
    private static boolean ParserRule_1_1_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_1_1_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, RETURNS_KEYWORD);
        r = r && TypeRef(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')')?
    private static boolean ParserRule_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2")) return false;
        ParserRule_2_0(b, l + 1);
        return true;
    }

    // 'hidden' '(' (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)? ')'
    private static boolean ParserRule_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, HIDDEN_KEYWORD, L_BRACKET_KEYWORD);
        r = r && ParserRule_2_0_2(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*)?
    private static boolean ParserRule_2_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2_0_2")) return false;
        ParserRule_2_0_2_0(b, l + 1);
        return true;
    }

    // REFERENCE_AbstractRule_RuleID (',' REFERENCE_AbstractRule_RuleID)*
    private static boolean ParserRule_2_0_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2_0_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_AbstractRule_RuleID(b, l + 1);
        r = r && ParserRule_2_0_2_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' REFERENCE_AbstractRule_RuleID)*
    private static boolean ParserRule_2_0_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2_0_2_0_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!ParserRule_2_0_2_0_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "ParserRule_2_0_2_0_1", c)) break;
        }
        return true;
    }

    // ',' REFERENCE_AbstractRule_RuleID
    private static boolean ParserRule_2_0_2_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ParserRule_2_0_2_0_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, COMMA_KEYWORD);
        r = r && REFERENCE_AbstractRule_RuleID(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // ('=>' | '->') '(' Alternatives ')'
    public static boolean PredicatedGroup(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedGroup")) return false;
        if (!nextTokenIs(b, "<predicated group>", PRED_KEYWORD, WEAK_PRED_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PREDICATED_GROUP, "<predicated group>");
        r = PredicatedGroup_0(b, l + 1);
        r = r && consumeToken(b, L_BRACKET_KEYWORD);
        r = r && Alternatives(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '=>' | '->'
    private static boolean PredicatedGroup_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedGroup_0")) return false;
        boolean r;
        r = consumeToken(b, PRED_KEYWORD);
        if (!r) r = consumeToken(b, WEAK_PRED_KEYWORD);
        return r;
    }

    /* ********************************************************** */
    // ('=>' | '->') STRING
    public static boolean PredicatedKeyword(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedKeyword")) return false;
        if (!nextTokenIs(b, "<predicated keyword>", PRED_KEYWORD, WEAK_PRED_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, PREDICATED_KEYWORD, "<predicated keyword>");
        r = PredicatedKeyword_0(b, l + 1);
        r = r && consumeToken(b, STRING);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // '=>' | '->'
    private static boolean PredicatedKeyword_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedKeyword_0")) return false;
        boolean r;
        r = consumeToken(b, PRED_KEYWORD);
        if (!r) r = consumeToken(b, WEAK_PRED_KEYWORD);
        return r;
    }

  /* ********************************************************** */
  // ('=>' | '->') REFERENCE_AbstractRule_RuleID ('<' NamedArgument (',' NamedArgument)* '>')?
  public static boolean PredicatedRuleCall(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "PredicatedRuleCall")) return false;
      if (!nextTokenIs(b, "<predicated rule call>", PRED_KEYWORD, WEAK_PRED_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PREDICATED_RULE_CALL, "<predicated rule call>");
    r = PredicatedRuleCall_0(b, l + 1);
    r = r && REFERENCE_AbstractRule_RuleID(b, l + 1);
    r = r && PredicatedRuleCall_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // '=>' | '->'
  private static boolean PredicatedRuleCall_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "PredicatedRuleCall_0")) return false;
      boolean r;
      r = consumeToken(b, PRED_KEYWORD);
      if (!r) r = consumeToken(b, WEAK_PRED_KEYWORD);
      return r;
  }

    // ('<' NamedArgument (',' NamedArgument)* '>')?
    private static boolean PredicatedRuleCall_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedRuleCall_2")) return false;
        PredicatedRuleCall_2_0(b, l + 1);
        return true;
    }

    // '<' NamedArgument (',' NamedArgument)* '>'
    private static boolean PredicatedRuleCall_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "PredicatedRuleCall_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_ANGLE_BRACKET_KEYWORD);
        r = r && NamedArgument(b, l + 1);
        r = r && PredicatedRuleCall_2_0_2(b, l + 1);
        r = r && consumeToken(b, R_ANGLE_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' NamedArgument)*
  private static boolean PredicatedRuleCall_2_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PredicatedRuleCall_2_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!PredicatedRuleCall_2_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PredicatedRuleCall_2_0_2", c)) break;
    }
    return true;
  }

  // ',' NamedArgument
  private static boolean PredicatedRuleCall_2_0_2_0(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "PredicatedRuleCall_2_0_2_0")) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, COMMA_KEYWORD);
      r = r && NamedArgument(b, l + 1);
      exit_section_(b, m, null, r);
      return r;
  }

    /* ********************************************************** */
    // ID
    public static boolean REFERENCE_AbstractMetamodelDeclaration_ID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_AbstractMetamodelDeclaration_ID")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, REFERENCE_ABSTRACT_METAMODEL_DECLARATION_ID, r);
        return r;
    }

    /* ********************************************************** */
    // ID
    public static boolean REFERENCE_EClassifier_ID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_EClassifier_ID")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, REFERENCE_E_CLASSIFIER_ID, r);
        return r;
    }

    /* ********************************************************** */
    // RuleID
    public static boolean REFERENCE_AbstractRule_RuleID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_AbstractRule_RuleID")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, REFERENCE_ABSTRACT_RULE_RULE_ID, "<reference abstract rule rule id>");
        r = RuleID(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // ID
    public static boolean REFERENCE_EEnumLiteral_ID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_EEnumLiteral_ID")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, REFERENCE_E_ENUM_LITERAL_ID, r);
        return r;
    }

    /* ********************************************************** */
    // STRING
    public static boolean REFERENCE_EPackage_STRING(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_EPackage_STRING")) return false;
        if (!nextTokenIs(b, STRING)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, STRING);
        exit_section_(b, m, REFERENCE_E_PACKAGE_STRING, r);
        return r;
    }

    /* ********************************************************** */
    // GrammarID
    public static boolean REFERENCE_Grammar_GrammarID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_Grammar_GrammarID")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, REFERENCE_GRAMMAR_GRAMMAR_ID, "<reference grammar grammar id>");
        r = GrammarID(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean REFERENCE_Parameter_ID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REFERENCE_Parameter_ID")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, REFERENCE_PARAMETER_ID, r);
    return r;
  }

  /* ********************************************************** */
  // 'import' REFERENCE_EPackage_STRING ('as' ValidID)?
  public static boolean ReferencedMetamodel(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ReferencedMetamodel")) return false;
      if (!nextTokenIs(b, IMPORT_KEYWORD)) return false;
      boolean r;
      Marker m = enter_section_(b);
      r = consumeToken(b, IMPORT_KEYWORD);
      r = r && REFERENCE_EPackage_STRING(b, l + 1);
      r = r && ReferencedMetamodel_2(b, l + 1);
      exit_section_(b, m, REFERENCED_METAMODEL, r);
      return r;
  }

    // ('as' ValidID)?
  private static boolean ReferencedMetamodel_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReferencedMetamodel_2")) return false;
    ReferencedMetamodel_2_0(b, l + 1);
    return true;
  }

  // 'as' ValidID
  private static boolean ReferencedMetamodel_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "ReferencedMetamodel_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, AS_KEYWORD);
      r = r && ValidID(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // REFERENCE_AbstractRule_RuleID ('<' NamedArgument (',' NamedArgument)* '>')?
  public static boolean RuleCall(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleCall")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RULE_CALL, "<rule call>");
    r = REFERENCE_AbstractRule_RuleID(b, l + 1);
    r = r && RuleCall_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // ('<' NamedArgument (',' NamedArgument)* '>')?
    private static boolean RuleCall_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RuleCall_1")) return false;
        RuleCall_1_0(b, l + 1);
        return true;
    }

    // '<' NamedArgument (',' NamedArgument)* '>'
    private static boolean RuleCall_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RuleCall_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_ANGLE_BRACKET_KEYWORD);
        r = r && NamedArgument(b, l + 1);
        r = r && RuleCall_1_0_2(b, l + 1);
        r = r && consumeToken(b, R_ANGLE_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' NamedArgument)*
  private static boolean RuleCall_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleCall_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!RuleCall_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RuleCall_1_0_2", c)) break;
    }
    return true;
  }

  // ',' NamedArgument
  private static boolean RuleCall_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleCall_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, COMMA_KEYWORD);
      r = r && NamedArgument(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ValidID ('::' ValidID)*
  public static boolean RuleID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleID")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RULE_ID, "<rule id>");
    r = ValidID(b, l + 1);
    r = r && RuleID_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

    // ('::' ValidID)*
  private static boolean RuleID_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleID_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!RuleID_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RuleID_1", c)) break;
    }
    return true;
  }

  // '::' ValidID
  private static boolean RuleID_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleID_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, COLONS_KEYWORD);
      r = r && ValidID(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

    /* ********************************************************** */
    // ValidID ('<' (Parameter (',' Parameter)*)? '>')?
    static boolean RuleNameAndParams(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RuleNameAndParams")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = ValidID(b, l + 1);
        r = r && RuleNameAndParams_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('<' (Parameter (',' Parameter)*)? '>')?
    private static boolean RuleNameAndParams_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RuleNameAndParams_1")) return false;
        RuleNameAndParams_1_0(b, l + 1);
        return true;
    }

    // '<' (Parameter (',' Parameter)*)? '>'
    private static boolean RuleNameAndParams_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "RuleNameAndParams_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_ANGLE_BRACKET_KEYWORD);
        r = r && RuleNameAndParams_1_0_1(b, l + 1);
        r = r && consumeToken(b, R_ANGLE_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (Parameter (',' Parameter)*)?
  private static boolean RuleNameAndParams_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleNameAndParams_1_0_1")) return false;
    RuleNameAndParams_1_0_1_0(b, l + 1);
    return true;
  }

    // Parameter (',' Parameter)*
  private static boolean RuleNameAndParams_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleNameAndParams_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Parameter(b, l + 1);
    r = r && RuleNameAndParams_1_0_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

    // (',' Parameter)*
  private static boolean RuleNameAndParams_1_0_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleNameAndParams_1_0_1_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!RuleNameAndParams_1_0_1_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "RuleNameAndParams_1_0_1_0_1", c)) break;
    }
    return true;
  }

  // ',' Parameter
  private static boolean RuleNameAndParams_1_0_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "RuleNameAndParams_1_0_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, COMMA_KEYWORD);
      r = r && Parameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // TerminalGroup TerminalAlternativesSuffix1?
  public static boolean TerminalAlternatives(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TerminalAlternatives")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERMINAL_ALTERNATIVES, "<terminal alternatives>");
    r = TerminalGroup(b, l + 1);
    r = r && TerminalAlternatives_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // TerminalAlternativesSuffix1?
    private static boolean TerminalAlternatives_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalAlternatives_1")) return false;
        TerminalAlternativesSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('|' TerminalGroup)+
    public static boolean TerminalAlternativesSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalAlternativesSuffix1")) return false;
        if (!nextTokenIs(b, PIPE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = TerminalAlternativesSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!TerminalAlternativesSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TerminalAlternativesSuffix1", c)) break;
        }
        exit_section_(b, m, TERMINAL_ALTERNATIVES_SUFFIX_1, r);
        return r;
  }

    // '|' TerminalGroup
    private static boolean TerminalAlternativesSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalAlternativesSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, PIPE_KEYWORD);
        r = r && TerminalGroup(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

  /* ********************************************************** */
  // TerminalToken TerminalGroupSuffix1?
  public static boolean TerminalGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TerminalGroup")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERMINAL_GROUP, "<terminal group>");
    r = TerminalToken(b, l + 1);
    r = r && TerminalGroup_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // TerminalGroupSuffix1?
    private static boolean TerminalGroup_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalGroup_1")) return false;
        TerminalGroupSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // (TerminalToken)+
    public static boolean TerminalGroupSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalGroupSuffix1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, TERMINAL_GROUP_SUFFIX_1, "<terminal group suffix 1>");
        r = TerminalGroupSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!TerminalGroupSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TerminalGroupSuffix1", c)) break;
        }
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (TerminalToken)
    private static boolean TerminalGroupSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalGroupSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = TerminalToken(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // (Annotation)* 'terminal' ('fragment' ValidID | ValidID ('returns' TypeRef)?) ':' TerminalAlternatives ';'
    public static boolean TerminalRule(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule")) return false;
        if (!nextTokenIs(b, "<terminal rule>", AT_SIGN_KEYWORD, TERMINAL_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, TERMINAL_RULE, "<terminal rule>");
        r = TerminalRule_0(b, l + 1);
        r = r && consumeToken(b, TERMINAL_KEYWORD);
        r = r && TerminalRule_2(b, l + 1);
        r = r && consumeToken(b, COLON_KEYWORD);
        r = r && TerminalAlternatives(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // (Annotation)*
    private static boolean TerminalRule_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_0")) return false;
        while (true) {
            int c = current_position_(b);
            if (!TerminalRule_0_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "TerminalRule_0", c)) break;
        }
        return true;
    }

    // (Annotation)
    private static boolean TerminalRule_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = Annotation(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'fragment' ValidID | ValidID ('returns' TypeRef)?
    private static boolean TerminalRule_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_2")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = TerminalRule_2_0(b, l + 1);
        if (!r) r = TerminalRule_2_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // 'fragment' ValidID
    private static boolean TerminalRule_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, FRAGMENT_KEYWORD);
        r = r && ValidID(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ValidID ('returns' TypeRef)?
    private static boolean TerminalRule_2_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_2_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = ValidID(b, l + 1);
        r = r && TerminalRule_2_1_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('returns' TypeRef)?
    private static boolean TerminalRule_2_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_2_1_1")) return false;
        TerminalRule_2_1_1_0(b, l + 1);
        return true;
    }

    // 'returns' TypeRef
    private static boolean TerminalRule_2_1_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRule_2_1_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, RETURNS_KEYWORD);
        r = r && TypeRef(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // TerminalTokenElement ('?' | '*' | '+')?
    public static boolean TerminalToken(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalToken")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, TERMINAL_TOKEN, "<terminal token>");
        r = TerminalTokenElement(b, l + 1);
        r = r && TerminalToken_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // REFERENCE_AbstractRule_RuleID
    public static boolean TerminalRuleCall(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalRuleCall")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, TERMINAL_RULE_CALL, "<terminal rule call>");
        r = REFERENCE_AbstractRule_RuleID(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('?' | '*' | '+')?
    private static boolean TerminalToken_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalToken_1")) return false;
        TerminalToken_1_0(b, l + 1);
        return true;
    }

    // '?' | '*' | '+'
    private static boolean TerminalToken_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TerminalToken_1_0")) return false;
        boolean r;
        r = consumeToken(b, QUES_MARK_KEYWORD);
        if (!r) r = consumeToken(b, ASTERISK_KEYWORD);
        if (!r) r = consumeToken(b, PLUS_KEYWORD);
        return r;
    }

  /* ********************************************************** */
  // CharacterRange | TerminalRuleCall | ParenthesizedTerminalElement | AbstractNegatedToken | Wildcard | EOF
  public static boolean TerminalTokenElement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TerminalTokenElement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, TERMINAL_TOKEN_ELEMENT, "<terminal token element>");
    r = CharacterRange(b, l + 1);
    if (!r) r = TerminalRuleCall(b, l + 1);
    if (!r) r = ParenthesizedTerminalElement(b, l + 1);
    if (!r) r = AbstractNegatedToken(b, l + 1);
      if (!r) r = Wildcard(b, l + 1);
      if (!r) r = EOF(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (REFERENCE_AbstractMetamodelDeclaration_ID '::')? REFERENCE_EClassifier_ID
  public static boolean TypeRef(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "TypeRef")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = TypeRef_0(b, l + 1);
      r = r && REFERENCE_EClassifier_ID(b, l + 1);
      exit_section_(b, m, TYPE_REF, r);
      return r;
  }

    // (REFERENCE_AbstractMetamodelDeclaration_ID '::')?
    private static boolean TypeRef_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeRef_0")) return false;
        TypeRef_0_0(b, l + 1);
        return true;
    }

    // REFERENCE_AbstractMetamodelDeclaration_ID '::'
    private static boolean TypeRef_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "TypeRef_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_AbstractMetamodelDeclaration_ID(b, l + 1);
        r = r && consumeToken(b, COLONS_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // Group UnorderedGroupSuffix1?
  public static boolean UnorderedGroup(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "UnorderedGroup")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UNORDERED_GROUP, "<unordered group>");
    r = Group(b, l + 1);
    r = r && UnorderedGroup_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
      return r;
  }

    // UnorderedGroupSuffix1?
    private static boolean UnorderedGroup_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnorderedGroup_1")) return false;
        UnorderedGroupSuffix1(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // ('&' Group)+
    public static boolean UnorderedGroupSuffix1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnorderedGroupSuffix1")) return false;
        if (!nextTokenIs(b, AMPERSAND_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = UnorderedGroupSuffix1_0(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!UnorderedGroupSuffix1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "UnorderedGroupSuffix1", c)) break;
        }
        exit_section_(b, m, UNORDERED_GROUP_SUFFIX_1, r);
        return r;
  }

    // '&' Group
    private static boolean UnorderedGroupSuffix1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "UnorderedGroupSuffix1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, AMPERSAND_KEYWORD);
        r = r && Group(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

  /* ********************************************************** */
  // '->' TerminalTokenElement
  public static boolean UntilToken(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "UntilToken")) return false;
      if (!nextTokenIs(b, WEAK_PRED_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
      r = consumeToken(b, WEAK_PRED_KEYWORD);
      r = r && TerminalTokenElement(b, l + 1);
    exit_section_(b, m, UNTIL_TOKEN, r);
    return r;
  }

  /* ********************************************************** */
  // ID | 'true' | 'false'
  public static boolean ValidID(PsiBuilder b, int l) {
      if (!recursion_guard_(b, l, "ValidID")) return false;
      boolean r;
      Marker m = enter_section_(b, l, _NONE_, VALID_ID, "<valid id>");
      r = consumeToken(b, ID);
      if (!r) r = consumeToken(b, TRUE_KEYWORD);
      if (!r) r = consumeToken(b, FALSE_KEYWORD);
      exit_section_(b, l, m, r, false, null);
      return r;
  }

    /* ********************************************************** */
    // ValidID
    public static boolean ValidID1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "ValidID1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, VALID_ID_1, "<valid id 1>");
        r = ValidID(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // '.'
    public static boolean Wildcard(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "Wildcard")) return false;
        if (!nextTokenIs(b, DOT_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, DOT_KEYWORD);
        exit_section_(b, m, WILDCARD, r);
        return r;
    }

    /* ********************************************************** */
    // Grammar
    static boolean XtextFile(PsiBuilder b, int l) {
        return Grammar(b, l + 1);
    }

    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r;
        b = adapt_builder_(t, b, this, EXTENDS_SETS_);
        Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        r = parse_root_(t, b);
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }

}
