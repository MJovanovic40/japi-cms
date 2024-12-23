package com.cms.japi.logging.internal.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("within(@com.cms.japi.logging.LogService *) && !@annotation(com.cms.japi.logging.LogService)")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        return performLogging(joinPoint);
    }

    @Around("@annotation(com.cms.japi.logging.LogService)")
    public Object logAnnotatedMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return performLogging(joinPoint);
    }

    @After("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
    public void logExceptionHandling(JoinPoint joinPoint) {
        String handlerName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        if(args.length == 0) return;
        log.info("Exception {} handled with handler {}", args[0].getClass().getName(), handlerName);
    }

    private Object performLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        String serviceName = joinPoint.getSignature().toShortString();
        Object[] callArgs = joinPoint.getArgs();

        log.info("Service {} called with arguments: {}", serviceName, callArgs != null ? callArgs : "[]");
        final Object res = joinPoint.proceed();
        log.info("Service {} returned {}", serviceName, res != null ? res : "null");

        return res;
    }
}
