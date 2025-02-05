package com.cms.japi.metadata.internal.requests;

import com.cms.japi.commons.dynamicclassproperties.DynamicClassProperties;
import com.cms.japi.commons.validators.ValidJsonClassMapping;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class UpdateDynamicEntityRequest {

    @NotNull
    @NotEmpty
    private final String name;

    @NotNull
    @NotEmpty
    @ValidJsonClassMapping(targetClass = DynamicClassProperties.class, ignoreFields = {"type", "dependencies"})
    private final String data;
}
