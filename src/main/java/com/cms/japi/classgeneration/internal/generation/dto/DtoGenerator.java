package com.cms.japi.classgeneration.internal.generation.dto;

import com.cms.japi.JapiApplication;
import com.cms.japi.classgeneration.internal.generation.ClassGenerator;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassField;
import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.lang3.StringUtils;


@RequiredArgsConstructor
public class DtoGenerator implements ClassGenerator {

    private final DynamicClassProperties dynamicClassProperties;

    @Override
    public Class<?> generate() {
        var builder = new ByteBuddy().subclass(GeneratedDto.class)
                .annotateType(AnnotationDescription.Builder.ofType(Data.class).build())
                .annotateType(AnnotationDescription.Builder.ofType(NoArgsConstructor.class).build());

        for (DynamicClassField field : dynamicClassProperties.getFields()) {
            builder = builder.defineField(field.getName(), String.class, Visibility.PUBLIC)
                    .defineMethod("get" + StringUtils.capitalize(field.getName()), String.class, Visibility.PUBLIC)
                    .intercept(FieldAccessor.ofField(field.getName()))

                    .defineMethod("set" + StringUtils.capitalize(field.getName()), void.class, Visibility.PUBLIC)
                    .withParameter(String.class)
                    .intercept(FieldAccessor.ofField(field.getName()));
        }

        builder = builder.defineField("id", Long.class, Visibility.PUBLIC);

        DynamicType.Unloaded<?> generatedClass = builder.name(dynamicClassProperties.getName()).make();
        return generatedClass.load(JapiApplication.class.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();
    }
}
