package tn.esprit.aop;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;



@Aspect
@Configuration
@EnableAspectJAutoProxy
public class ExecutionTimeAspect {
	
	private static final Logger log = LogManager.getLogger(ExecutionTimeAspect.class);
	
    @Around("@annotation(tn.esprit.spring.aop.TrackTime)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();//wa9th methode bdet
        Object proceed = joinPoint.proceed();///proceced anahi methode
        long executionTime = System.currentTimeMillis() - start;//execution time
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();//esme methode b package
        Method method = methodSignature.getMethod();//esem methode. 
        TrackTime measured = method.getAnnotation(TrackTime.class);
        String message = measured.message();
        String nameMethod= joinPoint.getSignature().toShortString();
        if (Strings.isEmpty(message))
            log.debug("Method {} execution: {} ms",nameMethod , executionTime);
        else
            log.debug("{}: {} ms", message, executionTime);
        return proceed;
    }
}