package com.cms.japi.commons.dynamicclassproperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
public class DynamicClassProperties {

    private String name;

    private final List<DynamicClassField> fields = new ArrayList<>();

    private final Map<DynamicClassType, Class<?>> dependencies = new HashMap<>();

    private DynamicClassType dynamicClassType;
}
