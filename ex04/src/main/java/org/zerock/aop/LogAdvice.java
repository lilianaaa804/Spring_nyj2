package org.zerock.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Log4j
@Component
public class LogAdvice {

    @Before("execution(* org.zerock.service.SampleService*.*(..))")
    public void logBefore(){
        log.info("=====================");
    }

    @Before("execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2){
        log.info("str: " + str1);
        log.info("str: " + str2);
    }
    @AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing = "exception")
    public void logException(Exception exception){
        log.info("Exception.....!!!!!");
        log.info("exception: "+ exception);
    }

}