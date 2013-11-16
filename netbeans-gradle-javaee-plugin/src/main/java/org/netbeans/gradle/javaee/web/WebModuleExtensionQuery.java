/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.netbeans.gradle.project.api.entry.GradleProjectExtensionQuery;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

/**
 *
 * @author Ed
 */
@ServiceProvider(service = GradleProjectExtensionQuery.class, position = 800)
public class WebModuleExtensionQuery implements GradleProjectExtensionQuery {

    @Override
    public GradleProjectExtension loadExtensionForProject(Project project) throws IOException{
        return new WebModuleExtension(project);
    }
}
