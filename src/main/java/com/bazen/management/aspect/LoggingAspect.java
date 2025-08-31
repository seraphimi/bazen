package com.bazen.management.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut for Service layer methods
    @Pointcut("execution(* com.bazen.management.service..*(..))")
    public void serviceLayer() {}

    // Pointcut for Controller layer methods  
    @Pointcut("execution(* com.bazen.management.controller..*(..))")
    public void controllerLayer() {}

    // Pointcut for Repository layer methods
    @Pointcut("execution(* com.bazen.management.repository..*(..))")
    public void repositoryLayer() {}

    // Combined pointcut for all layers
    @Pointcut("serviceLayer() || controllerLayer() || repositoryLayer()")
    public void allLayers() {}

    // @Around advice for method execution with timing
    @Around("allLayers()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        // Log method entry with parameters
        if (args.length > 0) {
            logger.info(">>> Entering {}.{} with parameters: {}", 
                       className, methodName, Arrays.toString(args));
        } else {
            logger.info(">>> Entering {}.{} with no parameters", className, methodName);
        }

        Object result;
        try {
            // Proceed with method execution
            result = joinPoint.proceed();
            
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log method exit with return value and execution time
            logger.info("<<< Exiting {}.{} - Execution time: {}ms - Return value: {}", 
                       className, methodName, executionTime, 
                       result != null ? result.toString() : "null");
            
            return result;
            
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            // Log exception details
            logger.error("!!! Exception in {}.{} after {}ms - Exception: {} - Message: {}", 
                        className, methodName, executionTime, 
                        e.getClass().getSimpleName(), e.getMessage());
            
            throw e;
        }
    }

    // @Before advice for method entry logging
    @Before("allLayers()")
    public void logMethodEntry(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.debug("==> Starting method {}.{}", className, methodName);
    }

    // @After advice for method completion
    @After("allLayers()")
    public void logMethodCompletion(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.debug("<== Completed method {}.{}", className, methodName);
    }

    // @AfterReturning advice for successful method execution
    @AfterReturning(pointcut = "allLayers()", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.debug("*** {}.{} returned successfully", className, methodName);
    }

    // @AfterThrowing advice for exception handling
    @AfterThrowing(pointcut = "allLayers()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("### Exception caught in {}.{}: {} - {}", 
                    className, methodName, exception.getClass().getSimpleName(), 
                    exception.getMessage());
    }
}