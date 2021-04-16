package com.luismarcilio.grocery_brasil_app.textSearch;

import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggerAspect {

    @Around("@annotation(com.luismarcilio.grocery_brasil_app.textSearch.WithLog)")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        if(!log.isDebugEnabled()){
            return proceedingJoinPoint.proceed();
        }
        StringBuilder stringBuilder = new StringBuilder();
        try{
            stringBuilder.append(proceedingJoinPoint.getSignature().getName()+"(");
            Stream.of(proceedingJoinPoint.getArgs()).forEach(arg -> stringBuilder.append(arg.toString()+","));
            stringBuilder.append("): ");
            final Object proceed = proceedingJoinPoint.proceed();
            if(proceed != null){
                stringBuilder.append(proceed.toString());
            }
            return proceed;            
        }
        finally{
            log.debug(stringBuilder.toString());
        }
    }
    
}
