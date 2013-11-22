/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.beans;

import org.netbeans.api.project.Project;
import org.netbeans.modules.web.beans.CdiUtil;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Ed
 */
public class GradleCdiUtil extends CdiUtil {

    public GradleCdiUtil(Project project) {
        super(project);
    }

    @Override
    public boolean isCdiEnabled() {
        boolean returnValue = false;
        FileObject webInf = getProject().getProjectDirectory().getFileObject("src/main/webapp/WEB-INF");
        if (webInf != null) {
            FileObject beansXml = webInf.getFileObject("beans.xml");
            returnValue = (beansXml != null);
        }
        return returnValue;
    }
}
