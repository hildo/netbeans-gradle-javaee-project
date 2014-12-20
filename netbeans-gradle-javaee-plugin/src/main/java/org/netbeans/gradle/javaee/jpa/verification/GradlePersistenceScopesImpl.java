/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.verification;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.List;

import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.gradle.javaee.jpa.JpaModuleExtension;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeFactory;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopesImplementation;
import org.netbeans.spi.java.classpath.support.ClassPathSupport;
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
        scope.setClassPath(buildClasspath(jpaModule.getCurrentModel().getJavaSourceDirs()));
        returnValue[0] = PersistenceScopeFactory.createPersistenceScope(scope);
        return returnValue;
    }
    
    private ClassPath buildClasspath(Iterable<File> classpath) {
        List<URL> pathList = new java.util.ArrayList<>();
        for (File classpathFile: classpath) {
            pathList.add(FileUtil.urlForArchiveOrDir(classpathFile));
        }
        URL[] files = pathList.toArray(new URL[pathList.size()]);
        return ClassPathSupport.createClassPath(files);
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
