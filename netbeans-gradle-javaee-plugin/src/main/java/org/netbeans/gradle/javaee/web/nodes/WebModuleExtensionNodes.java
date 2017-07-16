/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.nodes;

import org.netbeans.gradle.javaee.web.WebModuleExtension;
import org.netbeans.gradle.project.api.nodes.GradleProjectExtensionNodes;
import org.netbeans.gradle.project.api.nodes.ManualRefreshedNodes;
import org.netbeans.gradle.project.api.nodes.SingleNodeFactory;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jtrim2.concurrent.Tasks;
import org.jtrim2.event.ListenerRef;
import org.netbeans.gradle.javaee.web.ModelReloadListener;
import org.netbeans.gradle.javaee.models.NbWebModel;
import org.openide.util.ChangeSupport;

/**
 *
 * @author Ed
 */
@ManualRefreshedNodes
public class WebModuleExtensionNodes implements GradleProjectExtensionNodes, ModelReloadListener {

    private static final Logger LOGGER = Logger.getLogger(WebModuleExtensionNodes.class.getName());

    private final WebModuleExtension webModule;
    private final ChangeSupport nodeChanges;

    public WebModuleExtensionNodes(WebModuleExtension webModule) {
        this.webModule = webModule;
        this.nodeChanges = new ChangeSupport(this);
    }

    private static boolean hasChanged(NbWebModel prevModel, NbWebModel newModel) {
        if (prevModel == newModel) {
            return false;
        }

        if (prevModel == null || newModel == null) {
            return true;
        }

        return !Objects.equals(prevModel.getWebAppDir(), newModel.getWebAppDir());
    }

    @Override
    public void onModelChange(NbWebModel prevModel, NbWebModel newModel) {
        if (hasChanged(prevModel, newModel)) {
            nodeChanges.fireChange();
        }
    }

    @Override
    public ListenerRef addNodeChangeListener(final Runnable listener) {
        ChangeListener changeListener = (ChangeEvent e) -> listener.run();

        nodeChanges.addChangeListener(changeListener);
        return Tasks.runOnceTask(() -> {
            nodeChanges.removeChangeListener(changeListener);
        })::run;
    }

    @Override
    public List<SingleNodeFactory> getNodeFactories() {
        List<SingleNodeFactory> returnValue = new java.util.LinkedList<>();
        addWebDir(returnValue);
        return returnValue;
    }

    private void addWebDir(List<SingleNodeFactory> list) {
        FileObject webDir = webModule.getWebDir();
        LOGGER.log(Level.FINEST, "webDir = {0}", webDir);
        if (webDir != null) {
            list.add(new WebDirNodeFactory(webDir));
        }
    }

    private static class WebDirNodeFactory implements SingleNodeFactory {
        private final FileObject webDir;
        private final DataFolder listedFolder;

        public WebDirNodeFactory(FileObject webDir) {
            this.webDir = webDir;
            this.listedFolder = DataFolder.findFolder(webDir);
        }

        @Override
        public Node createNode() {
            return new FilterNode(listedFolder.getNodeDelegate().cloneNode()) {
                @Override
                public String getDisplayName() {
                    return "Web Pages";
                }
            };
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 37 * hash + Objects.hashCode(webDir);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;

            final WebDirNodeFactory other = (WebDirNodeFactory) obj;
            return Objects.equals(this.webDir, other.webDir);
        }
    }
}
