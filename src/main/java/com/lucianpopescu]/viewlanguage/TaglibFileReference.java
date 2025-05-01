package com.lucianpopescu.viewlanguage;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TaglibFileReference extends PsiReferenceBase<PsiElement> implements PsiReference {

    private final String expectedPath;

    public TaglibFileReference(@NotNull PsiElement element, @NotNull String expectedPath) {
        super(element); // no TextRange trickery
        this.expectedPath = expectedPath;
    }

    @Override
    public @Nullable PsiElement resolve() {
        PsiFile containingFile = getElement().getContainingFile();
        if (containingFile == null) {
            return null;
        }

        VirtualFile root = containingFile.getProject().getBaseDir();
        if (root == null) {
            return null;
        }

        VirtualFile target = root.findFileByRelativePath(expectedPath);
        if (target == null) {
            return null;
        }

        return PsiManager.getInstance(getElement().getProject()).findFile(target);
    }
}

