/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.sources;

import java.util.Collection;
import java.util.Collections;
import org.netbeans.api.project.Project;
import org.netbeans.modules.web.common.spi.ProjectWebRootProvider;
import org.openide.filesystems.FileObject;

/**
 * This class is <i>immutable</i> and thus <i>thread safe</i>.
 *
 * @author Martin Janicek <mjanicek@netbeans.org>
 */
public final class GradleWebRootProvider implements ProjectWebRootProvider {

    private final FileObject projectDir;


    public GradleWebRootProvider(Project project) {
        this.projectDir = project.getProjectDirectory();
    }

    @Override
    public FileObject getWebRoot(FileObject fo) {
        FileObject webAppFO = projectDir.getFileObject("src/main/webapp");
        if (webAppFO != null) {
            return webAppFO;
        }
        return null;
    }

    @Override
    public Collection<FileObject> getWebRoots() {
        FileObject webRoot = getWebRoot(projectDir);
        if (webRoot != null) {
            return Collections.singletonList(getWebRoot(projectDir));
        }
        return Collections.emptyList();
    }
}
