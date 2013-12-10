/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.model;

import org.gradle.api.Project;
import org.netbeans.gradle.model.api.ProjectInfoBuilder;

import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public class NbWebModelBuilder implements ProjectInfoBuilder<NbWebModel>{

    private static final String NAME = "org.netbeans.gradle.javaee.web.model.NbWebModelBuilder";
    private static final long serialVersionUID = 8513886793993792394L;
    private static final Logger LOGGER = Logger.getLogger(NbWebModelBuilder.class.getName());

    public static final NbWebModelBuilder INSTANCE = new NbWebModelBuilder();

    @Override
    public NbWebModel getProjectInfo(Project project) {
        LOGGER.entering(this.getClass().getName(), "getProjectInfo", project);
        NbWebModel returnValue = null;
        if (project.getPlugins().hasPlugin("war")) {
            LOGGER.info("Have WAR plugin");
            returnValue = NbWebModel.createModel(project);
        }
        LOGGER.exiting(this.getClass().getName(), "getProjectInfo", returnValue);
        return returnValue;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
