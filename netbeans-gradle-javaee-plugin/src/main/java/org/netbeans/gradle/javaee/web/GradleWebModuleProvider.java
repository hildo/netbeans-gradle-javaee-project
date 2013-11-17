/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web;

import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.WebModuleProvider;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Ed
 */
public class GradleWebModuleProvider implements WebModuleProvider {

    @Override
    public WebModule findWebModule(FileObject fo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
