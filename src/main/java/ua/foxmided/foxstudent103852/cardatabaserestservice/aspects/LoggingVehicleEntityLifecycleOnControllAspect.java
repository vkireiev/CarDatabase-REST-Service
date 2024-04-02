package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;

@Aspect
@Component
public class LoggingVehicleEntityLifecycleOnControllAspect extends AbstractControllerMessageLogger {

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller.Vehicle*.findAllEntities(..))")
    public void onFindAllEntities() {
    }

    @Before("onFindAllEntities() && args(..)")
    public void beforeOnFindAllEntities(JoinPoint joinPoint) {
        logAction(LAYER,
                joinPoint,
                "Find all entities");
    }

    @AfterReturning(pointcut = "onFindAllEntities() && args(..)", returning = "returning")
    public void afterReturningOnFindAllEntities(JoinPoint joinPoint,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                "Find all entities",
                returning);
    }

}
