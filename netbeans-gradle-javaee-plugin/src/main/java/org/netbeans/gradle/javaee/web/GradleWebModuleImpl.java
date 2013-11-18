/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.j2ee.core.Profile;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.project.Project;
import org.netbeans.modules.j2ee.dd.api.web.WebAppMetadata;
import org.netbeans.modules.j2ee.dd.spi.MetadataUnit;
import org.netbeans.modules.j2ee.dd.spi.web.WebAppMetadataModelFactory;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.web.spi.webmodule.WebModuleImplementation2;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import java.beans.PropertyChangeListener;

/**
 *
 * @author Ed
 */
public class GradleWebModuleImpl implements WebModuleImplementation2 {

    private final Project project;
    private FileObject documentBase;
    private FileObject webInf;
    private FileObject deploymentDescriptor;
    private FileObject[] javaSources;

    public GradleWebModuleImpl(Project project) {
        this.project = project;
        initialise();
    }

    private void initialise() {
        documentBase = project.getProjectDirectory().getFileObject("src/main/webapp");
        webInf = documentBase.getFileObject("WEB-INF");
        deploymentDescriptor = webInf.getFileObject("web.xml");
        FileObject javaSource = documentBase.getFileObject("src/man/java");
        javaSources = new FileObject[] { javaSource };
    }

    @Override
    public FileObject getDocumentBase() {
        return documentBase;
    }

    @Override
    public String getContextPath() {
        /*
        TODO Returns the context path of the web module.
        // read from glassfish-web.xml?
        */
        return "/";
    }

    @Override
    public Profile getJ2eeProfile() {
        return Profile.JAVA_EE_6_WEB;
    }

    @Override
    public FileObject getWebInf() {
        return webInf;
    }

    @Override
    public FileObject getDeploymentDescriptor() {
        return deploymentDescriptor;
    }

    @Override
    public FileObject[] getJavaSources() {
        return javaSources;
    }

    @Override
    public MetadataModel<WebAppMetadata> getMetadataModel() {
        // TODO get classpaths from Gradle?
        MetadataUnit metadataUnit = MetadataUnit.create(ClassPath.EMPTY, ClassPath.EMPTY, ClassPath.EMPTY,
                FileUtil.toFile(deploymentDescriptor));
        return WebAppMetadataModelFactory.createMetadataModel(metadataUnit, true);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
    }

}
