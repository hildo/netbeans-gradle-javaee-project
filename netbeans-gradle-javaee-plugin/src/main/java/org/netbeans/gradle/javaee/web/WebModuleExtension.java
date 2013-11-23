/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.web.beans.GradleCdiUtil;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ed
 */
public class WebModuleExtension implements GradleProjectExtension {

    private final Project project;
    private final Lookup extensionLookup;

    public WebModuleExtension(Project project) {
        this.project = project;
        extensionLookup = Lookups.fixed(
            new GradleWebModuleProvider(project),
            new GradleCdiUtil(project)
        );
    }

    @Override
    public String getExtensionName() {
        return "org.netbeans.gradle.project.javaee.WebModuleExtension";
    }

    @Override
    public Iterable<List<Class<?>>> getGradleModels() {
        return Collections.emptyList();
    }

    @Override
    public Lookup getExtensionLookup() {
        return extensionLookup;
    }

    @Override
    public Set<String> modelsLoaded(Lookup modelLookup) {
        return Collections.emptySet();
    }

    @Override
    public Map<File, Lookup> deduceModelsForProjects(Lookup modelLookup) {
        return Collections.emptyMap();
    }

}
