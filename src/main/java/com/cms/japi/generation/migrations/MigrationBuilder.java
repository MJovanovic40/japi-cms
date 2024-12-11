package com.cms.japi.generation.migrations;

import org.jooq.Query;
import org.springframework.stereotype.Service;

import static org.jooq.impl.DSL.*;

@Service
public class MigrationBuilder {

    public String dropColumn(String table, String column) {
        Query query = alterTable(table).dropColumn(column);
        return query.getSQL();
    }
}
