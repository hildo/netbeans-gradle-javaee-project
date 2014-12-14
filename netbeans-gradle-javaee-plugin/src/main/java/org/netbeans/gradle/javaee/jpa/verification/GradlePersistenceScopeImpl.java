/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.verification;

import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.j2ee.persistence.api.metadata.orm.EntityMappingsMetadata;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeImplementation;
import org.openide.filesystems.FileObject;

/**
 *
 * @author ed
 */
public class GradlePersistenceScopeImpl implements PersistenceScopeImplementation {

    private FileObject persistenceXml;
    
    public void setPersistenceXml(FileObject persistenceXml) {
        this.persistenceXml = persistenceXml;
    }
    
    @Override
    public FileObject getPersistenceXml() {
        return persistenceXml;
    }

    @Override
    public ClassPath getClassPath() {
        return ClassPath.EMPTY;
    }

    @Override
    public MetadataModel<EntityMappingsMetadata> getEntityMappingsModel(String string) {
        return null;
    }
    
}
