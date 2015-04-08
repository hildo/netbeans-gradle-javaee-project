/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.web.model.NbWebModel;
import org.netbeans.gradle.javaee.web.model.NbWebModelBuilder;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension2;
import org.netbeans.gradle.project.api.entry.GradleProjectExtensionDef;
import org.netbeans.gradle.project.api.entry.ModelLoadResult;
import org.netbeans.gradle.project.api.entry.ParsedModel;
import org.netbeans.gradle.project.api.modelquery.GradleModelDef;
import org.netbeans.gradle.project.api.modelquery.GradleModelDefQuery2;
import org.netbeans.gradle.project.api.modelquery.GradleTarget;
import org.openide.modules.SpecificationVersion;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Ed
 */
@ServiceProvider(service = GradleProjectExtensionDef.class)
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
        LOGGER.entering(this.getClass().getName(), "parseModel", retrievedModels);
        ParsedModel<NbWebModel> returnValue = null;
        // XXX: My log files suggest that the lookup method is returning null
        NbWebModel webModel = retrievedModels.getMainProjectModels().lookup(NbWebModel.class);
        if (webModel != null) {
            returnValue = new ParsedModel(webModel);
        }
        LOGGER.exiting(this.getClass().getName(), "parseModel", returnValue);
        return returnValue;
    }

    @Override
    public GradleProjectExtension2<NbWebModel> createExtension(Project project) throws IOException {
        LOGGER.entering(this.getClass().getName(), "createExtension", project);
        GradleProjectExtension2<NbWebModel> returnValue = new WebModuleExtension(project);
        LOGGER.exiting(this.getClass().getName(), "createExtension", returnValue);
        return returnValue;
    }

    @Override
    public Set<String> getSuppressedExtensions() {
        return Collections.emptySet();
    }

    private static final class Query2 implements GradleModelDefQuery2 {

        private static final SpecificationVersion MINIMUM_JDK_VERSION = new SpecificationVersion("1.7");
        private static final GradleModelDef RESULT = GradleModelDef.fromProjectInfoBuilders(NbWebModelBuilder.INSTANCE);

        @Override
        public GradleModelDef getModelDef(GradleTarget gradleTarget) {
            if (gradleTarget.getJavaVersion().compareTo(MINIMUM_JDK_VERSION) < 0) {
                return GradleModelDef.EMPTY;
            }
            return RESULT;
        }
    }

}
