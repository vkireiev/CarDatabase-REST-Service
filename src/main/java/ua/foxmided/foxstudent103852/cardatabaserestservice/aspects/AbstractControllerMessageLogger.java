package ua.foxmided.foxstudent103852.cardatabaserestservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpEntity;

import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiDataResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;

public class AbstractControllerMessageLogger extends AbstractMessageLogger {
    protected static final String LAYER = "Controller";

    protected void logBeforeAction(JoinPoint joinPoint, Long id, Object entity, String action) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("%s-entity (ID = %s) with %s", entity.getClass().getSimpleName(), id, entity));
    }

    protected void logBeforeActionWithEntity(JoinPoint joinPoint, Object entity, String action) {
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

    protected void logBeforeActionWithEntityId(JoinPoint joinPoint, Long id, String action) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("entity (ID = %s)", id));
    }

    protected void logSuccessWithEntityId(JoinPoint joinPoint, Long id, Object entity, String action) {
        logAction(LAYER,
                joinPoint,
                action,
                String.format("%s-entity (ID = %s) finished successfully", entity.getClass().getSimpleName(), id));
    }

    protected void logSuccessWithResponse(JoinPoint joinPoint, String action,
            HttpEntity<RestApiSimpleResponse> returning) {

        if (returning == null || returning.getBody() == null) {
            logAction(LAYER,
                    joinPoint,
                    action,
                    "finished with 'null'");
            return;
        }

        if (returning.getBody() instanceof RestApiDataResponse response) {
            logAction(LAYER,
                    joinPoint,
                    action,
                    String.format("finished with [code=%s, state='%s', date %s]",
                            response.getStatus(),
                            response.getMessage(),
                            response.getData() == null
                                    ? "is empty"
                                    : "is not empty"));
        } else {
            logAction(LAYER,
                    joinPoint,
                    action,
                    String.format("finished with [code=%s, state='%s']",
                            returning.getBody().getStatus(),
                            returning.getBody().getMessage()));
        }
    }

}
