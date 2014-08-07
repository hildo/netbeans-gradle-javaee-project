/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.j2ee.core.Profile;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.gradle.javaee.web.model.NbWebModel;
import org.netbeans.modules.j2ee.dd.api.web.WebAppMetadata;
import org.netbeans.modules.j2ee.dd.spi.MetadataUnit;
import org.netbeans.modules.j2ee.dd.spi.web.WebAppMetadataModelFactory;
import org.netbeans.modules.j2ee.metadata.model.api.MetadataModel;
import org.netbeans.modules.web.spi.webmodule.WebModuleImplementation2;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public class GradleWebModuleImpl implements WebModuleImplementation2 {

    private static final Logger LOGGER = Logger.getLogger(GradleWebModuleImpl.class.getName());

    private final WebModuleExtension webExt;
    private FileObject documentBase;
    private FileObject webInf;
    private FileObject deploymentDescriptor;
    private FileObject[] javaSources;

    public GradleWebModuleImpl(WebModuleExtension webExt) {
        this.webExt = webExt;
        initialise();
    }

    private void initialise() {
        if (webExt == null) {
            return;
        }

        NbWebModel model = webExt.getCurrentModel();
        LOGGER.log(Level.FINER, "model = {0}", model);

        if (model == null) {
            return;
        }

        LOGGER.log(Level.FINER, "model.getWebAppDir() = {0}", model.getWebAppDir());
        documentBase = webExt.getProject().getProjectDirectory().getFileObject(model.getWebAppDir());
        if (documentBase != null) {
            webInf = documentBase.getFileObject("WEB-INF");
            LOGGER.log(Level.FINER, "webInf = {0}", webInf);
            LOGGER.log(Level.FINER, "model.getDeploymentDescName() = {0}", model.getDeploymentDescName());
            if (webInf != null) {
                deploymentDescriptor = webInf.getFileObject(model.getDeploymentDescName());
            }
            javaSources = getSourcesForNBProject(webExt.getProject());
        }
    }

    private FileObject[] getSourcesForNBProject(Project project) {
        SourceGroup[] groups = null;
        Sources sources = ProjectUtils.getSources(project);
        if (sources != null) {
            groups = sources.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
        }
        return constructSourceObject(documentBase, groups);
    }
    
    // Sources, plus documentBase
    private FileObject[] constructSourceObject(FileObject webAppDir, SourceGroup[] groups) {
        int sourcesLength = 0;
        if (webAppDir != null) {
            sourcesLength += 1;
        }
        if (groups != null) {
            sourcesLength += groups.length;
        }
        FileObject[] returnValue = new FileObject[sourcesLength];
        int idx = 0;
        if (groups != null) {
            for (SourceGroup group : groups) {
                returnValue[idx++] = group.getRootFolder();
            }
        }
        if (webAppDir != null) {
            returnValue[idx] = webAppDir;
        }
        return returnValue;
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
