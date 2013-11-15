/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.project;

import org.netbeans.api.project.Project;
import org.netbeans.gradle.javaee.project.impl.JavaEeExtension;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.netbeans.gradle.project.api.entry.GradleProjectExtensionQuery;
import org.openide.util.lookup.ServiceProvider;

import java.io.IOException;

import javax.annotation.Nonnull;

/**
 *
 * @author Ed
 */
@ServiceProvider(service = GradleProjectExtensionQuery.class, position = 800)
public class JavaEEGradleProjectExtQuery implements GradleProjectExtensionQuery {

    @Override
    public GradleProjectExtension loadExtensionForProject(@Nonnull Project project) throws IOException{
        return new JavaEeExtension(project);
    }
}
