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
public class LoggingEntityLifecycleOnControllerAspect extends AbstractControllerMessageLogger {

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller..getEntity(..))")
    public void onGetEntity() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller..getAllEntities(..))")
    public void onGetAllEntities() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller..addEntity(..))")
    public void onCreateEntity() {
    }

    @Pointcut("execution(public * ua.foxmided.foxstudent103852.cardatabaserestservice.controller..updateEntity(..))")
    public void onEditEntity() {
    }

    @Pointcut("execution(* ua.foxmided.foxstudent103852.cardatabaserestservice.controller..deleteEntity(..))")
    public void onDeleteEntity() {
    }

    @Before("onGetEntity() && args(id)")
    public void beforeOnGetEntity(JoinPoint joinPoint, Long id) {
        logAction(LAYER,
                joinPoint,
                "Get",
                String.format("entity (ID = %s)", id));
    }

    @AfterReturning(pointcut = "onGetEntity() && args(id)", returning = "returning")
    public void afterReturningOnGetEntity(JoinPoint joinPoint, Long id,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                String.format("Get entity (ID = %s)", id),
                returning);
    }

    @Before("onGetAllEntities() && args(..)")
    public void beforeOnGetEntity(JoinPoint joinPoint) {
        logAction(LAYER,
                joinPoint,
                "Get all entities");
    }

    @AfterReturning(pointcut = "onGetAllEntities() && args(..)", returning = "returning")
    public void afterReturningOnGetEntity(JoinPoint joinPoint, HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                "Get all entities",
                returning);
    }

    @Before("onCreateEntity() && args(entity)")
    public void beforeOnCreateEntity(JoinPoint joinPoint, Object entity) {
        logBeforeActionWithEntity(joinPoint, entity, "Adding");
    }

    @Before("onCreateEntity() && args(id, entity)")
    public void beforeOnCreateEntity(JoinPoint joinPoint, Long id, Object entity) {
        logBeforeActionWithEntity(joinPoint, entity, "Adding");
    }

    @AfterReturning(pointcut = "onCreateEntity() && args(entity)", returning = "returning")
    public void afterReturningOnCreateEntity(JoinPoint joinPoint, Object entity,
            HttpEntity<RestApiSimpleResponse> returning) {
        callAfterReturningLogMessage(joinPoint, entity, returning);
    }

    @AfterReturning(pointcut = "onCreateEntity() && args(id, entity)", returning = "returning")
    public void afterReturningOnCreateEntity(JoinPoint joinPoint, Long id, Object entity,
            HttpEntity<RestApiSimpleResponse> returning) {
        callAfterReturningLogMessage(joinPoint, entity, returning);
    }

    @Before("onEditEntity() && args(id, entity)")
    public void beforeOnEditEntity(JoinPoint joinPoint, Long id, Object entity) {
        logBeforeAction(joinPoint, id, entity, "Updating");
    }

    @AfterReturning(pointcut = "onEditEntity() && args(id, entity)", returning = "returning")
    public void afterReturningOnEditEntity(JoinPoint joinPoint, Long id, Object entity,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                String.format("Updating %s-entity (ID = %s)", entity.getClass().getSimpleName(), id),
                returning);
    }

    @Before("onDeleteEntity() && args(id)")
    public void beforeOnDeleteEntity(JoinPoint joinPoint, Long id) {
        logBeforeActionWithEntityId(joinPoint, id, "Deleting");
    }

    @AfterReturning(pointcut = "onDeleteEntity() && args(id)", returning = "returning")
    public void afterReturningOnDeleteEntity(JoinPoint joinPoint, Long id,
            HttpEntity<RestApiSimpleResponse> returning) {
        logSuccessWithResponse(
                joinPoint,
                String.format("Deleting entity (ID = %s)", id),
                returning);
    }

//    @Before("onEditEntityPage() && args(id,..)")
//    public void beforeOnEditEntityPage(JoinPoint joinPoint, Long id) {
//        addBeforeLogMessageWithEntityId(joinPoint, id, "Getting data about updating");
//    }
//
//    @AfterReturning("onEditEntityPage() && args(id,..)")
//    public void afterReturningOnEditEntityPage(JoinPoint joinPoint, Long id) {
//        addAfterReturningLogMessageWithEntityId(joinPoint, id, "Getting data about updating");
//    }
//
//    @Before("onModifyEntity() && args(entityId,..)")
//    public void beforeOnModifyEntity(JoinPoint joinPoint, Long entityId) {
//        addBeforeLogMessageWithEntityId(joinPoint, entityId, "Updating");
//    }
//
//    @AfterReturning(pointcut = "onModifyEntity() && args(entityId,elementId,attributes,..)", returning = "returning")
//    public void afterReturningOnModifyEntity(JoinPoint joinPoint,
//            Long entityId, Long elementId, RedirectAttributes attributes, Object returning) {
//        addAfterReturningLogMessage(joinPoint, null, attributes, "Updating", returning);
//    }
//
//    @Before("onChangePersonEntity() && args(id,person,..)")
//    public void beforeOnChangePersonEntity(JoinPoint joinPoint, Long id, Person person) {
//        addBeforeLogMessageWithEntity(joinPoint, person, "Updating");
//    }
//
//    @AfterReturning(pointcut = "onChangePersonEntity() && args(id,person,result,model)", returning = "returning")
//    public void afterReturningOnChangePersonEntity(JoinPoint joinPoint,
//            Long id, Person person, BindingResult result, Model model, Object returning) {
//        addAfterReturningLogMessage(joinPoint, model, null, "Updating", returning);
//    }
//
//    @Before("onChangePersonEntity() && args(id,locked,attributes)")
//    public void beforeOnChangePersonEntity(JoinPoint joinPoint,
//            Long id, boolean locked, RedirectAttributes attributes) {
//        logAction(LAYER,
//                joinPoint,
//                "Updating isLocked-field",
//                String.format("for Person (ID = %s)", id));
//    }
//
//    @AfterReturning(pointcut = "onChangePersonEntity() && args(id,locked,attributes)", returning = "returning")
//    public void afterReturningOnChangePersonEntity(JoinPoint joinPoint,
//            Long id, boolean locked, RedirectAttributes attributes, Object returning) {
//        addAfterReturningLogMessage(joinPoint, null, attributes, "Updating", returning);
//    }
//
//    @Before("onChangeGroupStudent() && args(id,student,..)")
//    public void beforeOnChangeGroupStudent(JoinPoint joinPoint, Long id, Student student) {
//        logAction(LAYER,
//                joinPoint,
//                "Changing Group",
//                String.format("for Student (ID = %s)", id));
//    }
//
//    @AfterReturning(pointcut = "onChangeGroupStudent() && args(id,student,result,model)", returning = "returning")
//    public void afterReturningOnChangeGroupStudent(JoinPoint joinPoint,
//            Long id, Student student, BindingResult result, Model model, Object returning) {
//        addAfterReturningLogMessage(joinPoint, model, null, "Changing Group for Student", returning);
//    }

    protected void callAfterReturningLogMessage(JoinPoint joinPoint, Object entity,
            HttpEntity<RestApiSimpleResponse> returning) {
        if (entity == null) {
            logSuccessWithResponse(joinPoint, "Adding 'null' as entity", returning);
        } else {
            logSuccessWithResponse(
                    joinPoint,
                    String.format("Adding %s-entity", entity.getClass().getSimpleName()),
                    returning);
        }
    }

}
