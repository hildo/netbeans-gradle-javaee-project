/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.beans;

import org.netbeans.gradle.javaee.web.WebModuleExtension;
import org.netbeans.modules.web.beans.CdiUtil;
import org.openide.filesystems.FileObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ed
 */
public class GradleCdiUtil extends CdiUtil {

    private static final Logger LOGGER = Logger.getLogger(GradleCdiUtil.class.getName());

    private final WebModuleExtension webExt;

    public GradleCdiUtil(WebModuleExtension webExt) {
        super(webExt.getProject());
        this.webExt = webExt;
        LOGGER.log(Level.FINEST, "Creating GradleCdiUtil for {0}", webExt.getProject().getProjectDirectory().getName());
    }

    @Override
    public boolean isCdiEnabled() {
        LOGGER.entering(this.getClass().getName(), "isCdiEnabled");
        boolean returnValue = false;
        FileObject webInf = getProject().getProjectDirectory().getFileObject(
                webExt.getCurrentModel().getWebAppDir() + "/WEB-INF");
        if (webInf != null) {
            FileObject beansXml = webInf.getFileObject("beans.xml");
            returnValue = (beansXml != null);
        }
        LOGGER.exiting(this.getClass().getName(), "isCdiEnabled", returnValue);
        return returnValue;
    }
}
