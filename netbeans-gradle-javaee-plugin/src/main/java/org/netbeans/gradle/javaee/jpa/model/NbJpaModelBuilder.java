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
                returnValue = new NbJpaModel(builder.getPersistenceXmlFile());
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
//        private final Method getOutput;
//        private final Method getName;
//        private final Method getJava;
        private final Method getResources;
//        private final Method getAllSource;
//        private final Method getCompileClasspath;
//        private final Method getRuntimeClasspath;

        private SourceSetMethods(GradleClass type) throws Exception {
            this.type = type;
//            this.getOutput = type.getMethod("getOutput");
//            this.getName = type.getMethod("getName");
//            this.getJava = type.getMethod("getJava");
            this.getResources = type.getMethod("getResources");
//            this.getAllSource = type.getMethod("getAllSource");
//            this.getCompileClasspath = type.getMethod("getCompileClasspath");
//            this.getRuntimeClasspath = type.getMethod("getRuntimeClasspath");
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

//        public Object getOutput(Object sourceSet) throws Exception {
//            return getOutput.invoke(sourceSet);
//        }
//
//        public String getName(Object sourceSet) throws Exception {
//            Object result = getName.invoke(sourceSet);
//            return result != null ? result.toString() : null;
//        }
//
//        public Object getJava(Object sourceSet) throws Exception {
//            return getJava.invoke(sourceSet);
//        }

        public Object getResources(Object sourceSet) throws Exception {
            return getResources.invoke(sourceSet);
        }

//        public Object getAllSource(Object sourceSet) throws Exception {
//            return getAllSource.invoke(sourceSet);
//        }
//
//        public FileCollection getCompileClasspath(Object sourceSet) throws Exception {
//            return (FileCollection)getCompileClasspath.invoke(sourceSet);
//        }
//
//        public FileCollection getRuntimeClasspath(Object sourceSet) throws Exception {
//            return (FileCollection)getRuntimeClasspath.invoke(sourceSet);
//        }
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
        
        Builder(Project project) throws Exception {
            this.project = project;
            this.sourceSetMethods = SourceSetMethods.getInstance(project);
            this.sourceDirectorySetMethods = SourceDirectorySetMethods.getInstance(project);
            init();
        }
        
        String getPersistenceXmlFile() {
            return persistenceXmlFile;
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
                            return;
                        }
                    }
                }
            }
        }
    }
}