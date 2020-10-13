package com.intellij.xtextLanguage.xtext.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Helper {
    public static <T> void esetMany(@NotNull EObject host, @NotNull EStructuralFeature feature, @NotNull T value) {
        assert feature.isMany();
        List erased = (List) host.eGet(feature);
        //noinspection unchecked
        erased.add(value);
    }

}
