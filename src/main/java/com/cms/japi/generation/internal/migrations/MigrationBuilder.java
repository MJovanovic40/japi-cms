package com.cms.japi.generation.internal.migrations;

import com.cms.japi.generation.internal.exceptions.throwables.ColumnDoesNotExistsException;
import com.cms.japi.generation.internal.exceptions.throwables.TableDoesNotExistsException;
import com.cms.japi.logging.LogService;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.alterTable;

@Service
@RequiredArgsConstructor
public class MigrationBuilder {

    private static final String INFORMATION_SCHEMA_TABLES = "information_schema.tables";
    private static final String INFORMATION_SCHEMA_COLUMNS = "information_schema.columns";
    private final DSLContext dslContext;
    private final SqlScriptGenerator sqlScriptGenerator;

    @Value("${spring.datasource.name}")
    private String databaseName;

    @LogService
    public String dropColumn(String table, String column) throws Exception {
        if (!checkIfTableExists(table)) throw new TableDoesNotExistsException(table);
        if (!checkIfColumnExists(column, table)) throw new ColumnDoesNotExistsException(column);
        Query query = alterTable(table).dropColumn(column);
        sqlScriptGenerator.generateSqlScript(query.getSQL(), "drop_column");
        return query.getSQL();
    }

    private boolean checkIfTableExists(String table) {
        return dslContext.selectOne()
                .from(INFORMATION_SCHEMA_TABLES)
                .where("table_schema = ?", databaseName)
                .and("table_name = ?", table)
                .fetchOne() != null;
    }

    private boolean checkIfColumnExists(String column, String table) {
        Integer count = dslContext
                .selectCount()
                .from(INFORMATION_SCHEMA_COLUMNS)
                .where("table_schema = ?", databaseName)
                .and("table_name = ?", table)
                .and("column_name = ?", column)
                .fetchOne(0, Integer.class);
        return count != null && count > 0;
    }
}