package com.cms.japi.generation;

import com.cms.japi.generation.internal.migrations.MigrationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final MigrationBuilder migrationBuilder;

    public Controller(MigrationBuilder migrationBuilder) {
        this.migrationBuilder = migrationBuilder;
    }

    @GetMapping("/test")
    public String test() {
        return migrationBuilder.dropColumn("event_publication", "id");
    }
}
