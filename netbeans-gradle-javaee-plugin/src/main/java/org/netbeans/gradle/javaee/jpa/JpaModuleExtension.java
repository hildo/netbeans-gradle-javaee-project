/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa;

import java.util.concurrent.atomic.AtomicReference;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.jpa.model.NbJpaModel;
import org.netbeans.gradle.javaee.web.ModelReloadListener;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author ed
 */
public class JpaModuleExtension implements GradleProjectExtension2<NbJpaModel> {

    private final Project project; // NetBeans project
    private Lookup permanentProjectLookup;
    private Lookup projectLookup;
    private Lookup extensionLookup;

    private final AtomicReference<NbJpaModel> currentModelRef;

    public JpaModuleExtension(Project project) {
        this.project = project;
        this.currentModelRef = new AtomicReference<>(null);
    }

    @Override
    public Lookup getPermanentProjectLookup() {
        if (permanentProjectLookup == null) {
            permanentProjectLookup = Lookups.fixed(this);
        }
        return permanentProjectLookup;
    }

    @Override
    public Lookup getProjectLookup() {
        if (projectLookup == null) {
//            projectLookup = Lookups.fixed(
//                new GradleWebModuleProvider(this),
//                new WebCdiUtil(project),
//                new GradleWebProjectSources(this)
//            );
        }
        return projectLookup;
    }

    @Override
    public Lookup getExtensionLookup() {
        return null;
    }

    @Override
    public void activateExtension(NbJpaModel parsedModel) {
        NbJpaModel prevModel = currentModelRef.getAndSet(parsedModel);
//        for (ModelReloadListener listener: getExtensionLookup().lookupAll(ModelReloadListener.class)) {
//            listener.onModelChange(prevModel, parsedModel);
//        }
    }

    @Override
    public void deactivateExtension() {
        activateExtension(null);
    }
    
}