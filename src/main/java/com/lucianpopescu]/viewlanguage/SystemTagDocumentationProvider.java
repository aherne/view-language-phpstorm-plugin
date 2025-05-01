package com.lucianpopescu.viewlanguage;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.Nullable;

public class SystemTagDocumentationProvider extends AbstractDocumentationProvider {

    @Override
    public @Nullable String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (!(element instanceof XmlTag)) {
            return null;
        }

        XmlTag tag = (XmlTag) element;
        String tagName = tag.getName();
        if (!tagName.startsWith(":")) {
            return null;
        }

        String systemTag = tagName.substring(1);

        // You can customize this further, like showing attributes
        switch (systemTag) {
            case "for":
                return "<b>System Tag: for</b><br>Creates a numeric for loop.<br><br>" +
                       "<b>Attributes:</b><br>" +
                       "<ul>" +
                       "<li><b>var</b>: Counter variable</li>" +
                       "<li><b>start</b>: Begin counter (integer)</li>" +
                       "<li><b>end</b>: End counter (integer)</li>" +
                       "<li><b>step</b>: Step increment (integer, optional)</li>" +
                       "</ul>";
            case "foreach":
                return "<b>System Tag: foreach</b><br>Iterates a dictionary by key and value.<br><br>" +
		               "<b>Attributes:</b><br>" +
		               "<ul>" +
		               "<li><b>var</b>: Variable to iterate</li>" +
		               "<li><b>key</b>: Dictionary key (optional)</li>" +
		               "<li><b>val</b>: Dictionary value</li>" +
		               "</ul>";
            case "if":
                return "<b>System Tag: if</b><br>Evaluates a condition.<br><br>"+
		               "<b>Attributes:</b><br>" +
		               "<ul>" +
		               "<li><b>test</b>: PHP/ViewLanguage condition</li>" +
		               "</ul>";
            case "elseif":
                return "<b>System Tag: elseif</b><br>Else-if condition after an if.<br><br>" +
 		               "<b>Attributes:</b><br>" +
 		               "<ul>" +
 		               "<li><b>test</b>: PHP/ViewLanguage condition</li>" +
 		               "</ul>";
            case "else":
                return "<b>System Tag: else</b><br>Fallback body if no if/elseif matched.";
            case "set":
                return "<b>System Tag: set</b><br>Sets a variable.<br><br>" +
	               "<b>Attributes:</b><br>" +
	               "<ul>" +
	               "<li><b>var</b>: Variable to set</li>" +
	               "<li><b>val</b>: PHP/ViewLanguage value</li>" +
	               "</ul>";
            case "unset":
                return "<b>System Tag: unset</b><br>Unsets a variable.";
            case "while":
                return "<b>System Tag: while</b><br>Repeats body while a condition is true.<br><br>" +
	               "<b>Attributes:</b><br>" +
	               "<ul>" +
	               "<li><b>test</b>: PHP/ViewLanguage condition</li>" +
	               "</ul>";
            case "break":
                return "<b>System Tag: break</b><br>Ends the current loop.";
            case "continue":
                return "<b>System Tag: continue</b><br>Skips rest of current loop iteration.";
            default:
                return null;
        }
    }
}
