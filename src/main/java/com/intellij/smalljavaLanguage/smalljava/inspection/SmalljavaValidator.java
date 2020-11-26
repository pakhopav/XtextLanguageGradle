package com.intellij.smalljavaLanguage.smalljava.inspection;

import com.intellij.xtextLanguage.xtext.inspections.AbstractValidator;
import com.intellij.xtextLanguage.xtext.inspections.Check;
import smallJava.SJAssignment;
import smallJava.SJClass;
import smallJava.SJField;
import smallJava.SmallJavaPackage;

public class SmalljavaValidator extends AbstractValidator {

    @Check
    public void checkNameStartsWithCapital(SJClass element) {
        if (!Character.isUpperCase(element.getName().charAt(0))) {
            warning("Name should start with a capital rrrr",
                    SmallJavaPackage.Literals.SJ_CLASS__NAME);
        }
    }

    @Check
    public void checkNameStartsWithCapital2(SJClass element) {
        if (element.getMembers().size() == 0) {
            warning("yyy empty class",
                    SmallJavaPackage.Literals.SJ_CLASS__NAME);
        }
    }

    @Check
    public void checkNameStartsWithCapital5(SJField element) {
        if (Character.isUpperCase(element.getName().charAt(0))) {
            warning("Name of fild should NOT start with a capital",
                    SmallJavaPackage.Literals.SJ_CLASS__NAME);
        }
    }


    @Check
    public void AAA(SJClass element, SJAssignment assignment) {
        if (!Character.isUpperCase(element.getName().charAt(0))) {
            warning("Name should start with a capital rrrr",
                    SmallJavaPackage.Literals.SJ_CLASS__NAME);
        }
    }

    public void AAA2(SJClass element) {
        if (!Character.isUpperCase(element.getName().charAt(0))) {
            warning("Name should start with a capital rrrr",
                    SmallJavaPackage.Literals.SJ_CLASS__NAME);
        }
    }

    @Check
    public void AAA3(String ste) {

    }


}
