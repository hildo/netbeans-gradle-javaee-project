package org.netbeans.gradle.javaee.models;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.plugins.WarPluginConvention;
import org.netbeans.gradle.model.api.ProjectInfoBuilder2;

/**
 *
 * @author Ed
 */
enum NbWebModelBuilder implements ProjectInfoBuilder2<NbWebModel> {
    INSTANCE;

    @Override
    public NbWebModel getProjectInfo(Object project) {
        return getProjectInfo((Project) project);
    }

    private NbWebModel getProjectInfo(Project project) {
        WarPluginConvention war = project.getConvention().findPlugin(WarPluginConvention.class);
        return war != null ? createModel(project, war) : null;
    }

    private NbWebModel createModel(Project project, WarPluginConvention war) {
        String webAppDirValue = war.getWebAppDirName();
        String ddValue = "web.xml";
        File deploymentDesc = (File) project.getProperties().get("webXml");
        if (deploymentDesc != null) {
            ddValue = deploymentDesc.getName();
        }
        return new NbWebModel(webAppDirValue, ddValue);
    }

    @Override
    public String getName() {
        return getClass().getName();
    }
}
