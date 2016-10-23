package org.netbeans.gradle.javaee.models;

import java.util.logging.Logger;

import org.gradle.api.Project;
import org.netbeans.gradle.model.api.ProjectInfoBuilder2;

/**
 *
 * @author Ed
 */
enum NbWebModelBuilder implements ProjectInfoBuilder2<NbWebModel>{
    INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(NbWebModelBuilder.class.getName());

    @Override
    public NbWebModel getProjectInfo(Object project) {
        return getProjectInfo((Project)project);
    }

    private NbWebModel getProjectInfo(Project project) {
        LOGGER.entering(this.getClass().getName(), "getProjectInfo", project);
        NbWebModel returnValue = null;
        if (project.getPlugins().hasPlugin("war")) {
            LOGGER.finer("Have WAR plugin");
            returnValue = NbWebModel.createModel(project);
        }
        LOGGER.exiting(this.getClass().getName(), "getProjectInfo", returnValue);
        return returnValue;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }
}
