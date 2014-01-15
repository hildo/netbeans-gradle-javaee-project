/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.nodes;

import org.netbeans.gradle.javaee.web.WebModuleExtension;
import org.netbeans.gradle.javaee.web.WebModuleExtensionDef;
import org.netbeans.gradle.project.api.event.NbListenerRef;
import org.netbeans.gradle.project.api.nodes.GradleProjectExtensionNodes;
import org.netbeans.gradle.project.api.nodes.SingleNodeFactory;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataFolder;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public class WebModuleExtensionNodes implements GradleProjectExtensionNodes {

    private static final Logger LOGGER = Logger.getLogger(WebModuleExtensionNodes.class.getName());

    private final WebModuleExtension webModule;

    public WebModuleExtensionNodes(WebModuleExtension webModule) {
        this.webModule = webModule;
    }

    @Override
    public NbListenerRef addNodeChangeListener(Runnable r) {
        return null;
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
