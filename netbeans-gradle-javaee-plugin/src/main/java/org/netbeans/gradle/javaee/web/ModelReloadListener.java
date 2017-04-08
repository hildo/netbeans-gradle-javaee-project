package org.netbeans.gradle.javaee.web;

import org.netbeans.gradle.javaee.models.NbWebModel;

public interface ModelReloadListener {
    public void onModelChange(NbWebModel prevModel, NbWebModel newModel);
}
