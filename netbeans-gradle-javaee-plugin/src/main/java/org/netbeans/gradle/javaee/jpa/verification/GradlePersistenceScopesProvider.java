/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.verification;

import org.netbeans.gradle.javaee.jpa.JpaModuleExtension;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScopes;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopesFactory;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopesProvider;

/**
 *
 * @author ed
 */
public class GradlePersistenceScopesProvider implements PersistenceScopesProvider {

    private final GradlePersistenceScopesImpl persistenceScopesImpl;
    
    public GradlePersistenceScopesProvider(JpaModuleExtension jpaModule) {
        this.persistenceScopesImpl = new GradlePersistenceScopesImpl(jpaModule);
    }
    
    @Override
    public PersistenceScopes getPersistenceScopes() {
        return PersistenceScopesFactory.createPersistenceScopes(persistenceScopesImpl);
    }
    
}
