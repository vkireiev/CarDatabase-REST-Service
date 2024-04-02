package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingVehicleModelEntityLifecycleOnServiceAspect extends AbstractServiceMessageLogger {

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.service.VehicleModel*.modifyYears(..))")
    public void onModifyYears() {
    }

    @Before("onModifyYears() && args(id,..)")
    public void beforeOnModifyYears(JoinPoint joinPoint, Long id) {
        logBeforeActionOnUpdatingElement(joinPoint, id, "VehicleModel.years");
    }

    @AfterReturning(pointcut = "onModifyYears() && args(id,..)", returning = "result")
    public void afterReturningOnModifyYears(JoinPoint joinPoint, Long id, Object result) {
        logSuccessOnUpdatingElement(joinPoint, id, "VehicleModel.years");
    }

}
