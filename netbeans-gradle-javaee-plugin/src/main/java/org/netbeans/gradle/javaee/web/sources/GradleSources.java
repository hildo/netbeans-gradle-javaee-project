/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.gradle.javaee.web.sources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import static org.netbeans.gradle.javaee.web.sources.Bundle.LABEL_WebPages;
import org.netbeans.gradle.javaee.web.utils.Constants;
import org.netbeans.modules.web.common.spi.ProjectWebRootProvider;
import org.netbeans.spi.project.support.GenericSources;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 * Implementation of {@link Sources} interface for Java EE Gradle projects.
 *
 * <p>
 * This class is <i>immutable</i> and thus <i>thread safe</i>.
 *
 * @author Martin Janicek <mjanicek@netbeans.org>
 */
public final class GradleSources implements Sources {

    private final Project project;


    public GradleSources(Project project) {
        this.project = project;
    }

    @Override
    public SourceGroup[] getSourceGroups(String string) {
        List<SourceGroup> sourceGroups = new ArrayList<>();

        ProjectWebRootProvider webRootProvider = project.getLookup().lookup(ProjectWebRootProvider.class);
        if (webRootProvider != null) {
            for (FileObject webRoot : webRootProvider.getWebRoots()) {
                sourceGroups.add(GenericSources.group(project, webRoot, Constants.WEB_ROOT, getDisplayName(), null, null));
            }
        }

        return sourceGroups.toArray(new SourceGroup[sourceGroups.size()]);
    }

    @NbBundle.Messages("LABEL_WebPages=Web Pages")
    private String getDisplayName() {
        return LABEL_WebPages();
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
        // I guess there is no need to listen on Source changes at the moment
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
        // I guess there is no need to listen on Source changes at the moment
    }
}
