package com.intellij.xtextLanguage.xtext.psi.impl;

public class XtextPsiImplUtil {


//    public static String getName(XtextParserRule element) {
//        return Optional.ofNullable(getNameIdentifier(element))
//                .map(PsiElement::getText)
//                .orElse(null);
//
//
//    }
//
//    public static PsiElement setName(XtextParserRule element, String newName) {
//        PsiElement oldName = getNameIdentifier(element);
//        if (oldName != null) {
//            XtextValidID newNamePsi = (XtextValidID) XtextElementFactory.createValidID(newName);
//            element.getNode().replaceChild(oldName.getNode(), newNamePsi.getNode());
//
//        }
//        return element;
//    }
//
//    public static String getName(XtextEnumRule element) {
//        return Optional.ofNullable(getNameIdentifier(element))
//                .map(PsiElement::getText)
//                .orElse(null);
//
//
//    }
//
//    public static PsiElement setName(XtextEnumRule element, String newName) {
//        PsiElement oldName = getNameIdentifier(element);
//        if (oldName != null) {
//            XtextValidID newNamePsi = (XtextValidID) XtextElementFactory.createValidID(newName);
//            oldName.getParent().getNode().replaceChild(oldName.getNode(), newNamePsi.getNode());
//
//        }
//        return element;
//    }
//
//    public static String getName(XtextTerminalRule element) {
//        return Optional.ofNullable(getNameIdentifier(element))
//                .map(PsiElement::getText)
//                .orElse(null);
//
//
//    }
//
//    public static PsiElement setName(XtextTerminalRule element, String newName) {
//        PsiElement oldName = getNameIdentifier(element);
//        if (oldName != null) {
//            XtextValidID newNamePsi = (XtextValidID) XtextElementFactory.createValidID(newName);
//            oldName.getParent().getNode().replaceChild(oldName.getNode(), newNamePsi.getNode());
//
//        }
//        return element;
//    }
//
//    public static PsiElement getNameIdentifier(XtextParserRule element) {
//
//        XtextRuleNameAndParams nameAndParams = PsiTreeUtil.getChildOfType(element, XtextRuleNameAndParams.class);
//        if (nameAndParams == null) {
//            throw new NullPointerException();
//        }
//        return nameAndParams.getValidID();
//    }
//
//    public static PsiElement getNameIdentifier(XtextTerminalRule element) {
//
//        return  element.getValidID();
//    }
//
//    public static PsiElement getNameIdentifier(XtextEnumRule element) {
//
//        return element.getValidID();
//    }


}
