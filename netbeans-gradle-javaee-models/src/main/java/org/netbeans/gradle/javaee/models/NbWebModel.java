/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.models;

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

    private final String webAppDir;
    private final String deploymentDescName;

    public NbWebModel(String webAppDir, String deploymentDescName) {
        this.webAppDir = webAppDir;
        this.deploymentDescName = deploymentDescName;
    }

    public String getWebAppDir() {
        return webAppDir;
    }

    public String getDeploymentDescName() {
        return deploymentDescName;
    }
}
