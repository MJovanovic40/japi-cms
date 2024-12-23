package com.cms.japi.metadata.internal.requests;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CreateDynamicEntityRequest {
    @NotNull
    @NotEmpty
    private final String name;
}
