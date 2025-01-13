package com.cms.japi.classgeneration.internal.generation.dto;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassField;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;

public class DtoGenerator implements ClassGenerator {

    @Override
    public Class<?> generate(DynamicClassProperties dynamicClassProperties) {
        var builder = new ByteBuddy().subclass(GeneratedDto.class)
                .annotateType(AnnotationDescription.Builder.ofType(Data.class).build())
                .annotateType(AnnotationDescription.Builder.ofType(NoArgsConstructor.class).build());

        for (DynamicClassField field : dynamicClassProperties.getFields()) {
            builder = builder.defineField(field.getName(), String.class)
                    .annotateField(AnnotationDescription.Builder.ofType(Column.class).build());
        }

        builder = builder.defineField("id", Long.class);

        DynamicType.Unloaded<?> generatedClass = builder.name(dynamicClassProperties.getName()).make();
        return generatedClass.load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
