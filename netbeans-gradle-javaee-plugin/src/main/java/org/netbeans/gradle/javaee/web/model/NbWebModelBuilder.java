/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.model;

import org.gradle.api.Project;
import org.netbeans.gradle.model.api.ProjectInfoBuilder;

/**
 *
 * @author Ed
 */
public class NbWebModelBuilder implements ProjectInfoBuilder<NbWebModel>{

    private static final String NAME = "org.netbeans.gradle.javaee.web.model.NbWebModelBuilder";
    private static final long serialVersionUID = 8513886793993792394L;

    public static final NbWebModelBuilder INSTANCE = new NbWebModelBuilder();

    @Override
    public NbWebModel getProjectInfo(Project project) {
        NbWebModel returnValue = null;
        if (project.getPlugins().hasPlugin("war")) {
            returnValue = new NbWebModel();
            returnValue.loadFromGradleProject(project);
        }
        return returnValue;
    }

    @Override
    public String getName() {
        return NAME;
    }

}
