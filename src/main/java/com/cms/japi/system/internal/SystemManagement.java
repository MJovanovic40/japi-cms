package com.cms.japi.system.internal;

import com.cms.japi.JapiApplication;
import com.cms.japi.generation.events.ScriptGeneratedEvent;
import com.cms.japi.logging.LogService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SystemManagement {
    private ConfigurableApplicationContext context;

    public SystemManagement(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @LogService
    @Async
    @EventListener
    public void restart(ScriptGeneratedEvent event) {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);

        Thread thread = new Thread(() -> {
            context.close();
            context = SpringApplication.run(JapiApplication.class, args.getSourceArgs());
        });

        thread.setDaemon(false);
        thread.start();
    }
}
