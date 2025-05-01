package com.lucianpopescu.viewlanguage;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.XmlPatterns;
import com.intellij.util.ProcessingContext;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class ImportFileCompletionContributor extends CompletionContributor {
    public ImportFileCompletionContributor() {
        extend(CompletionType.BASIC,
            XmlPatterns.xmlAttributeValue().withSuperParent(2, 
                XmlPatterns.xmlAttribute().withName("file").withParent(
                    XmlPatterns.xmlTag().withName("import")
                )
            ),
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
                	VirtualFile viewsRoot = root.findFileByRelativePath(detector.getViewsFolder());
                    if (viewsRoot == null) {
                        return;
                    }

                    for (VirtualFile file : viewsRoot.getChildren()) {
                        if (!file.isDirectory() && file.getName().endsWith(".html")) {
                            resultSet.addElement(LookupElementBuilder.create(file.getNameWithoutExtension()));
                        }
                    }
                }
            });
    }
}
