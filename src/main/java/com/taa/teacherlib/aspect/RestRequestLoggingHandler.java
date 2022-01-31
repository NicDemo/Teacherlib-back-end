package com.taa.teacherlib.aspect;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestRequestLoggingHandler {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controller() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {
    }

    //penser à enlever les log de JPA pour mieux voir celles de l'aspect

    @AfterReturning(pointcut = "controller() && args(..,request) || restController() && args(..,request)", returning = "result")
    public void logAfter(JoinPoint joinPoint, HttpServletRequest request, Object result) {
        ResponseEntity<Object> res = (ResponseEntity) result;
        String returnValue = res.getStatusCode().toString();
        if (null != request) {
            String hearderLog = "HEADERS: ";
            Enumeration headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = (String) headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                hearderLog += headerName + ": " + headerValue + " - ";
            }
            log.info(request.getMethod() + " " + request.getServletPath() + " HTTP/1.1 " + returnValue + " - " + hearderLog);
        }
    }

    //After -> Any method within resource annotated with @Controller annotation
    @AfterThrowing(pointcut = "controller()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        log.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        log.error("Cause : " + exception.getCause());
    }



}