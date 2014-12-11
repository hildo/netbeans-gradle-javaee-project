/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gradle.api.Project;
import org.netbeans.gradle.model.api.ProjectInfoBuilder;

/**
 *
 * @author ed
 */
public class NbJpaModelBuilder implements ProjectInfoBuilder<NbJpaModel>{
    
    private static final String NAME = "org.netbeans.gradle.javaee.jpa.model.NbJpaModelBuilder";
    private static final Logger LOGGER = Logger.getLogger(NbJpaModelBuilder.class.getName());
    
    public static final NbJpaModelBuilder INSTANCE = new NbJpaModelBuilder();
    
    @Override
    public NbJpaModel getProjectInfo(Project project) {
        LOGGER.log(Level.INFO, "getProjectInfo from this project: {0}", project);
        LOGGER.info(project.getProperties().toString());
        return null;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
}