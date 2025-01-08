package com.cms.japi.classgeneration.internal.generation;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.properties.DynamicClassField;
import com.cms.japi.classgeneration.internal.properties.DynamicClassProperties;
import com.cms.japi.classgeneration.internal.utility.JsonUtils;
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
    private final JsonUtils jsonUtils;

    @Override
    public Class<?> generate(DynamicClassProperties dynamicClassProperties) {
        var builder = new ByteBuddy().subclass(Object.class)
                .annotateType(AnnotationDescription.Builder.ofType(Entity.class).build());

        for (DynamicClassField field : dynamicClassProperties.getFields()) {
            builder = builder.defineField(field.getName(), String.class)
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
