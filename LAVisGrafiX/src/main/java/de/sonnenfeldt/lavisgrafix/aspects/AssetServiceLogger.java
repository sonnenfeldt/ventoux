package de.sonnenfeldt.lavisgrafix.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AssetServiceLogger {
		
	static Logger log = Logger.getLogger(AssetServiceLogger.class.getName());	

		@Before("set()")
		public void trace(JoinPoint jp, Object repo) {
			
			log.debug("AssetService method invoked:" + jp.getSignature());
		}
		
		@Pointcut("execution(void *..AssetServiceImpl.set*(*))")
		public void set() {

		}	

}


