package org.netbeans.gradle.javaee.models;

import org.netbeans.gradle.model.api.ProjectInfoBuilder2;

public final class GradleEEModelBuilders {
    public static final ProjectInfoBuilder2<NbJpaModel> JPA_BUILDER
            = new EnumProjectInfoBuilderRef<>(NbJpaModel.class, "NbJpaModelBuilder");

    public static final ProjectInfoBuilder2<NbWebModel> WEB_BUILDER
            = new EnumProjectInfoBuilderRef<>(NbWebModel.class, "NbWebModelBuilder");

    private GradleEEModelBuilders() {
        throw new AssertionError();
    }
}
