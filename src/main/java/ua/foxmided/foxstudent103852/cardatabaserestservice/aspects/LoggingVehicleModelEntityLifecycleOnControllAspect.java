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
public class LoggingVehicleModelEntityLifecycleOnControllAspect extends AbstractControllerMessageLogger {

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller.VehicleModel*.findAllByMakeEntities(..))")
    public void onFindAllByMakeEntities() {
    }

    @Pointcut("execution(* ua.foxmided.foxstudent103852.cardatabaserestservice.controller.VehicleModel*.addModelYearToEntityYears(..))")
    public void onAddModelYearToEntityYears() {
    }

    @Pointcut("execution(* ua.foxmided.foxstudent103852.cardatabaserestservice.controller.VehicleModel*.removeModelYearFromEntityYears(..))")
    public void onRemoveModelYearFromEntityYears() {
    }

    @Before("onFindAllByMakeEntities() && args(..)")
    public void beforeOnFindAllByMakeEntities(JoinPoint joinPoint) {
        logAction(LAYER,
                joinPoint,
                "Find all entities by Make.name");
    }

    @AfterReturning(pointcut = "onFindAllByMakeEntities() && args(..)", returning = "returning")
    public void afterReturningOnFindAllByMakeEntities(JoinPoint joinPoint,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                "Find all entities by Make.name",
                returning);
    }

    @Before("onAddModelYearToEntityYears() && args(modelId, yearId)")
    public void beforeOnAddModelYearToEntityYears(JoinPoint joinPoint, Long modelId, Long yearId) {
        logBeforeActionWithEntityId(
                joinPoint,
                modelId,
                String.format("Modify (add ModelYear (ID=%s)) in VehicleModel", yearId));
    }

    @AfterReturning(pointcut = "onAddModelYearToEntityYears() && args(modelId, yearId)", returning = "returning")
    public void afterReturningOnAddModelYearToEntityYears(JoinPoint joinPoint, Long modelId, Long yearId,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                String.format("Modify (add ModelYear (ID=%s)) in VehicleModel entity (ID = %s)", yearId, modelId),
                returning);
    }

    @Before("onRemoveModelYearFromEntityYears() && args(modelId, yearId)")
    public void beforeOnRemoveModelYearFromEntityYears(JoinPoint joinPoint, Long modelId, Long yearId) {
        logBeforeActionWithEntityId(
                joinPoint,
                modelId,
                String.format("Modify (remove ModelYear (ID=%s)) in VehicleModel", yearId));
    }

    @AfterReturning(pointcut = "onRemoveModelYearFromEntityYears() && args(modelId, yearId)", returning = "returning")
    public void afterReturningOnRemoveModelYearFromEntityYears(JoinPoint joinPoint, Long modelId, Long yearId,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                String.format("Modify (remove ModelYear (ID=%s)) in VehicleModel entity (ID = %s)", yearId, modelId),
                returning);
    }

}
