package com.cms.japi.classgeneration.internal.generation.model;

import com.cms.japi.JapiApplication;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassField;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.internal.DynamicClassPropertiesServiceImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

@RequiredArgsConstructor
public class ModelGenerator implements ClassGenerator {
    private final DynamicClassPropertiesServiceImpl classPropertiesService;

    @Override
    public Class<?> generate(DynamicClassProperties dynamicClassProperties) {
        var builder = new ByteBuddy().subclass(GeneratedEntity.class)
                .annotateType(AnnotationDescription.Builder.ofType(Entity.class).build());

        for (DynamicClassField field : dynamicClassProperties.getFields()) {
            builder = builder.defineField(dynamicClassProperties.getName() + "_" + field.getName(), String.class)
                    .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
        }

        builder = builder.defineField("id", Long.class).annotateField(
                AnnotationDescription.Builder.ofType(Id.class).build(),
                AnnotationDescription.Builder.ofType(Column.class).build(),
                AnnotationDescription.Builder.ofType(GeneratedValue.class).build());

        DynamicType.Unloaded<?> generatedClass = builder.name(dynamicClassProperties.getName()).make();
        return generatedClass.load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
