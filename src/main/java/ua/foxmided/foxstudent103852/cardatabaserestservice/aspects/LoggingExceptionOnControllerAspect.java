package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;

@Aspect
@Component
@Log4j2
public class LoggingExceptionOnControllerAspect extends AbstractControllerMessageLogger {
    protected static final String LAYER = "Controller";

    @AfterThrowing(pointcut = "execution(* ua.foxmided.foxstudent103852.cardatabaserestservice.controller..*(..))", throwing = "exception")
    public void afterThrowingExceptionOnController(JoinPoint joinPoint, Throwable exception) {
        log.info("[     Controller] Exception: {}", exception.getMessage(), exception);
    }

    @AfterReturning(pointcut = "execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller.GlobalExceptionHandler.*(..)) && args(..)", returning = "returning")
    public void afterReturningOnCreateEntity(JoinPoint joinPoint, HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(joinPoint, "Exception handling", returning);
    }

}
