package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingEntityLifecycleOnServiceAspect extends AbstractServiceMessageLogger {

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.service.*.add(..))")
    public void onAdd() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.service.*.update(..))")
    public void onUpdate() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.service.*.delete(..))")
    public void onDelete() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.service.*.deleteById(..))")
    public void onDeleteById() {
    }

    @Before("onAdd() && args(entity)")
    public void beforeOnAdd(JoinPoint joinPoint, Object entity) {
        logBeforeActionOnSimpleCrud(joinPoint, entity, "Adding");
    }

    @AfterReturning(pointcut = "onAdd() && args(entity)", returning = "result")
    public void afterReturningOnAdd(JoinPoint joinPoint, Object entity, Object result) {
        logSuccessOnSimpleCrud(joinPoint, entity, "Adding", result);
    }

    @Before("onUpdate() && args(entity)")
    public void beforeOnUpdate(JoinPoint joinPoint, Object entity) {
        logBeforeActionOnSimpleCrud(joinPoint, entity, "Updating");
    }

    @AfterReturning(pointcut = "onUpdate() && args(entity)", returning = "result")
    public void afterReturningOnUpdate(JoinPoint joinPoint, Object entity, Object result) {
        logSuccessOnSimpleCrud(joinPoint, entity, "Updating", result);
    }

    @Before("onDelete() && args(entity)")
    public void beforeOnDelete(JoinPoint joinPoint, Object entity) {
        logBeforeActionOnSimpleCrud(joinPoint, entity, "Deleting");
    }

    @AfterReturning(pointcut = "onDelete() && args(entity)", returning = "result")
    public void afterReturningOnDelete(JoinPoint joinPoint, Object entity, Object result) {
        if (entity != null) {
            logAction(LAYER,
                    joinPoint,
                    "Deleting",
                    String.format("%s-entity finished with '%s'", entity.getClass().getSimpleName(), result));
        } else {
            logAction(LAYER,
                    joinPoint,
                    "Deleting",
                    String.format("'null' as entity finished with '%s'", result));
        }
    }

    @Before("onDeleteById() && args(id)")
    public void beforeOnDeleteById(JoinPoint joinPoint, Long id) {
        logAction(LAYER,
                joinPoint,
                "Deleting",
                String.format("entity (ID = %s)", id));
    }

    @AfterReturning(pointcut = "onDeleteById() && args(id)", returning = "result")
    public void afterReturningOnDeleteById(JoinPoint joinPoint, Long id, Object result) {
        logAction(LAYER,
                joinPoint,
                "Deleting",
                String.format("entity (ID = %s) finished with '%s'", id, result));
    }

}
