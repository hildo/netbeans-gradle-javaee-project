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
import org.mockito.runners.MockitoJUnitRunner;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.spi.webmodule.WebModuleProvider;

/**
 *
 * @author Ed
 */
@RunWith(MockitoJUnitRunner.class)
public class WebModuleExtensionTest {

    @Mock
    private Project mockedProject;

    @Test
    public void testName() {
        WebModuleExtension ext = new WebModuleExtension(mockedProject);
        Assert.assertEquals("org.netbeans.gradle.project.javaee.WebModuleExtension", ext.getExtensionName());
    }

    @Test
    public void testLookupsCreated() {
        WebModuleExtension ext = new WebModuleExtension(mockedProject);
        Assert.assertNotNull(ext.getExtensionLookup());
        Assert.assertNotNull(ext.getExtensionLookup().lookup((WebModuleProvider.class)));
    }
}
