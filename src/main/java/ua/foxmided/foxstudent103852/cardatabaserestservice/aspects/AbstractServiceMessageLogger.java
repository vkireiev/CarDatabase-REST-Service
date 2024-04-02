package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component
public class AbstractServiceMessageLogger extends AbstractMessageLogger {
    protected static final String LAYER = "Service";

    protected void logBeforeActionOnSimpleCrud(JoinPoint joinPoint, Object entity, String action) {
        if (entity != null) {
            logAction(LAYER,
                    joinPoint,
                    action,
                    String.format("%s-entity: %s", entity.getClass().getSimpleName(), entity));
        } else {
            logAction(LAYER,
                    joinPoint,
                    action,
                    "'null' as entity");
        }
    }

    protected void logSuccessOnSimpleCrud(JoinPoint joinPoint, Object entity, String action, Object result) {
        if (result != null) {
            logAction(LAYER,
                    joinPoint,
                    action,
                    String.format("%s-entity finished successfully", result.getClass().getSimpleName()));
        } else {
            logAction(LAYER,
                    joinPoint,
                    action,
                    String.format("%s-entity returned 'null'", entity.getClass().getSimpleName()));
        }
    }

    protected void logBeforeActionOnUpdatingElement(JoinPoint joinPoint, Long id, String action) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("updating for entity (ID = %s)", id));
    }

    protected void logSuccessOnUpdatingElement(JoinPoint joinPoint, Long id, String action, Object result) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("updating for entity (ID = %s) finished with '%s'", id, result));
    }

    protected void logSuccessOnUpdatingElement(JoinPoint joinPoint, Long id, String action) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("updating for entity (ID = %s) finished successfully", id));
    }

}
