package com.lucianpopescu.viewlanguage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class FrameworkDetector {
	private String viewsFolder;
	private String tagsFolder;
	
    public FrameworkDetector(Project project) {
        boolean isNew = this.isNew(project);
        this.setViewsFolder(isNew);
        this.setTagsFolder(isNew);
    }

    private boolean isNew(Project project) {
        VirtualFile root = project.getBaseDir();
        if (root == null) {
            return true; // Very defensive: assume new if we cannot even find root
        }
        VirtualFile applicationFolder = root.findChild("application");
        return (applicationFolder == null || !applicationFolder.isDirectory());
    }

    
    private void setViewsFolder(boolean isNew) {
        this.viewsFolder = isNew ? "templates/views" : "application/views";
    }
    
    public String getViewsFolder()
    {
    	return this.viewsFolder;
    }

    private void setTagsFolder(boolean isNew) {
        this.tagsFolder = isNew ? "templates/tags" : "application/taglib";
    }
    
    public String getTagsFolder()
    {
    	return this.tagsFolder;
    }
}