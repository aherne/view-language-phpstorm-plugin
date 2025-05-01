package com.lucianpopescu.viewlanguage;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

public class TaglibReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
            XmlPatterns.xmlTag().withName(PlatformPatterns.string().contains(":")),
            new TaglibReferenceProvider()
        );

        registrar.registerReferenceProvider(
            XmlPatterns.xmlAttributeValue().withParent(
                XmlPatterns.xmlAttribute().withName("file").withParent(
                    XmlPatterns.xmlTag().withName("import")
                )
            ),
            new ImportFileReferenceProvider()
        );
    }
}
