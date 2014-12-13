/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.model;

import java.io.Serializable;
import java.util.logging.Logger;

import org.gradle.api.Project;

/**
 *
 * @author ed
 */
public final class NbJpaModel implements Serializable {
    
    private static final Logger LOGGER = Logger.getLogger(NbJpaModel.class.getName());
    private final String persistenceFile;
    
    public NbJpaModel(String persistenceFile) {
        this.persistenceFile = persistenceFile;
    }
    
    public String getPersistenceFile() {
        return persistenceFile;
    }

}
