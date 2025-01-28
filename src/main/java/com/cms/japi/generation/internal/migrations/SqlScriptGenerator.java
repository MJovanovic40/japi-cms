package com.cms.japi.generation.internal.migrations;

import com.cms.japi.logging.LogService;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlScriptGenerator {

    private static final Pattern VERSION_PATTERN = Pattern.compile("^V(\\d+)__.*\\.sql$");

    @LogService
    public void generateSqlScript(String query, String description) throws Exception {
        Path migrationPath = resolveMigrationClasspath();
        Path scriptName = resolveScriptName(description);
        Path filePath = Path.of(migrationPath.toString(), scriptName.toString());
        Files.writeString(filePath, query, StandardOpenOption.CREATE);
    }

    private Path resolveScriptName(String description) throws Exception {
        int migrationVersion = findLastMigrationVersion() + 1;
        String scriptName = "V" +
                migrationVersion +
                "__" +
                description +
                ".sql";
        return Path.of(scriptName);
    }

    /**
     * helper function to determine the last migration version from SQL files in the migration directory.
     * <p>
     * The function searches for `.sql` files in the `db/migration` directory, extracts version numbers
     * from their filenames using a predefined pattern, and returns the highest version number found.
     * If no migration files are found, it returns 0.
     *
     * @return int The highest migration version found, or 0 if no valid migration files exist.
     * @throws Exception if an error occurs while accessing or processing files in the migration directory.
     */
    private int findLastMigrationVersion() throws Exception {
        Path migrationPath = resolveMigrationClasspath();

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

    /**
     * Helper function that resolves the file system path to the `db/migration` directory containing
     * `.sql` migration files.
     *
     * @return Path The resolved path to the `db/migration` directory.
     * @throws IllegalStateException if the directory does not exist.
     * @throws Exception             for any unexpected errors while resolving the path.
     */
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
