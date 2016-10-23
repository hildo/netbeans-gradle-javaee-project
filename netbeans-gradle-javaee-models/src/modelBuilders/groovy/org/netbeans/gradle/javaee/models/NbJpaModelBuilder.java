package org.netbeans.gradle.javaee.models;

import java.io.File;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.jtrim.utils.ExceptionHelper;
import org.netbeans.gradle.model.api.ProjectInfoBuilder2;

/**
 *
 * @author ed
 */
enum NbJpaModelBuilder implements ProjectInfoBuilder2<NbJpaModel>{
    INSTANCE;

    @Override
    public NbJpaModel getProjectInfo(Object project) {
        return getProjectInfo((Project)project);
    }

    private NbJpaModel getProjectInfo(Project project) {
        NbJpaModel returnValue = null;
        try {
            Builder builder = new Builder(project);
            if (builder.getPersistenceXmlFile() != null) {
                returnValue = new NbJpaModel(
                        builder.getPersistenceXmlFile(),
                        builder.getJavaSourceDirs()
                );
            }
        } catch (Exception ex) {
            throw ExceptionHelper.throwUnchecked(ex);
        }
        return returnValue;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    private static class Builder {
        private final Project project;
        //private final SourceSetMethods sourceSetMethods;
        //private final SourceDirectorySetMethods sourceDirectorySetMethods;
        private String persistenceXmlFile;
        private Iterable<File> javaSourceDirs;

        Builder(Project project) throws Exception {
            this.project = project;
            init();
        }

        String getPersistenceXmlFile() {
            return persistenceXmlFile;
        }

        Iterable<File> getJavaSourceDirs() {
            return javaSourceDirs;
        }

        private void init() throws Exception {
            JavaPluginConvention java = project.getConvention().findPlugin(JavaPluginConvention.class);
            if (java == null) {
                return;
            }

            for (SourceSet sourceSet : java.getSourceSets()) {
                SourceDirectorySet resourceDirectorySet = sourceSet.getResources();
                Set<File> resourceDirectories = resourceDirectorySet.getSrcDirs();
                for (File resourceDirectory : resourceDirectories) {
                    File metaInfDir = new File(resourceDirectory, "META-INF");
                    if (metaInfDir.exists()) {
                        File persistenceXmlFileObj = new File(metaInfDir, "persistence.xml");
                        if (persistenceXmlFileObj.exists()) {
                            persistenceXmlFile = persistenceXmlFileObj.getCanonicalPath();
                            break;
                        }
                    }
                }
                if (persistenceXmlFile != null) {
                    SourceDirectorySet allJavaDirectorySet = sourceSet.getAllJava();
                    javaSourceDirs = allJavaDirectorySet.getSrcDirs();
                    break;
                }
            }
        }
    }
}