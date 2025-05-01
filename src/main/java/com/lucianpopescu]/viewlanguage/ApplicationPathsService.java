package com.lucianpopescu.viewlanguage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ApplicationPathsService {
    private static String taglibPath = "application/taglib";
    private static String viewsPath = "application/views";

    public static void initialize(Project project) {
        VirtualFile xmlFile = project.getBaseDir().findFileByRelativePath("xml/application_stdout.xml");
        if (xmlFile != null) {
            try {
                String content = new String(xmlFile.contentsToByteArray(), StandardCharsets.UTF_8);
                taglibPath = extractPath(content, "tags", taglibPath);
                viewsPath = extractPath(content, "views", viewsPath);
            } catch (IOException ignored) {
            }
        }
    }

    private static String extractPath(String xmlContent, String tag, String defaultPath) {
        Pattern pattern = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">");
        Matcher matcher = pattern.matcher(xmlContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return defaultPath;
    }

    public static String getTaglibPath() {
        return taglibPath;
    }

    public static String getViewsPath() {
        return viewsPath;
    }
}

