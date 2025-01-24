package com.cms.japi.classgeneration.internal.injection;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BeanInjector {

    private final GenericApplicationContext applicationContext;

    public Object injectBean(Class<?> bean) {
        applicationContext.registerBean(bean);
        return applicationContext.getBean(bean);
    }

    public Object getBean(Class<?> bean) {
        return applicationContext.getBean(bean);
    }
}
