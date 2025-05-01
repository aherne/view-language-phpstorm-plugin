package com.lucianpopescu.viewlanguage;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.XmlPatterns;
import com.intellij.util.ProcessingContext;
import com.intellij.psi.xml.XmlToken;
import com.intellij.psi.xml.XmlTokenType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class TaglibTagCompletionContributor extends CompletionContributor {
    public TaglibTagCompletionContributor() {
        extend(CompletionType.BASIC,
            XmlPatterns.psiElement(XmlTokenType.XML_NAME).withParent(XmlPatterns.xmlTag()),
            new CompletionProvider<CompletionParameters>() {
                @Override
                protected void addCompletions(@NotNull CompletionParameters parameters, 
                                              @NotNull ProcessingContext context, 
                                              @NotNull CompletionResultSet resultSet) {
                    Project project = parameters.getPosition().getProject();
                    VirtualFile root = project.getBaseDir();
                    if (root == null) {
                        return;
                    }

                    FrameworkDetector detector = new FrameworkDetector(project);                    
                    VirtualFile taglibRoot = root.findFileByRelativePath(detector.getTagsFolder());
                    if (taglibRoot == null) {
                        return;
                    }

                    addTagsFromFolder(taglibRoot, "", resultSet);
                }
            });
    }

    private void addTagsFromFolder(VirtualFile folder, String prefix, CompletionResultSet resultSet) {
        for (VirtualFile child : folder.getChildren()) {
            if (child.isDirectory()) {
                addTagsFromFolder(child, prefix.isEmpty() ? child.getName() : prefix + ":" + child.getName(), resultSet);
            } else if (child.getName().endsWith(".html")) {
                String tagName = child.getNameWithoutExtension();
                String suggestion = prefix.isEmpty() ? tagName : prefix + ":" + tagName;
                resultSet.addElement(LookupElementBuilder.create(suggestion));
            }
        }
    }
}
