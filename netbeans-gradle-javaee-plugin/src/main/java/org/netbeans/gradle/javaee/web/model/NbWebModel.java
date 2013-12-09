/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.model;

import org.gradle.api.Project;

import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Handy doco pages for this:
 * http://www.gradle.org/docs/current/userguide/war_plugin.html
 * http://www.gradle.org/docs/current/dsl/org.gradle.api.plugins.WarPluginConvention.html
 *
 * @author Ed
 */
public final class NbWebModel implements Serializable {

    private static final long serialVersionUID = 2209710889385730864L;
    private static final Logger LOGGER = Logger.getLogger(NbWebModel.class.getName());

    private String webAppDir;
    private String deploymentDescName = "web.xml";

    public void loadFromGradleProject(Project project) {
        LOGGER.entering(this.getClass().getName(), "loadFromGradleProject", project);
        webAppDir = (String) project.getProperties().get("webAppDirName"); // Use webAppDirName
        File deploymentDesc = (File) project.getProperties().get("webXml");
        if (deploymentDesc != null) {
            deploymentDescName = deploymentDesc.getName();
        }
        LOGGER.exiting(this.getClass().getName(), "loadFromGradleProject");
    }

    public String getWebAppDir() {
        return webAppDir;
    }

    public String getDeploymentDescName() {
        return deploymentDescName;
    }
}
