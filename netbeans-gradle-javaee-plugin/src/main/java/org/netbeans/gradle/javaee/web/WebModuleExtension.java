/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.web.beans.GradleCdiUtil;
import org.netbeans.gradle.javaee.web.model.NbWebModel;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
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
    private NbWebModel currentModel; // NetBeans Gradle Web Model

    public WebModuleExtension(Project project) {
        this.project = project;
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
                new GradleCdiUtil(this)
            );
        }
        return projectLookup;
    }

    @Override
    public synchronized Lookup getExtensionLookup() {
        if (extensionLookup == null) {
            extensionLookup = Lookups.fixed(this);
        }
        return extensionLookup;
    }

    @Override
    public void activateExtension(NbWebModel parsedModel) {
        currentModel = parsedModel;
    }

    @Override
    public void deactivateExtension() {
        currentModel = null;
    }

    public NbWebModel getCurrentModel() {
        return currentModel;
    }

    public Project getProject() {
        return project;
    }
}
