package com.cms.japi.generation.internal.utils;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class WatchServiceImpl {
    public static BasicFileAttributes awaitFile(Path target, long timeout)
            throws IOException, InterruptedException
    {
        final Path name = target.getFileName();
        final Path targetDir = target.getParent();

        // If path already exists, return early
        try {
            return Files.readAttributes(target, BasicFileAttributes.class);
        } catch (NoSuchFileException ex) {}

        final WatchService watchService = FileSystems.getDefault().newWatchService();
        try {
            final WatchKey watchKey = targetDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            // The file could have been created in the window between Files.readAttributes and Path.register
            try {
                return Files.readAttributes(target, BasicFileAttributes.class);
            } catch (NoSuchFileException ex) {}
            // The file is absent: watch events in parent directory
            WatchKey watchKey1 = null;
            boolean valid = true;
            do {
                long t0 = System.currentTimeMillis();
                watchKey1 = watchService.poll(timeout, TimeUnit.MILLISECONDS);
                if (watchKey1 == null) {
                    return null; // timed out
                }
                // Examine events associated with key
                for (WatchEvent<?> event: watchKey1.pollEvents()) {
                    Path path1 = (Path) event.context();
                    if (path1.getFileName().equals(name)) {
                        return Files.readAttributes(target, BasicFileAttributes.class);
                    }
                }
                // Did not receive an interesting event; re-register key to queue
                long elapsed = System.currentTimeMillis() - t0;
                timeout = elapsed < timeout? (timeout - elapsed) : 0L;
                valid = watchKey1.reset();
            } while (valid);
        } finally {
            watchService.close();
        }

        return null;
    }
}
