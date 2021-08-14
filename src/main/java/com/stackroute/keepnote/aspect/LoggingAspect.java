package com.stackroute.keepnote.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/* Annotate this class with @Aspect and @Component */

@Aspect
@Component
public class LoggingAspect {
	
	 private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	    @Pointcut("execution(* com.stackroute.lombok_dto_springAOP.controller.MapController.*(..))")
	    public void pointCutMethod(){

	    }

	    @Before("pointCutMethod()")
	    public void beforeAdvice(JoinPoint joinPoint){
	        logger.info("****************Before************");
	        logger.warn("Method Name : {}", joinPoint.getSignature().getName());
	        logger.warn("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
	        logger.info("****************End of Before************");
	    }

	    @After("pointCutMethod()")
	    public void afterAdvice(JoinPoint joinPoint){
	        logger.info("****************After************");
	        logger.debug("Method Name : {}", joinPoint.getSignature().getName());
	        logger.debug("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
	        logger.info("****************End of After************");
	    }

	    @AfterReturning(value = "pointCutMethod()", returning = "temp")
	    public void afterAdvice(JoinPoint joinPoint, Object temp){
	        logger.info("****************After************");
	        logger.warn("Method Name : {}", joinPoint.getSignature().getName());
	        logger.warn("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
	        logger.warn("Returned value: {}", temp);
	        logger.info("****************End of After************");
	    }

	    @AfterThrowing(value = "pointCutMethod()", throwing = "temp")
	    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable temp){
	        logger.info("****************After************");
	        logger.warn("Method Name : {}", joinPoint.getSignature().getName());
	        logger.warn("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
	        logger.warn("Returned value: {}", temp);
	        logger.info("****************End of After************");
	    }

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
}
