/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.netbeans.gradle.project.api.entry.GradleProjectExtensionQuery;
import org.openide.filesystems.FileObject;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
@ServiceProvider(service = GradleProjectExtensionQuery.class, position = 800)
public class WebModuleExtensionQuery implements GradleProjectExtensionQuery {

    private static final Logger LOGGER = Logger.getLogger(WebModuleExtensionQuery.class.getName());
    private static final String WEBAPP_LOCATION = "src/main/webapp";

    @Override
    public GradleProjectExtension loadExtensionForProject(Project project) throws IOException{
        GradleProjectExtension returnValue = null;
        if (isWebProject(project)) {
            LOGGER.log(Level.FINE, "Found Web project: {0}", project.getProjectDirectory().getName());
            returnValue = new WebModuleExtension(project);
        } else {
            LOGGER.log(Level.FINER, "{0} is not a Web project", project.getProjectDirectory().getName());
        }
        return returnValue;
    }

    private boolean isWebProject(Project project) {
        // TODO It would be nice to use the Gradle config to glean this.  For now, assume standard location
        FileObject webappDir = project.getProjectDirectory().getFileObject(WEBAPP_LOCATION);
        return (webappDir != null);
    }
}
