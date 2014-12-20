/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.gradle.javaee.jpa.model;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Set;

import org.gradle.api.Project;
import org.netbeans.gradle.javaee.model.gradleclasses.GradleClass;
import org.netbeans.gradle.javaee.model.gradleclasses.GradleClasses;
import org.netbeans.gradle.model.api.ProjectInfoBuilder;

/**
 *
 * @author ed
 */
public class NbJpaModelBuilder implements ProjectInfoBuilder<NbJpaModel>{

    private static final String NAME = "org.netbeans.gradle.javaee.jpa.model.NbJpaModelBuilder";
    
    public static final NbJpaModelBuilder INSTANCE = new NbJpaModelBuilder();
    
    @Override
    public NbJpaModel getProjectInfo(Project project) {
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
            ex.printStackTrace(System.out);
        }
        return returnValue;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    private static final class SourceSetMethods {
        private static volatile SourceSetMethods CACHE = null;

        private final GradleClass type;
        private final Method getAllJava;
        private final Method getResources;

        private SourceSetMethods(GradleClass type) throws Exception {
            this.type = type;
            this.getAllJava = type.getMethod("getAllJava");
            this.getResources = type.getMethod("getResources");
        }

        public static SourceSetMethods getInstance(Project project) throws Exception {
            SourceSetMethods result = CACHE;
            GradleClass type = GradleClasses.getGradleClass(project, "org.gradle.api.tasks.SourceSet");
            if (result != null && type.equals(result.type)) {
                return result;
            }
            result = new SourceSetMethods(type);
            CACHE = result;
            return result;
        }

        public Object getResources(Object sourceSet) throws Exception {
            return getResources.invoke(sourceSet);
        }
        
        public Object getAllJava(Object sourceSet) throws Exception {
            return getAllJava.invoke(sourceSet);
        }
    }

    private static final class SourceDirectorySetMethods {
        private static volatile SourceDirectorySetMethods CACHE = null;

        private final GradleClass type;
        private final Method getSrcDirs;

        public SourceDirectorySetMethods(GradleClass type) throws Exception {
            this.type = type;
            this.getSrcDirs = type.getMethod("getSrcDirs");
        }

        public static SourceDirectorySetMethods getInstance(Project project) throws Exception {
            SourceDirectorySetMethods result = CACHE;
            GradleClass type = GradleClasses.getGradleClass(project, "org.gradle.api.file.SourceDirectorySet");
            if (result != null && type.equals(result.type)) {
                return result;
            }
            result = new SourceDirectorySetMethods(type);
            CACHE = result;
            return result;
        }

        @SuppressWarnings("unchecked")
        public Set<File> getSrcDirs(Object sourceDirectorySet) throws Exception {
            return (Set<File>)getSrcDirs.invoke(sourceDirectorySet);
        }
    }
    
    private static class Builder {
        private final Project project;
        private final SourceSetMethods sourceSetMethods;
        private final SourceDirectorySetMethods sourceDirectorySetMethods;
        private String persistenceXmlFile;
        private Iterable<File> javaSourceDirs;
        
        Builder(Project project) throws Exception {
            this.project = project;
            this.sourceSetMethods = SourceSetMethods.getInstance(project);
            this.sourceDirectorySetMethods = SourceDirectorySetMethods.getInstance(project);
            init();
        }
        
        String getPersistenceXmlFile() {
            return persistenceXmlFile;
        }
        
        Iterable<File> getJavaSourceDirs() {
            return javaSourceDirs;
        }
        
        private void init() throws Exception {
            Iterable<?> sourceSets = (Iterable<?>) project.getProperties().get("sourceSets");
            if (sourceSets == null) {
                return;
            }
            for (Object sourceSet : sourceSets) {
                Object resourceDirectorySet = sourceSetMethods.getResources(sourceSet);
                Set<File>resourceDirectories = sourceDirectorySetMethods.getSrcDirs(resourceDirectorySet);
                for (File resourceDirectory : resourceDirectories) {
                    File metaInfDir = new File(resourceDirectory, "META-INF");
                    if  (metaInfDir.exists()) {
                        File persistenceXmlFileObj = new File(metaInfDir, "persistence.xml");
                        if (persistenceXmlFileObj.exists()) {
                            persistenceXmlFile = persistenceXmlFileObj.getCanonicalPath();
                            break;
                        }
                    }
                }
                if (persistenceXmlFile != null) {
                    // Use this sourceSet and call getAllJava, which returns a SourceDirectorySet
                    // Then call getSrcDirs to get the ClassPath to set javaSourceDirs
                    Object allJavaDirectorySet = sourceSetMethods.getAllJava(sourceSet);
                    javaSourceDirs = sourceDirectorySetMethods.getSrcDirs(allJavaDirectorySet);
                    break;
                }
            }
        }
    }
}