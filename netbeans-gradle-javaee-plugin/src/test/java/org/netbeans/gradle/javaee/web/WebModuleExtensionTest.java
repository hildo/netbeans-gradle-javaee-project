/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.spi.webmodule.WebModuleProvider;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Ed
 */
@RunWith(MockitoJUnitRunner.class)
public class WebModuleExtensionTest {

    @Mock
    private FileObject mockedProjectDir;

    @Mock
    private Project mockedProject;

    private void setUpProject() {
        when(mockedProjectDir.getName()).thenReturn("MyTestProject");
        when(mockedProject.getProjectDirectory()).thenReturn(mockedProjectDir);
    }

    @Test
    public void testName() {
        setUpProject();
        WebModuleExtension ext = new WebModuleExtension(mockedProject);
        Assert.assertEquals("org.netbeans.gradle.project.javaee.WebModuleExtension", ext.getExtensionName());
    }

    @Test
    public void testLookupsCreated() {
        setUpProject();
        WebModuleExtension ext = new WebModuleExtension(mockedProject);
        Assert.assertNotNull(ext.getExtensionLookup());
        Assert.assertNotNull(ext.getExtensionLookup().lookup((WebModuleProvider.class)));
    }
}
