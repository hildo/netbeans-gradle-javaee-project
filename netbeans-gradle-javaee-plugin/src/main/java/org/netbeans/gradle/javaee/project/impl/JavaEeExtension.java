/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.project.impl;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.openide.util.Lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Ed
 */
public class JavaEeExtension implements GradleProjectExtension {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaEeExtension.class);

    private final Project project;

    public JavaEeExtension(Project project) {
        LOGGER.info("JavaEeExtension.Create -- slf4j");
        System.out.println("JavaEeExtension.Create -- sout");
        this.project = project;
    }

    @Override
    public String getExtensionName() {
        return "org.netbeans.gradle.project.javaee.JavaEeExtension";
    }

    @Override
    public Iterable<List<Class<?>>> getGradleModels() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lookup getExtensionLookup() {
        return null;
    }

    @Override
    public Set<String> modelsLoaded(Lookup modelLookup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<File, Lookup> deduceModelsForProjects(Lookup modelLookup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
