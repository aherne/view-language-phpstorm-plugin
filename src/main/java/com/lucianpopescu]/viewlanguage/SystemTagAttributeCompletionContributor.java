package com.lucianpopescu.viewlanguage;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class SystemTagAttributeCompletionContributor extends CompletionContributor {

    private static final Map<String, List<String>> SYSTEM_TAG_ATTRIBUTES = Map.of(
        "for", List.of("var", "start", "end", "step"),
        "foreach", List.of("var", "key", "val"),
        "if", List.of("test"),
        "elseif", List.of("test"),
        "set", List.of("var", "val"),
        "unset", List.of("var"),
        "while", List.of("test")
        // Note: else, break, continue don't have attributes
    );

    public SystemTagAttributeCompletionContributor() {
        extend(CompletionType.BASIC,
            XmlPatterns.xmlAttribute().withParent(XmlPatterns.xmlTag()),
            new CompletionProvider<CompletionParameters>() {
                @Override
                protected void addCompletions(@NotNull CompletionParameters parameters,
                                              @NotNull ProcessingContext context,
                                              @NotNull CompletionResultSet resultSet) {
                    XmlAttribute attribute = (XmlAttribute) parameters.getPosition().getParent();
                    if (attribute == null) {
                        return;
                    }

                    XmlTag tag = attribute.getParent();
                    if (tag == null) {
                        return;
                    }

                    String tagName = tag.getName();
                    if (!tagName.startsWith(":")) {
                        return;
                    }

                    String systemTagName = tagName.substring(1); // Remove colon

                    List<String> attrs = SYSTEM_TAG_ATTRIBUTES.get(systemTagName);
                    if (attrs == null) {
                        return;
                    }

                    for (String attr : attrs) {
                        resultSet.addElement(LookupElementBuilder.create(attr));
                    }
                }
            }
        );
    }
}
