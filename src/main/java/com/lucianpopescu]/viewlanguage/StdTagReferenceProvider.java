package com.lucianpopescu.viewlanguage;

import com.intellij.psi.*;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class StdTagReferenceProvider extends PsiReferenceProvider {
    @Override
    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof XmlTag)) {
            return PsiReference.EMPTY_ARRAY;
        }

        XmlTag tag = (XmlTag) element;
        String fullName = tag.getName(); // e.g., ":foreach"

        if (!fullName.startsWith(":")) {
            return PsiReference.EMPTY_ARRAY;
        }

        String localName = fullName.substring(1);

        if (!isStdTag(localName)) {
            return PsiReference.EMPTY_ARRAY;
        }

        String className = "Std" + capitalizeFirstLetter(localName) + "Tag.php";
        String expectedPath = "vendor/lucinda/view-language/src/taglib/Std/" + className;

        XmlToken nameToken = findNameToken(tag);
        if (nameToken == null) {
            return PsiReference.EMPTY_ARRAY;
        }

        return new PsiReference[]{
            new TaglibFileReference(nameToken, expectedPath)
        };
    }

    private boolean isStdTag(String tag) {
        switch (tag) {
            case "for":
            case "foreach":
            case "if":
            case "elseif":
            case "else":
            case "set":
            case "unset":
            case "while":
            case "break":
            case "continue":
                return true;
            default:
                return false;
        }
    }

    private String capitalizeFirstLetter(String text) {
        if (text == null || text.isEmpty()) return text;
        if (text.length() == 1) return text.toUpperCase();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private XmlToken findNameToken(XmlTag tag) {
        for (PsiElement child : tag.getChildren()) {
            if (child instanceof XmlToken) {
                XmlToken token = (XmlToken) child;
                if (token.getTokenType() == XmlTokenType.XML_NAME) {
                    return token;
                }
            }
        }
        return null;
    }
}
