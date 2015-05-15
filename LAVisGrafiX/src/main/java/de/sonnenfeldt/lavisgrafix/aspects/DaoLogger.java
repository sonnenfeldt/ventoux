package de.sonnenfeldt.lavisgrafix.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DaoLogger {
	
	static Logger log = Logger.getLogger(DaoLogger.class.getName());	

	@Before("daoOperations() && target(repo)")
	public void trace(JoinPoint jp, Object repo) {
		// String className = jp.getTarget().getClass().getName();
		String className = repo.getClass().getName();
		String methodName = jp.getSignature().getName();
		log.debug("method invoked:" + className + "#" + methodName);
	}

	@Pointcut("execution(* *..dao.*.*(..))")
	public void daoOperations() {

	}
}
