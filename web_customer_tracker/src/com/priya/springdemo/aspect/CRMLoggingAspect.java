package com.priya.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* com.priya.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.priya.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.priya.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() ||forDaoPackage()")
	private void forAppFlow() {}
	
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===========> in @Before: calling method: " +theMethod);
		
		Object[] args = theJoinPoint.getArgs();
		for(Object theArgs : args) {
			myLogger.info("===========> arguments:" +theArgs);
		}
	}
	
	@AfterReturning(pointcut="forAppFlow()", returning="result")
	public void afterReturning(JoinPoint theJoinPoint, Object result) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===========> in @AfterReturning: calling method: " +theMethod);
		
		myLogger.info("===========> result:" +result);
	}
}
	
