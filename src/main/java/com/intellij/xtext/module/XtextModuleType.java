package com.intellij.xtext.module;

import com.intellij.openapi.module.ModuleType;
import com.intellij.xtext.language.XtextIcon;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class XtextModuleType extends ModuleType<XtextModuleBuilder> {
    private static final XtextModuleType INSTANCE = new XtextModuleType();

    public XtextModuleType() {
        super("XTEXT_MODULE");
    }


    @Override
    public @NotNull
    XtextModuleBuilder createModuleBuilder() {
        return new XtextModuleBuilder();
    }

    @Override
    public @NotNull
    @Nls(capitalization = Nls.Capitalization.Title)
    String getName() {
        return "Xtext";
    }

    @Override
    public @NotNull
    @Nls(capitalization = Nls.Capitalization.Sentence)
    String getDescription() {
        return "Support for Xtext";
    }

    @Override
    public @NotNull
    Icon getNodeIcon(boolean isOpened) {
        return XtextIcon.FILE;
    }


}