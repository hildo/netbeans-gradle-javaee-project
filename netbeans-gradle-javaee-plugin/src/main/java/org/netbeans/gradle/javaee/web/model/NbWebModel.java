/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.model;

import org.gradle.api.Project;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public final class NbWebModel implements Serializable {

    private static final long serialVersionUID = 2209710889385730864L;
    private static final Logger LOGGER = Logger.getLogger(NbWebModel.class.getName());

    private String webAppDir;

    public void loadFromGradleProject(Project project) {
        Object o = project.getProperties().get("webAppDir");
        LOGGER.info("Loading NbWebModel from Project");
        LOGGER.info(o.getClass().getName());
        LOGGER.info(o.toString());
    }

    public String getWebAppDir() {
        return webAppDir;
    }
}
