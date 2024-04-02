package ua.foxmided.foxstudent103852.cardatabaserestservice.exception;

public class EntityUpdateDataIntegrityViolationException extends EntityDataIntegrityViolationException {

    public EntityUpdateDataIntegrityViolationException(String message) {
        super(message);
    }

    public EntityUpdateDataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
