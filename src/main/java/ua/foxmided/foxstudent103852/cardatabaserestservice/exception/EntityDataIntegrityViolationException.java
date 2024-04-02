package ua.foxmided.foxstudent103852.cardatabaserestservice.exception;

public class EntityDataIntegrityViolationException extends DataProcessingException {

    public EntityDataIntegrityViolationException(String message) {
        super(message);
    }

    public EntityDataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
