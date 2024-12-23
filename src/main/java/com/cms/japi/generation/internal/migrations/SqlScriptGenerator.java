package com.cms.japi.generation.internal.migrations;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlScriptGenerator {

    private static final Pattern VERSION_PATTERN = Pattern.compile("^V(\\d+)__.*\\.sql$");

    public void generateSqlScript(String query) throws Exception {
        Path scriptName = resolveScriptName();
    }

    private Path resolveScriptName() throws Exception {
        int migrationVersion = findLastMigrationVersion();
        System.out.println(migrationVersion);
        return Path.of("");
    }

    private int findLastMigrationVersion() throws Exception {
        Path migrationPath = resolveMigrationClasspath();
        if (!Files.exists(migrationPath)) throw new IllegalArgumentException("Migration Path does not exists.");

        int maxVersion = 0;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(migrationPath, "*.sql")) {
            for (Path path : files) {
                Matcher matcher = VERSION_PATTERN.matcher(path.getFileName().toString());

                if (matcher.matches()) {
                    int version = Integer.parseInt(matcher.group(1));
                    maxVersion = Math.max(maxVersion, version);
                }
            }
        }
        return maxVersion;
    }

    private Path resolveMigrationClasspath() throws Exception {
        try {
            // Try to find the migration folder in the runtime resources directory
            URL resource = getClass().getClassLoader().getResource("db/migration");
            Path migrationPath;

            if (resource != null) {
                // If found in the classpath, use the resource folder (runtime)
                migrationPath = Paths.get(resource.toURI());
            } else {
                // Fall back to development path (src/main/resources/db/migration)
                migrationPath = Paths.get("src/main/resources/db.migration");
            }

            if (!Files.exists(migrationPath)) {
                throw new IllegalStateException("Migration path does not exist: " + migrationPath);
            }

            return migrationPath;
        } catch (Exception e) {
            throw new Exception("Error resolving migration path: " + e.getMessage(), e);
        }
    }

}
