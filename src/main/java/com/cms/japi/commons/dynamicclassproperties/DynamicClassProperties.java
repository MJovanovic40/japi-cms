package com.cms.japi.commons.dynamicclassproperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@ToString
@NoArgsConstructor
public class DynamicClassProperties {

    private String name;

    private final List<DynamicClassField> fields = new ArrayList<>();

    private final Map<DynamicClassType, Object> dependencies = new EnumMap<>(DynamicClassType.class);

    private DynamicClassType dynamicClassType;
}
