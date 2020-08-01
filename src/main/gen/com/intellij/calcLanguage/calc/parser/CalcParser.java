// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import static com.intellij.calcLanguage.calc.psi.CalcTypes.*;
import static com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CalcParser implements PsiParser, LightPsiParser {

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    boolean r;
    if (t == ABSTRACT_DEFINITION) {
      r = AbstractDefinition(b, l + 1);
    } else if (t == ADDITION) {
      r = Addition(b, l + 1);
    } else if (t == DECLARED_PARAMETER) {
      r = DeclaredParameter(b, l + 1);
    } else if (t == DEFINITION) {
      r = Definition(b, l + 1);
    } else if (t == EVALUATION) {
      r = Evaluation(b, l + 1);
    } else if (t == EXPRESSION) {
      r = Expression(b, l + 1);
    } else if (t == IMPORT) {
      r = Import(b, l + 1);
    } else if (t == MODULE) {
      r = Module(b, l + 1);
    } else if (t == MULTIPLICATION) {
      r = Multiplication(b, l + 1);
    } else if (t == PRIMARY_EXPRESSION) {
      r = PrimaryExpression(b, l + 1);
    } else if (t == PRIMARY_EXPRESSION_1) {
      r = PrimaryExpression1(b, l + 1);
    } else if (t == PRIMARY_EXPRESSION_2) {
      r = PrimaryExpression2(b, l + 1);
    } else if (t == PRIMARY_EXPRESSION_3) {
      r = PrimaryExpression3(b, l + 1);
    } else if (t == REFERENCE_ABSTRACT_DEFINITION_ID) {
      r = REFERENCE_AbstractDefinition_ID(b, l + 1);
    } else if (t == REFERENCE_MODULE_ID) {
      r = REFERENCE_Module_ID(b, l + 1);
    } else if (t == STATEMENT) {
      r = Statement(b, l + 1);
    } else {
      r = CalcFile(b, l + 1);
    }
    return r;
  }

  /* ********************************************************** */
  // Definition | DeclaredParameter
  public static boolean AbstractDefinition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "AbstractDefinition")) return false;
    if (!nextTokenIs(b, "<abstract definition>", DEF_KEYWORD, ID)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ABSTRACT_DEFINITION, "<abstract definition>");
    r = Definition(b, l + 1);
    if (!r) r = DeclaredParameter(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Multiplication ( ( '+' | '-' ) Multiplication ) *
  public static boolean Addition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Addition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADDITION, "<addition>");
    r = Multiplication(b, l + 1, MULTIPLICATION);
    r = r && Addition_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( ( '+' | '-' ) Multiplication ) *
  private static boolean Addition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Addition_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Addition_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Addition_1", c)) break;
    }
    return true;
  }

  // ( '+' | '-' ) Multiplication
  private static boolean Addition_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Addition_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Addition_1_0_0(b, l + 1);
    r = Addition_1_0_0(b, l + 1);
    r = r && Multiplication(b, l + 1, MULTIPLICATION_ASSIGNED_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-'
  private static boolean Addition_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Addition_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, PLUS_KEYWORD);
    if (!r) r = consumeToken(b, KEYWORD_0);
    return r;
  }

  /* ********************************************************** */
  // Module
  static boolean CalcFile(PsiBuilder b, int l) {
    return Module(b, l + 1);
  }

  /* ********************************************************** */
  // ID
  public static boolean DeclaredParameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "DeclaredParameter")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, DECLARED_PARAMETER, r);
    return r;
  }

  /* ********************************************************** */
  // 'def' ID ( '(' DeclaredParameter ( ',' DeclaredParameter ) * ')' ) ? ':' Expression ';'
  public static boolean Definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Definition")) return false;
    if (!nextTokenIs(b, DEF_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, DEF_KEYWORD, ID);
    r = r && Definition_2(b, l + 1);
    r = r && consumeToken(b, COLON_KEYWORD);
    r = r && Expression(b, l + 1);
    r = r && consumeToken(b, SEMICOLON_KEYWORD);
    exit_section_(b, m, DEFINITION, r);
    return r;
  }

  // ( '(' DeclaredParameter ( ',' DeclaredParameter ) * ')' ) ?
  private static boolean Definition_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Definition_2")) return false;
    Definition_2_0(b, l + 1);
    return true;
  }

  // '(' DeclaredParameter ( ',' DeclaredParameter ) * ')'
  private static boolean Definition_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Definition_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_BRACKET_KEYWORD);
    r = r && DeclaredParameter(b, l + 1);
    r = r && Definition_2_0_2(b, l + 1);
    r = r && consumeToken(b, R_BRACKET_KEYWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' DeclaredParameter ) *
  private static boolean Definition_2_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Definition_2_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Definition_2_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Definition_2_0_2", c)) break;
    }
    return true;
  }

  // ',' DeclaredParameter
  private static boolean Definition_2_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Definition_2_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA_KEYWORD);
    r = r && DeclaredParameter(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // Expression ';'
  public static boolean Evaluation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Evaluation")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EVALUATION, "<evaluation>");
    r = Expression(b, l + 1);
    r = r && consumeToken(b, SEMICOLON_KEYWORD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // Addition
  public static boolean Expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = Addition(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // 'import' REFERENCE_Module_ID
  public static boolean Import(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Import")) return false;
    if (!nextTokenIs(b, IMPORT_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IMPORT_KEYWORD);
    r = r && REFERENCE_Module_ID(b, l + 1);
    exit_section_(b, m, IMPORT, r);
    return r;
  }

  /* ********************************************************** */
  // 'module' ID ( Import ) * ( Statement ) *
  public static boolean Module(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Module")) return false;
    if (!nextTokenIs(b, MODULE_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, MODULE_KEYWORD, ID);
    r = r && Module_2(b, l + 1);
    r = r && Module_3(b, l + 1);
    exit_section_(b, m, MODULE, r);
    return r;
  }

  // ( Import ) *
  private static boolean Module_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Module_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Module_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Module_2", c)) break;
    }
    return true;
  }

  // ( Import )
  private static boolean Module_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Module_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Import(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( Statement ) *
  private static boolean Module_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Module_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Module_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Module_3", c)) break;
    }
    return true;
  }

  // ( Statement )
  private static boolean Module_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Module_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Statement(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PrimaryExpression ( ( '*' | '/' ) PrimaryExpression ) *
  public static boolean Multiplication(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Multiplication")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MULTIPLICATION, "<multiplication>");
    r = PrimaryExpression(b, l + 1, PRIMARY_EXPRESSION);
    r = r && Multiplication_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  public static boolean Multiplication(PsiBuilder b, int l, IElementType type) {
    if (!recursion_guard_(b, l, "Multiplication")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, type, "<multiplication>");
    r = PrimaryExpression(b, l + 1);
    r = r && Multiplication_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( ( '*' | '/' ) PrimaryExpression ) *
  private static boolean Multiplication_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Multiplication_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!Multiplication_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "Multiplication_1", c)) break;
    }
    return true;
  }

  // ( '*' | '/' ) PrimaryExpression
  private static boolean Multiplication_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Multiplication_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = Multiplication_1_0_0(b, l + 1);
    r = r && PrimaryExpression(b, l + 1, PRIMARY_EXPRESSION_ASSIGNED_RIGHT);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/'
  private static boolean Multiplication_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Multiplication_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, ASTERISK_KEYWORD);
    if (!r) r = consumeToken(b, KEYWORD_1);
    return r;
  }

  /* ********************************************************** */
  // PrimaryExpression1 | PrimaryExpression2 | PrimaryExpression3
  public static boolean PrimaryExpression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRIMARY_EXPRESSION, "<primary expression>");
    r = PrimaryExpression1(b, l + 1);
    if (!r) r = PrimaryExpression2(b, l + 1);
    if (!r) r = PrimaryExpression3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  public static boolean PrimaryExpression(PsiBuilder b, int l, IElementType type) {
    if (!recursion_guard_(b, l, "PrimaryExpression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, type, "<primary expression>");
    r = PrimaryExpression1(b, l + 1);
    if (!r) r = PrimaryExpression2(b, l + 1);
    if (!r) r = PrimaryExpression3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '(' Expression ')'
  public static boolean PrimaryExpression1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression1")) return false;
    if (!nextTokenIs(b, L_BRACKET_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_BRACKET_KEYWORD);
    r = r && Expression(b, l + 1);
    r = r && consumeToken(b, R_BRACKET_KEYWORD);
    exit_section_(b, m, PRIMARY_EXPRESSION_1, r);
    return r;
  }

  /* ********************************************************** */
  // NUMBER
  public static boolean PrimaryExpression2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression2")) return false;
    if (!nextTokenIs(b, NUMBER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NUMBER);
    exit_section_(b, m, PRIMARY_EXPRESSION_2, r);
    return r;
  }

  /* ********************************************************** */
  // REFERENCE_AbstractDefinition_ID ( '(' Expression ( ',' Expression ) * ')' ) ?
  public static boolean PrimaryExpression3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression3")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = REFERENCE_AbstractDefinition_ID(b, l + 1);
    r = r && PrimaryExpression3_1(b, l + 1);
    exit_section_(b, m, PRIMARY_EXPRESSION_3, r);
    return r;
  }

  // ( '(' Expression ( ',' Expression ) * ')' ) ?
  private static boolean PrimaryExpression3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression3_1")) return false;
    PrimaryExpression3_1_0(b, l + 1);
    return true;
  }

  // '(' Expression ( ',' Expression ) * ')'
  private static boolean PrimaryExpression3_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression3_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, L_BRACKET_KEYWORD);
    r = r && Expression(b, l + 1);
    r = r && PrimaryExpression3_1_0_2(b, l + 1);
    r = r && consumeToken(b, R_BRACKET_KEYWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  // ( ',' Expression ) *
  private static boolean PrimaryExpression3_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression3_1_0_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!PrimaryExpression3_1_0_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "PrimaryExpression3_1_0_2", c)) break;
    }
    return true;
  }

  // ',' Expression
  private static boolean PrimaryExpression3_1_0_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "PrimaryExpression3_1_0_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA_KEYWORD);
    r = r && Expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean REFERENCE_AbstractDefinition_ID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REFERENCE_AbstractDefinition_ID")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, REFERENCE_ABSTRACT_DEFINITION_ID, r);
    return r;
  }

  /* ********************************************************** */
  // ID
  public static boolean REFERENCE_Module_ID(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "REFERENCE_Module_ID")) return false;
    if (!nextTokenIs(b, ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ID);
    exit_section_(b, m, REFERENCE_MODULE_ID, r);
    return r;
  }

  /* ********************************************************** */
  // Definition | Evaluation
  public static boolean Statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "Statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = Definition(b, l + 1);
    if (!r) r = Evaluation(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

}
