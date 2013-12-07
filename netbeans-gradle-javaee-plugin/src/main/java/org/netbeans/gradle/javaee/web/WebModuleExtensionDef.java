/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.web.model.NbWebModel;
import org.netbeans.gradle.javaee.web.model.NbWebModelBuilder;
import org.netbeans.gradle.model.api.GradleProjectInfoQuery;
import org.netbeans.gradle.model.api.ModelClassPathDef;
import org.netbeans.gradle.model.api.ProjectInfoBuilder;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
import org.netbeans.gradle.project.api.entry.GradleProjectExtensionDef;
import org.netbeans.gradle.project.api.entry.ModelLoadResult;
import org.netbeans.gradle.project.api.entry.ParsedModel;
import org.netbeans.gradle.project.api.modelquery.GradleModelDef;
import org.netbeans.gradle.project.api.modelquery.GradleModelDefQuery2;
import org.netbeans.gradle.project.api.modelquery.GradleTarget;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
@ServiceProvider(service = GradleProjectExtensionDef.class, position = 800)
public class WebModuleExtensionDef implements GradleProjectExtensionDef<NbWebModel> {

    private static final Logger LOGGER = Logger.getLogger(WebModuleExtensionDef.class.getName());
    private static final String EXTENSION_NAME = "org.netbeans.gradle.javaee.web.WebModuleExtensionDef";

    private final Lookup extensionLookup;

    public WebModuleExtensionDef() {
        extensionLookup = Lookups.singleton(new Query2());
    }

    @Override
    public String getName() {
        return EXTENSION_NAME;
    }

    @Override
    public String getDisplayName() {
        return "JavaEE Web";
    }

    @Override
    public Lookup getLookup() {
        return extensionLookup;
    }

    @Override
    public Class<NbWebModel> getModelType() {
        return NbWebModel.class;
    }

    @Override
    public ParsedModel<NbWebModel> parseModel(ModelLoadResult retrievedModels) {
        LOGGER.info("in parseModel");
        LOGGER.info(retrievedModels.getMainProjectDir().toString());
        return null;
    }

    @Override
    public GradleProjectExtension2<NbWebModel> createExtension(Project project) throws IOException {
        return new WebModuleExtension(project);
    }

    @Override
    public Set<String> getSuppressedExtensions() {
        return Collections.emptySet();
    }

    private static final class Query2 implements GradleModelDefQuery2 {

        private static final GradleModelDef RESULT = GradleModelDef.fromProjectQueries(
                toQuery(NbWebModelBuilder.INSTANCE)
        );

        @Override
        public GradleModelDef getModelDef(GradleTarget gradleTarget) {
            return RESULT;
        }
    }

    private static <T> GradleProjectInfoQuery<T> toQuery(final ProjectInfoBuilder<T> builder) {
        return new GradleProjectInfoQuery<T>() {
            @Override
            public ProjectInfoBuilder<T> getInfoBuilder() {
                return builder;
            }

            @Override
            public ModelClassPathDef getInfoClassPath() {
                return ModelClassPathDef.EMPTY;
            }
        };
    }

}
