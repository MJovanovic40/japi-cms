package com.cms.japi.logging.internal.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("@annotation(com.cms.japi.logging.LogService)")
    public Object logServiceExcecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String serviceName = joinPoint.getSignature().toShortString();
        Object[] callArgs = joinPoint.getArgs();

        log.info("Service {} called with arguments: {}", serviceName, callArgs != null ? callArgs : "[]");
        final Object res = joinPoint.proceed();
        log.info("Service {} returned {}", serviceName, res != null ? res : "null");

        return res;
    }
}
