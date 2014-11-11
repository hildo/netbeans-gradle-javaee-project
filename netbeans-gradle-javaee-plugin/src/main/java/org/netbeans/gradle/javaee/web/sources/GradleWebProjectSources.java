/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.web.sources;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;

import javax.swing.Icon;
import javax.swing.event.ChangeListener;

import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.gradle.javaee.web.WebModuleExtension;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author ed
 */
public class GradleWebProjectSources implements Sources {
    
    private static final String DOC_ROOT = "doc_root";

    private final WebModuleExtension webExt;

    public GradleWebProjectSources(WebModuleExtension webExt) {
        this.webExt = webExt;
    }

    @Override
    public SourceGroup[] getSourceGroups(String sourceGroupType) {
        if (DOC_ROOT.equals(sourceGroupType)) {
            return new SourceGroup[]{new GradleSourceGroup(webExt.getWebDir(), "Web Resources [doc_root]")};
        } else {
            return new SourceGroup[0];
        }
    }

    @Override
    public void addChangeListener(ChangeListener cl) {
        // TODO
    }

    @Override
    public void removeChangeListener(ChangeListener cl) {
        // TODO
    }

    private static class GradleSourceGroup implements SourceGroup {

        private final FileObject location;
        private final PropertyChangeSupport changes;
        private final String displayName;

        public GradleSourceGroup(FileObject location, String displayName) {
            this.location = location;
            this.displayName = displayName;
            this.changes = new PropertyChangeSupport(this);
        }

        @Override
        public FileObject getRootFolder() {
            return location;
        }

        @Override
        public String getName() {
            String locationStr = location.getPath();
            return locationStr.length() > 0 ? locationStr : "generic";
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }

        @Override
        public Icon getIcon(boolean opened) {
            return null;
        }

        @Override
        public boolean contains(FileObject file) {
            if (file == location) {
                return true;
            }

            if (FileUtil.getRelativePath(location, file) == null) {
                return false;
            }

            URI f = file.toURI();

            // else MIXED, UNKNOWN, or SHARABLE; or not a disk file
//            return f == null || SharabilityQuery.getSharability(f) != SharabilityQuery.Sharability.NOT_SHARABLE;
            return f == null;
        }

        @Override
        public void addPropertyChangeListener(PropertyChangeListener l) {
            changes.addPropertyChangeListener(l);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener l) {
            changes.removePropertyChangeListener(l);
        }

        @Override
        public String toString() {
            return "GradleWebProjectSources.Group[name=" + getName() + ",rootFolder=" + getRootFolder() + "]";
        }
    }

}
