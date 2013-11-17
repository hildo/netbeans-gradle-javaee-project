/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.netbeans.api.project.Project;
import org.netbeans.gradle.project.api.entry.GradleProjectExtension;
import org.openide.filesystems.FileObject;

import java.io.IOException;

/**
 *
 * @author Ed
 */
public class WebModuleExtensionQueryTest {

    @Test
    public void testLoadExtensionForProjectIsWeb() throws IOException {
        FileObject mockedWebAppDir = mock(FileObject.class);
        FileObject mockedProjectDir = mock(FileObject.class);
        when(mockedProjectDir.getFileObject(eq("src/main/webapp"))).thenReturn(mockedWebAppDir);
        when(mockedProjectDir.getName()).thenReturn("MyWebApp");
        Project mockedProject = mock(Project.class);
        when(mockedProject.getProjectDirectory()).thenReturn(mockedProjectDir);

        WebModuleExtensionQuery q = new WebModuleExtensionQuery();
        GradleProjectExtension ext = q.loadExtensionForProject(mockedProject);
        Assert.assertNotNull(ext);
    }

    @Test
    public void testLoadExtensionForProjectIsNotWeb() throws IOException {
        FileObject mockedProjectDir = mock(FileObject.class);
        when(mockedProjectDir.getFileObject(eq("src/main/webapp"))).thenReturn(null);
        when(mockedProjectDir.getName()).thenReturn("SomeOtherProject");
        Project mockedProject = mock(Project.class);
        when(mockedProject.getProjectDirectory()).thenReturn(mockedProjectDir);

        WebModuleExtensionQuery q = new WebModuleExtensionQuery();
        GradleProjectExtension ext = q.loadExtensionForProject(mockedProject);
        Assert.assertNull(ext);
    }

}
