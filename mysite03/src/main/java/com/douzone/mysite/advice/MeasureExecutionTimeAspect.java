package com.douzone.mysite.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	
	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))")
	public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch sw = new StopWatch();
		sw.start();
		Object result = joinPoint.proceed();
		sw.stop();
		System.out.println("[Execution Time][" + joinPoint.getSignature() + "] " + sw.getTotalTimeMillis() + "ms");

		return result;
	}
}