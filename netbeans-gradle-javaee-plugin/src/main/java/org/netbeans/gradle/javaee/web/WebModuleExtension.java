/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import java.util.concurrent.atomic.AtomicReference;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.web.model.NbWebModel;
import org.netbeans.gradle.javaee.web.nodes.WebModuleExtensionNodes;
import org.netbeans.gradle.javaee.web.sources.GradleWebProjectSources;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
import org.netbeans.modules.web.beans.WebCdiUtil;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Ed
 */
public class WebModuleExtension implements GradleProjectExtension2<NbWebModel> {

    private final Project project; // NetBeans project
    private Lookup permanentProjectLookup;
    private Lookup projectLookup;
    private Lookup extensionLookup;

    private final AtomicReference<NbWebModel> currentModelRef;

    public WebModuleExtension(Project project) {
        this.project = project;
        this.currentModelRef = new AtomicReference<>(null);
    }

    @Override
    public synchronized Lookup getPermanentProjectLookup() {
        if (permanentProjectLookup == null) {
            permanentProjectLookup = Lookups.fixed(this);
        }
        return permanentProjectLookup;
    }

    @Override
    public synchronized Lookup getProjectLookup() {
        if (projectLookup == null) {
            projectLookup = Lookups.fixed(
                new GradleWebModuleProvider(this),
                new WebCdiUtil(project),
                new GradleWebProjectSources(this)
            );
        }
        return projectLookup;
    }

    @Override
    public synchronized Lookup getExtensionLookup() {
        if (extensionLookup == null) {
            extensionLookup = Lookups.fixed(
                    new WebModuleExtensionNodes(this)
            );
        }
        return extensionLookup;
    }

    @Override
    public void activateExtension(NbWebModel parsedModel) {
        NbWebModel prevModel = currentModelRef.getAndSet(parsedModel);
        for (ModelReloadListener listener: getExtensionLookup().lookupAll(ModelReloadListener.class)) {
            listener.onModelChange(prevModel, parsedModel);
        }
    }

    @Override
    public void deactivateExtension() {
        activateExtension(null);
    }

    public NbWebModel getCurrentModel() {
        return currentModelRef.get();
    }

    public Project getProject() {
        return project;
    }

    public FileObject getWebDir() {
        NbWebModel model = getCurrentModel();
        return model != null
                ? project.getProjectDirectory().getFileObject(model.getWebAppDir())
                : null;
    }

}
