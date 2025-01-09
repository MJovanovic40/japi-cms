package com.cms.japi.classgeneration;

import lombok.Data;
import lombok.ToString;

import java.sql.JDBCType;

@Data
@ToString

public class DynamicClassField {

    private final String name;
    private final JDBCType type;
}
