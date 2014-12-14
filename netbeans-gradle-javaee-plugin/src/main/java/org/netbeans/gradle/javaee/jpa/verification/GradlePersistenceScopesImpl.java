/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.verification;

import java.beans.PropertyChangeListener;
import java.io.File;

import org.netbeans.gradle.javaee.jpa.JpaModuleExtension;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeFactory;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopesImplementation;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author ed
 */
public class GradlePersistenceScopesImpl implements PersistenceScopesImplementation {

    private static final PersistenceScope[] EMPTY = new PersistenceScope[0];
    
    private final JpaModuleExtension jpaModule;
    
    public GradlePersistenceScopesImpl(JpaModuleExtension jpaModule) {
        this.jpaModule = jpaModule;
    }
    
    private PersistenceScope[] constructScopes(JpaModuleExtension jpaModule) {
        if (jpaModule.getCurrentModel() == null) {
            return EMPTY;
        }
        PersistenceScope[] returnValue = new PersistenceScope[1];
        GradlePersistenceScopeImpl scope = new GradlePersistenceScopeImpl();
        scope.setPersistenceXml(
                FileUtil.toFileObject(
                        new File(jpaModule.getCurrentModel().getPersistenceFile())));
        returnValue[0] = PersistenceScopeFactory.createPersistenceScope(scope);
        return returnValue;
    }
    
    @Override
    public PersistenceScope[] getPersistenceScopes() {
        return constructScopes(jpaModule);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
    }
    
}
