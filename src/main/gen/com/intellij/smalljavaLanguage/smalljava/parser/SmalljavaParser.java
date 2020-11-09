// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import static com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy.*;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SmalljavaParser implements PsiParser, LightPsiParser {

    public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[]{
            create_token_set_(SJ_IF_BLOCK, SJ_IF_BLOCK_1),
            create_token_set_(SJ_FIELD, SJ_MEMBER, SJ_METHOD),
            create_token_set_(SJ_PARAMETER, SJ_SYMBOL, SJ_VARIABLE_DECLARATION),
    };

    static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
        boolean r;
        if (t == QUALIFIED_NAME) {
            r = QualifiedName(b, l + 1);
        } else if (t == QUALIFIED_NAME_WITH_WILDCARD) {
            r = QualifiedNameWithWildcard(b, l + 1);
        } else if (t == REFERENCE_SJ_CLASS_QUALIFIED_NAME) {
            r = REFERENCE_SJClass_QualifiedName(b, l + 1);
        } else if (t == REFERENCE_SJ_MEMBER_ID) {
            r = REFERENCE_SJMember_ID(b, l + 1);
        } else if (t == REFERENCE_SJ_SYMBOL_ID) {
            r = REFERENCE_SJSymbol_ID(b, l + 1);
        } else if (t == SJ_ACCESS_LEVEL) {
            r = SJAccessLevel(b, l + 1);
        } else if (t == SJ_ASSIGNMENT) {
            r = SJAssignment(b, l + 1);
        } else if (t == SJ_BLOCK) {
            r = SJBlock(b, l + 1);
        } else if (t == SJ_CLASS) {
            r = SJClass(b, l + 1);
        } else if (t == SJ_EXPRESSION) {
            r = SJExpression(b, l + 1);
        } else if (t == SJ_FIELD) {
            r = SJField(b, l + 1);
        } else if (t == SJ_IF_BLOCK) {
            r = SJIfBlock(b, l + 1);
        } else if (t == SJ_IF_BLOCK_1) {
            r = SJIfBlock1(b, l + 1);
        } else if (t == SJ_IF_STATEMENT) {
            r = SJIfStatement(b, l + 1);
        } else if (t == SJ_IMPORT) {
            r = SJImport(b, l + 1);
        } else if (t == SJ_MEMBER) {
            r = SJMember(b, l + 1);
        } else if (t == SJ_METHOD) {
            r = SJMethod(b, l + 1);
        } else if (t == SJ_METHOD_BODY) {
            r = SJMethodBody(b, l + 1);
        } else if (t == SJ_PARAMETER) {
            r = SJParameter(b, l + 1);
        } else if (t == SJ_PROGRAM) {
            r = SJProgram(b, l + 1);
        } else if (t == SJ_RETURN) {
            r = SJReturn(b, l + 1);
        } else if (t == SJ_SELECTION_EXPRESSION) {
            r = SJSelectionExpression(b, l + 1);
        } else if (t == SJ_STATEMENT) {
            r = SJStatement(b, l + 1);
        } else if (t == SJ_SYMBOL) {
            r = SJSymbol(b, l + 1);
        } else if (t == SJ_TERMINAL_EXPRESSION) {
            r = SJTerminalExpression(b, l + 1);
        } else if (t == SJ_VARIABLE_DECLARATION) {
            r = SJVariableDeclaration(b, l + 1);
        } else {
            r = SmalljavaFile(b, l + 1);
        }
        return r;
    }

    /* ********************************************************** */
    // ID ('.' ID)*
    public static boolean QualifiedName(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedName")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        r = r && QualifiedName_1(b, l + 1);
        exit_section_(b, m, QUALIFIED_NAME, r);
        return r;
    }

    // ('.' ID)*
    private static boolean QualifiedName_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedName_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!QualifiedName_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "QualifiedName_1", c)) break;
        }
        return true;
    }

    // '.' ID
    private static boolean QualifiedName_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedName_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, DOT_KEYWORD, ID);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // QualifiedName '.*'?
    public static boolean QualifiedNameWithWildcard(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedNameWithWildcard")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = QualifiedName(b, l + 1);
        r = r && QualifiedNameWithWildcard_1(b, l + 1);
        exit_section_(b, m, QUALIFIED_NAME_WITH_WILDCARD, r);
        return r;
    }

    // '.*'?
    private static boolean QualifiedNameWithWildcard_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "QualifiedNameWithWildcard_1")) return false;
        consumeToken(b, KEYWORD_1);
        return true;
    }

    /* ********************************************************** */
    // QualifiedName
    public static boolean REFERENCE_SJClass_QualifiedName(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_SJClass_QualifiedName")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = QualifiedName(b, l + 1);
        exit_section_(b, m, REFERENCE_SJ_CLASS_QUALIFIED_NAME, r);
        return r;
    }

    /* ********************************************************** */
    // ID
    public static boolean REFERENCE_SJMember_ID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_SJMember_ID")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, REFERENCE_SJ_MEMBER_ID, r);
        return r;
    }

    /* ********************************************************** */
    // ID
    public static boolean REFERENCE_SJSymbol_ID(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "REFERENCE_SJSymbol_ID")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ID);
        exit_section_(b, m, REFERENCE_SJ_SYMBOL_ID, r);
        return r;
    }

    /* ********************************************************** */
    // 'private' | 'public' | 'protected'
    public static boolean SJAccessLevel(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJAccessLevel")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_ACCESS_LEVEL, "<sj access level>");
        r = consumeToken(b, PRIVATE_KEYWORD);
        if (!r) r = consumeToken(b, PUBLIC_KEYWORD);
        if (!r) r = consumeToken(b, PROTECTED_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SJSelectionExpression ('=' SJExpression)?
    public static boolean SJAssignment(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJAssignment")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_ASSIGNMENT, "<sj assignment>");
        r = SJSelectionExpression(b, l + 1);
        r = r && SJAssignment_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('=' SJExpression)?
    private static boolean SJAssignment_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJAssignment_1")) return false;
        SJAssignment_1_0(b, l + 1);
        return true;
    }

    // '=' SJExpression
    private static boolean SJAssignment_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJAssignment_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, EQUALS_KEYWORD);
        r = r && SJExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // SJMethodBody | SJIfBlock
    public static boolean SJBlock(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJBlock")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_BLOCK, "<sj block>");
        r = SJMethodBody(b, l + 1);
        if (!r) r = SJIfBlock(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // 'class' ID ('extends' REFERENCE_SJClass_QualifiedName)? '{' SJMember* '}'
    public static boolean SJClass(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJClass")) return false;
        if (!nextTokenIs(b, CLASS_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, CLASS_KEYWORD, ID);
        r = r && SJClass_2(b, l + 1);
        r = r && consumeToken(b, L_BRACE_KEYWORD);
        r = r && SJClass_4(b, l + 1);
        r = r && consumeToken(b, R_BRACE_KEYWORD);
        exit_section_(b, m, SJ_CLASS, r);
        return r;
    }

    // ('extends' REFERENCE_SJClass_QualifiedName)?
    private static boolean SJClass_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJClass_2")) return false;
        SJClass_2_0(b, l + 1);
        return true;
    }

    // 'extends' REFERENCE_SJClass_QualifiedName
    private static boolean SJClass_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJClass_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, EXTENDS_KEYWORD);
        r = r && REFERENCE_SJClass_QualifiedName(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // SJMember*
    private static boolean SJClass_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJClass_4")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJMember(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJClass_4", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // SJAssignment
    public static boolean SJExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJExpression")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_EXPRESSION, "<sj expression>");
        r = SJAssignment(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SJAccessLevel? REFERENCE_SJClass_QualifiedName ID ';'
    public static boolean SJField(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJField")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_FIELD, "<sj field>");
        r = SJField_0(b, l + 1);
        r = r && REFERENCE_SJClass_QualifiedName(b, l + 1);
        r = r && consumeTokens(b, 0, ID, SEMICOLON_KEYWORD);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // SJAccessLevel?
    private static boolean SJField_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJField_0")) return false;
        SJAccessLevel(b, l + 1);
        return true;
    }

    /* ********************************************************** */
    // SJIfBlockPrivate
    public static boolean SJIfBlock(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfBlock")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_IF_BLOCK, "<sj if block>");
        r = SJIfBlockPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SJIfBlockPrivate
    public static boolean SJIfBlock1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfBlock1")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_IF_BLOCK_1, "<sj if block 1>");
        r = SJIfBlockPrivate(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SJStatement | '{' SJStatement+ '}'
    static boolean SJIfBlockPrivate(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfBlockPrivate")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SJStatement(b, l + 1);
        if (!r) r = SJIfBlockPrivate_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // '{' SJStatement+ '}'
    private static boolean SJIfBlockPrivate_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfBlockPrivate_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_BRACE_KEYWORD);
        r = r && SJIfBlockPrivate_1_1(b, l + 1);
        r = r && consumeToken(b, R_BRACE_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // SJStatement+
    private static boolean SJIfBlockPrivate_1_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfBlockPrivate_1_1")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SJStatement(b, l + 1);
        while (r) {
            int c = current_position_(b);
            if (!SJStatement(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJIfBlockPrivate_1_1", c)) break;
        }
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'if' '(' SJExpression ')' SJIfBlock ('else' SJIfBlock1)?
    public static boolean SJIfStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfStatement")) return false;
        if (!nextTokenIs(b, IF_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeTokens(b, 0, IF_KEYWORD, L_BRACKET_KEYWORD);
        r = r && SJExpression(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        r = r && SJIfBlock(b, l + 1);
        r = r && SJIfStatement_5(b, l + 1);
        exit_section_(b, m, SJ_IF_STATEMENT, r);
        return r;
    }

    // ('else' SJIfBlock1)?
    private static boolean SJIfStatement_5(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfStatement_5")) return false;
        SJIfStatement_5_0(b, l + 1);
        return true;
    }

    // 'else' SJIfBlock1
    private static boolean SJIfStatement_5_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJIfStatement_5_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, ELSE_KEYWORD);
        r = r && SJIfBlock1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // 'import' QualifiedNameWithWildcard ';'
    public static boolean SJImport(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJImport")) return false;
        if (!nextTokenIs(b, IMPORT_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, IMPORT_KEYWORD);
        r = r && QualifiedNameWithWildcard(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, m, SJ_IMPORT, r);
        return r;
    }

    /* ********************************************************** */
    // SJField | SJMethod
    public static boolean SJMember(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMember")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, SJ_MEMBER, "<sj member>");
        r = SJField(b, l + 1);
        if (!r) r = SJMethod(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // SJAccessLevel? REFERENCE_SJClass_QualifiedName ID '(' (SJParameter (',' SJParameter)*)? ')' SJMethodBody
    public static boolean SJMethod(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_METHOD, "<sj method>");
        r = SJMethod_0(b, l + 1);
        r = r && REFERENCE_SJClass_QualifiedName(b, l + 1);
        r = r && consumeTokens(b, 0, ID, L_BRACKET_KEYWORD);
        r = r && SJMethod_4(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        r = r && SJMethodBody(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // SJAccessLevel?
    private static boolean SJMethod_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod_0")) return false;
        SJAccessLevel(b, l + 1);
        return true;
    }

    // (SJParameter (',' SJParameter)*)?
    private static boolean SJMethod_4(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod_4")) return false;
        SJMethod_4_0(b, l + 1);
        return true;
    }

    // SJParameter (',' SJParameter)*
    private static boolean SJMethod_4_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod_4_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SJParameter(b, l + 1);
        r = r && SJMethod_4_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' SJParameter)*
    private static boolean SJMethod_4_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod_4_0_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJMethod_4_0_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJMethod_4_0_1", c)) break;
        }
        return true;
    }

    // ',' SJParameter
    private static boolean SJMethod_4_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethod_4_0_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, COMMA_KEYWORD);
        r = r && SJParameter(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // '{' SJStatement* '}'
    public static boolean SJMethodBody(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethodBody")) return false;
        if (!nextTokenIs(b, L_BRACE_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_BRACE_KEYWORD);
        r = r && SJMethodBody_1(b, l + 1);
        r = r && consumeToken(b, R_BRACE_KEYWORD);
        exit_section_(b, m, SJ_METHOD_BODY, r);
        return r;
    }

    // SJStatement*
    private static boolean SJMethodBody_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJMethodBody_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJStatement(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJMethodBody_1", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // REFERENCE_SJClass_QualifiedName ID
    public static boolean SJParameter(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJParameter")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_SJClass_QualifiedName(b, l + 1);
        r = r && consumeToken(b, ID);
        exit_section_(b, m, SJ_PARAMETER, r);
        return r;
    }

    /* ********************************************************** */
    // ('package' QualifiedName ';')? SJImport* SJClass*
    public static boolean SJProgram(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJProgram")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_PROGRAM, "<sj program>");
        r = SJProgram_0(b, l + 1);
        r = r && SJProgram_1(b, l + 1);
        r = r && SJProgram_2(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('package' QualifiedName ';')?
    private static boolean SJProgram_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJProgram_0")) return false;
        SJProgram_0_0(b, l + 1);
        return true;
    }

    // 'package' QualifiedName ';'
    private static boolean SJProgram_0_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJProgram_0_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, PACKAGE_KEYWORD);
        r = r && QualifiedName(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // SJImport*
    private static boolean SJProgram_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJProgram_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJImport(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJProgram_1", c)) break;
        }
        return true;
    }

    // SJClass*
    private static boolean SJProgram_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJProgram_2")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJClass(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJProgram_2", c)) break;
        }
        return true;
    }

    /* ********************************************************** */
    // 'return' SJExpression ';'
    public static boolean SJReturn(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJReturn")) return false;
        if (!nextTokenIs(b, RETURN_KEYWORD)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, RETURN_KEYWORD);
        r = r && SJExpression(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, m, SJ_RETURN, r);
        return r;
    }

    /* ********************************************************** */
    // SJTerminalExpression ('.' REFERENCE_SJMember_ID ('(' (SJExpression (',' SJExpression)*)? ')')?)*
    public static boolean SJSelectionExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_SELECTION_EXPRESSION, "<sj selection expression>");
        r = SJTerminalExpression(b, l + 1);
        r = r && SJSelectionExpression_1(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // ('.' REFERENCE_SJMember_ID ('(' (SJExpression (',' SJExpression)*)? ')')?)*
    private static boolean SJSelectionExpression_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJSelectionExpression_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJSelectionExpression_1", c)) break;
        }
        return true;
    }

    // '.' REFERENCE_SJMember_ID ('(' (SJExpression (',' SJExpression)*)? ')')?
    private static boolean SJSelectionExpression_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, DOT_KEYWORD);
        r = r && REFERENCE_SJMember_ID(b, l + 1);
        r = r && SJSelectionExpression_1_0_2(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // ('(' (SJExpression (',' SJExpression)*)? ')')?
    private static boolean SJSelectionExpression_1_0_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2")) return false;
        SJSelectionExpression_1_0_2_0(b, l + 1);
        return true;
    }

    // '(' (SJExpression (',' SJExpression)*)? ')'
    private static boolean SJSelectionExpression_1_0_2_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_BRACKET_KEYWORD);
        r = r && SJSelectionExpression_1_0_2_0_1(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // (SJExpression (',' SJExpression)*)?
    private static boolean SJSelectionExpression_1_0_2_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2_0_1")) return false;
        SJSelectionExpression_1_0_2_0_1_0(b, l + 1);
        return true;
    }

    // SJExpression (',' SJExpression)*
    private static boolean SJSelectionExpression_1_0_2_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2_0_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SJExpression(b, l + 1);
        r = r && SJSelectionExpression_1_0_2_0_1_0_1(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    // (',' SJExpression)*
    private static boolean SJSelectionExpression_1_0_2_0_1_0_1(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2_0_1_0_1")) return false;
        while (true) {
            int c = current_position_(b);
            if (!SJSelectionExpression_1_0_2_0_1_0_1_0(b, l + 1)) break;
            if (!empty_element_parsed_guard_(b, "SJSelectionExpression_1_0_2_0_1_0_1", c)) break;
        }
        return true;
    }

    // ',' SJExpression
    private static boolean SJSelectionExpression_1_0_2_0_1_0_1_0(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSelectionExpression_1_0_2_0_1_0_1_0")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, COMMA_KEYWORD);
        r = r && SJExpression(b, l + 1);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // SJVariableDeclaration | SJReturn | SJExpression ';' | SJIfStatement
    public static boolean SJStatement(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJStatement")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_STATEMENT, "<sj statement>");
        r = SJVariableDeclaration(b, l + 1);
        if (!r) r = SJReturn(b, l + 1);
        if (!r) r = SJStatement_2(b, l + 1);
        if (!r) r = SJIfStatement(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // SJExpression ';'
    private static boolean SJStatement_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJStatement_2")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = SJExpression(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // SJVariableDeclaration | SJParameter
    public static boolean SJSymbol(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJSymbol")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b, l, _COLLAPSE_, SJ_SYMBOL, null);
        r = SJVariableDeclaration(b, l + 1);
        if (!r) r = SJParameter(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    /* ********************************************************** */
    // STRING | INT | ('true' | 'false') | 'this' | 'super' | 'null' | REFERENCE_SJSymbol_ID | 'new' REFERENCE_SJClass_QualifiedName '(' ')' | '(' SJExpression ')'
    public static boolean SJTerminalExpression(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJTerminalExpression")) return false;
        boolean r;
        Marker m = enter_section_(b, l, _NONE_, SJ_TERMINAL_EXPRESSION, "<sj terminal expression>");
        r = consumeToken(b, STRING);
        if (!r) r = consumeToken(b, INT);
        if (!r) r = SJTerminalExpression_2(b, l + 1);
        if (!r) r = consumeToken(b, THIS_KEYWORD);
        if (!r) r = consumeToken(b, SUPER_KEYWORD);
        if (!r) r = consumeToken(b, NULL_KEYWORD);
        if (!r) r = REFERENCE_SJSymbol_ID(b, l + 1);
        if (!r) r = SJTerminalExpression_7(b, l + 1);
        if (!r) r = SJTerminalExpression_8(b, l + 1);
        exit_section_(b, l, m, r, false, null);
        return r;
    }

    // 'true' | 'false'
    private static boolean SJTerminalExpression_2(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJTerminalExpression_2")) return false;
        boolean r;
        r = consumeToken(b, TRUE_KEYWORD);
        if (!r) r = consumeToken(b, FALSE_KEYWORD);
        return r;
    }

    // 'new' REFERENCE_SJClass_QualifiedName '(' ')'
    private static boolean SJTerminalExpression_7(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJTerminalExpression_7")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, NEW_KEYWORD);
        r = r && REFERENCE_SJClass_QualifiedName(b, l + 1);
        r = r && consumeTokens(b, 0, L_BRACKET_KEYWORD, R_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    // '(' SJExpression ')'
    private static boolean SJTerminalExpression_8(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJTerminalExpression_8")) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = consumeToken(b, L_BRACKET_KEYWORD);
        r = r && SJExpression(b, l + 1);
        r = r && consumeToken(b, R_BRACKET_KEYWORD);
        exit_section_(b, m, null, r);
        return r;
    }

    /* ********************************************************** */
    // REFERENCE_SJClass_QualifiedName ID '=' SJExpression ';'
    public static boolean SJVariableDeclaration(PsiBuilder b, int l) {
        if (!recursion_guard_(b, l, "SJVariableDeclaration")) return false;
        if (!nextTokenIs(b, ID)) return false;
        boolean r;
        Marker m = enter_section_(b);
        r = REFERENCE_SJClass_QualifiedName(b, l + 1);
        r = r && consumeTokens(b, 0, ID, EQUALS_KEYWORD);
        r = r && SJExpression(b, l + 1);
        r = r && consumeToken(b, SEMICOLON_KEYWORD);
        exit_section_(b, m, SJ_VARIABLE_DECLARATION, r);
        return r;
    }

    /* ********************************************************** */
    // SJProgram
    static boolean SmalljavaFile(PsiBuilder b, int l) {
        return SJProgram(b, l + 1);
    }

    public ASTNode parse(IElementType t, PsiBuilder b) {
        parseLight(t, b);
        return b.getTreeBuilt();
    }

    public void parseLight(IElementType t, PsiBuilder b) {
        boolean r;
        b = adapt_builder_(t, b, this, EXTENDS_SETS_);
        Marker m = enter_section_(b, 0, _COLLAPSE_, null);
        r = parse_root_(t, b);
        exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
    }

    protected boolean parse_root_(IElementType t, PsiBuilder b) {
        return parse_root_(t, b, 0);
    }

}
