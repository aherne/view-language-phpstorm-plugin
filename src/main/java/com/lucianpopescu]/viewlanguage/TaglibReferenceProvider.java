package com.lucianpopescu.viewlanguage;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class TaglibReferenceProvider extends PsiReferenceProvider {
@Override
	public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
	    if (!(element instanceof XmlTag)) {
	        return PsiReference.EMPTY_ARRAY;
	    }
	
	    XmlTag tag = (XmlTag) element;
	    String fullName = tag.getName(); // popup:add
	
	    if (!fullName.contains(":")) {
	        return PsiReference.EMPTY_ARRAY;
	    }
	
	    String[] parts = fullName.split(":");
	    if (parts.length != 2) {
	        return PsiReference.EMPTY_ARRAY;
	    }
	
	    String library = parts[0];
	    String name = parts[1];
	    
	    FrameworkDetector detector = new FrameworkDetector(element.getProject());
	    String expectedPath = detector.getTagsFolder()+"/" + library + "/" + name + ".html";
	
	    // The "tag name" is represented as the first or second child PsiElement
	    PsiElement[] children = tag.getChildren();
	
	    if (children.length == 0) {
	        return PsiReference.EMPTY_ARRAY;
	    }
	
	    // Find the child that contains the tag name text
	    PsiElement nameElement = null;
	    for (PsiElement child : children) {
	        if (child.getText().contains(":")) {
	            nameElement = child;
	            break;
	        }
	    }
	
	    if (nameElement == null) {
	        return PsiReference.EMPTY_ARRAY;
	    }
	
	    return new PsiReference[]{
	        new TaglibFileReference(nameElement, expectedPath)
	    };
	}

}

