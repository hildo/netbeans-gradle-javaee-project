/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.models.NbJpaModel;
import org.netbeans.gradle.javaee.jpa.verification.GradlePersistenceScopesProvider;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author ed
 */
public class JpaModuleExtension implements GradleProjectExtension2<NbJpaModel> {

    private static final Logger LOGGER = Logger.getLogger(JpaModuleExtension.class.getName());
    
    private final Project project; // NetBeans project
    private Lookup permanentProjectLookup;
    private Lookup projectLookup;
    private final Lookup extensionLookup = Lookups.fixed();

    private final AtomicReference<NbJpaModel> currentModelRef;

    public JpaModuleExtension(Project project) {
        this.project = project;
        this.currentModelRef = new AtomicReference<>(null);
    }

    /**
     * Returns the NetBeans <code>Project</code> for the extension
     * 
     * @return a Project object
     */
    public Project getProject() {
        return project;
    }
    
    public NbJpaModel getCurrentModel() {
        return currentModelRef.get();
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
            projectLookup = Lookups.fixed(
                new GradlePersistenceScopesProvider(this)
            );
        }
        return projectLookup;
    }

    @Override
    public Lookup getExtensionLookup() {
        return extensionLookup;
    }

    @Override
    public void activateExtension(NbJpaModel parsedModel) {
        if (parsedModel != null) {
            LOGGER.log(Level.INFO, "activating Jpa Extension with {0}", parsedModel.getPersistenceFile());
        }
        currentModelRef.getAndSet(parsedModel);
    }

    @Override
    public void deactivateExtension() {
        activateExtension(null);
    }
    
}