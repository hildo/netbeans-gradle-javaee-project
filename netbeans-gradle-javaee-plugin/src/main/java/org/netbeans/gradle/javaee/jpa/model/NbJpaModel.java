/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.model;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author ed
 */
public final class NbJpaModel implements Serializable {
    
    private final String persistenceFile;
    private final Iterable<File> javaSourceDirs;
    
    public NbJpaModel(String persistenceFile, Iterable<File> compileClasspath) {
        this.persistenceFile = persistenceFile;
        this.javaSourceDirs = serializableIterable(compileClasspath);
    }
    
    private Iterable<File> serializableIterable(Iterable<File> iterable) {
        java.util.ArrayList<File> returnValue = new java.util.ArrayList<>();
        for (File file : iterable) {
            returnValue.add(file);
        }
        return returnValue;
    }
    
    public String getPersistenceFile() {
        return persistenceFile;
    }

    public Iterable<File> getJavaSourceDirs() {
        return javaSourceDirs;
    }
}
