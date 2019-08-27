package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.psi.tree.IElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextElementType;
import com.intellij.xtextLanguage.xtext.psi.XtextTokenType;
import com.intellij.xtextLanguage.xtext.psi.XtextTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class XtextTypesUtil {
    public static final ArrayList<XtextElementType> myTypes = new ArrayList<>();
    public static final ArrayList<IElementType> xtextTypes = new ArrayList<>(Arrays.asList(
            XtextTypes.ABSTRACT_METAMODEL_DECLARATION,
            XtextTypes.ABSTRACT_NEGATED_TOKEN,
            XtextTypes.ABSTRACT_TERMINAL,
            XtextTypes.ABSTRACT_TOKEN,
            XtextTypes.ABSTRACT_TOKEN_WITH_CARDINALITY,
            XtextTypes.ACTION,
            XtextTypes.ALTERNATIVES,
            XtextTypes.ANNOTATION,
            XtextTypes.ASSIGNABLE_ALTERNATIVES,
            XtextTypes.ASSIGNABLE_TERMINAL,
            XtextTypes.ASSIGNMENT,
            XtextTypes.ATOM,
            XtextTypes.CHARACTER_RANGE,
            XtextTypes.CONDITIONAL_BRANCH,
            XtextTypes.CONJUNCTION,
            XtextTypes.CROSS_REFERENCE,
            XtextTypes.CROSS_REFERENCEABLE_TERMINAL,
            XtextTypes.DISJUNCTION,
            XtextTypes.ENUM_LITERALS,
            XtextTypes.ENUM_LITERAL_DECLARATION,
            XtextTypes.ENUM_RULE,
            XtextTypes.EOF,
            XtextTypes.GENERATED_METAMODEL,
            XtextTypes.GRAMMAR_ID,
            XtextTypes.GROUP,
            XtextTypes.KEYWORD,
            XtextTypes.LITERAL_CONDITION,
            XtextTypes.NAMED_ARGUMENT,
            XtextTypes.NEGATED_TOKEN,
            XtextTypes.NEGATION,
            XtextTypes.PARAMETER,
            XtextTypes.PARAMETER_REFERENCE,
            XtextTypes.PARENTHESIZED_ASSIGNABLE_ELEMENT,
            XtextTypes.PARENTHESIZED_CONDITION,
            XtextTypes.PARENTHESIZED_ELEMENT,
            XtextTypes.PARENTHESIZED_TERMINAL_ELEMENT,
            XtextTypes.PARSER_RULE,
            XtextTypes.PREDICATED_GROUP,
            XtextTypes.PREDICATED_KEYWORD,
            XtextTypes.PREDICATED_RULE_CALL,
            XtextTypes.REFERENCED_METAMODEL,
            XtextTypes.REFERENCE_ABSTRACT_METAMODEL_DECLARATION,
            XtextTypes.REFERENCE_ABSTRACT_RULE_RULE_ID,
            XtextTypes.REFERENCE_ECORE_E_CLASSIFIER,
            XtextTypes.REFERENCE_ECORE_E_ENUM_LITERAL,
            XtextTypes.REFERENCE_ECORE_E_PACKAGE_STRING,
            XtextTypes.REFERENCE_GRAMMAR_GRAMMAR_ID,
            XtextTypes.REFERENCE_PARAMETER_ID,
            XtextTypes.RULE_CALL,
            XtextTypes.RULE_ID,
            XtextTypes.RULE_IDENTIFIER,
            XtextTypes.RULE_NAME_AND_PARAMS,
            XtextTypes.TERMINAL_ALTERNATIVES,
            XtextTypes.TERMINAL_GROUP,
            XtextTypes.TERMINAL_RULE,
            XtextTypes.TERMINAL_RULE_CALL,
            XtextTypes.TERMINAL_TOKEN,
            XtextTypes.TERMINAL_TOKEN_ELEMENT,
            XtextTypes.TYPE_REF,
            XtextTypes.UNORDERED_GROUP,
            XtextTypes.UNTIL_TOKEN,
            XtextTypes.VALID_ID,
            XtextTypes.WILDCARD
    ));
    public static XtextElementType findByName(String name){
        for(IElementType element : xtextTypes){
            if(element instanceof XtextElementType){
                if(((XtextElementType) element).getDebugName().equals(name)){
                    return (XtextElementType) element;
                }
            }

        }
        return null;
    }
    public static ArrayList<XtextTokenType> findKeywords(XtextElementType type){
        ArrayList<XtextTokenType> keywords = new ArrayList<>();
        switch (type.getDebugName()){
            case "ENUM_RULE":
                keywords.add((XtextTokenType) XtextTypes.ENUM);
                break;
            case "PARSER_RULE":
                keywords.add((XtextTokenType) XtextTypes.FRAGMENT);
                keywords.add((XtextTokenType) XtextTypes.AT_SIGN);
                break;
            case "TERMINAL_RULE":
                keywords.add((XtextTokenType) XtextTypes.TERMINAL);
                break;
            case "ABSTRACT_METAMODEL_DECLARATION":
                keywords.add((XtextTokenType) XtextTypes.GENERATE);
                keywords.add((XtextTokenType) XtextTypes.IMPORT);
                break;
            default:
                break;

        }
        return keywords;
    }
}
