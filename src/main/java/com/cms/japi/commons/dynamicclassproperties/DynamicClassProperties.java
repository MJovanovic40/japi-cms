package com.cms.japi.commons.dynamicclassproperties;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DynamicClassProperties {

    private final String name;

    private final List<DynamicClassField> fields;
}
