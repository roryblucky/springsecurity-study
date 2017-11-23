package com.rory.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.rory.web.controller.UserController.*(..))")
    private Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long start = new Date().getTime();
        Object obj = pjp.proceed();
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is " + arg);
        }
        long end = new Date().getTime() - start;
        System.out.println("Time aspect 耗时" + end);
        return obj;
    }

    ;
}
