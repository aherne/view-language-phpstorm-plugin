package com.lucianpopescu.viewlanguage;

import com.intellij.psi.*;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlAttributeValue;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class ImportFileReferenceProvider extends PsiReferenceProvider {
    @Override
    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof XmlAttributeValue)) {
            return PsiReference.EMPTY_ARRAY;
        }

        XmlAttributeValue value = (XmlAttributeValue) element;
        PsiElement parent = value.getParent();
        if (!(parent instanceof XmlAttribute)) {
            return PsiReference.EMPTY_ARRAY;
        }

        XmlAttribute attribute = (XmlAttribute) parent;
        if (!"file".equals(attribute.getName())) {
            return PsiReference.EMPTY_ARRAY;
        }

        XmlTag tag = attribute.getParent();
        if (tag == null || !"import".equals(tag.getName())) {
            return PsiReference.EMPTY_ARRAY;
        }

        String fileName = value.getValue(); // no quotes
        if (fileName.isEmpty()) {
            return PsiReference.EMPTY_ARRAY;
        }

        FrameworkDetector detector = new FrameworkDetector(element.getProject());
        String expectedPath = detector.getViewsFolder()+"/" + fileName + ".html";

        return new PsiReference[]{
            new TaglibFileReference(value, expectedPath)
        };
    }
}
