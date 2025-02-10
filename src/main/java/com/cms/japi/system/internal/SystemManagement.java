package com.cms.japi.system.internal;

import com.cms.japi.generation.events.ScriptGeneratedEvent;
import com.cms.japi.logging.LogService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SystemManagement {
    private final ConfigurableApplicationContext configurableApplicationContext;

    @PostConstruct
    public void post() {
        File file = new File("D:\\Projects\\japi-cms\\src\\main\\resources\\db.migration");
        System.out.println("reading files");
        Arrays.stream(file.listFiles()).forEach(f -> System.out.println(f.toURI()));
    }

    @LogService
    @Async
    @EventListener
    public void restart(ScriptGeneratedEvent event) {
        configurableApplicationContext.close();
        System.exit(0);
    }
}
