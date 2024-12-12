package com.cms.japi.generation.internal.migrations;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.alterTable;

@Service
public class MigrationBuilder {
    private static final String INFORMATION_SCHEMA_TABLES = "information_schema.tables";
    private static final String INFORMATION_SCHEMA_COLUMNS = "information_schema.columns";
    private final DSLContext dslContext;

    public MigrationBuilder(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public String dropColumn(String table, String column) {
        if (!checkIfTableExists(table)) throw new RuntimeException(table + " does not exists.");
        if (!checkIfColumnExists(column, table)) throw new RuntimeException(column + " does not exists.");
        Query query = alterTable(table).dropColumn(column);
        return query.getSQL();
    }

    private boolean checkIfTableExists(String table) {
        return dslContext.selectOne()
                .from(INFORMATION_SCHEMA_TABLES)
                .where("table_name = ?", table)
                .fetchOne() != null;
    }

    private boolean checkIfColumnExists(String column, String table) {
        Integer count = dslContext
                .selectCount()
                .from(INFORMATION_SCHEMA_COLUMNS)
                .where("table_name = ?", table)
                .and("column_name = ?", column)
                .fetchOne(0, Integer.class);
        return count != null && count > 0;
    }
}