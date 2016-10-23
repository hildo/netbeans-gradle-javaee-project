/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.nodes;

import org.netbeans.gradle.javaee.web.WebModuleExtension;
import org.netbeans.gradle.project.api.event.NbListenerRef;
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
import org.netbeans.gradle.javaee.web.ModelReloadListener;
import org.netbeans.gradle.javaee.models.NbWebModel;
import org.netbeans.gradle.project.api.event.NbListenerRefs;
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
    public NbListenerRef addNodeChangeListener(final Runnable listener) {
        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                listener.run();
            }
        };

        nodeChanges.addChangeListener(changeListener);
        return NbListenerRefs.fromRunnable(new Runnable() {
            @Override
            public void run() {
                nodeChanges.removeChangeListener(changeListener);
            }
        });
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
            final DataFolder listedFolder = DataFolder.findFolder(webDir);
            LOGGER.log(Level.FINEST, "listedFolder = {0}", listedFolder);
            list.add(new SingleNodeFactory() {
                @Override
                public Node createNode() {
                    return new FilterNode(listedFolder.getNodeDelegate().cloneNode()) {
                        @Override
                        public String getDisplayName() {
                            return "Web Pages";
                        }
                    };
                }
            });
        }
    }
}
