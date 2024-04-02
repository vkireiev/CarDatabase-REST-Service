package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityAddDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityUpdateDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.model.interfaces.Identifiable;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;
import ua.foxmided.foxstudent103852.cardatabaserestservice.service.interfaces.CrudService;

public abstract class DefaultIdentifiableEntityRestApiController<T extends Identifiable<U>, U> {

    protected final Class<T> entityType;
    private final CrudService<T, U> entityService;

    protected DefaultIdentifiableEntityRestApiController(CrudService<T, U> entityService, Class<T> entityType) {
        super();
        this.entityService = entityService;
        this.entityType = entityType;
    }

    protected <X> X returnEntityOrThrowEntityNotFoundException(Optional<X> optionalEntity,
            String exceptionMessage, String... args) {
        return optionalEntity.orElseThrow(() -> {
            String message = (exceptionMessage == null || exceptionMessage.isBlank())
                    ? "Entity not found"
                    : exceptionMessage;
            message = message + Arrays.asList(args).stream()
                    .collect(Collectors.joining(", ", ", ", ""));
            return new EntityNotFoundException(message);
        });
    }

    protected HttpEntity<RestApiSimpleResponse> getEntityByIdImpl(U id) {
        T entity = returnEntityOrThrowEntityNotFoundException(entityService.get(id),
                entityType.getSimpleName() + " (ID=" + id + ") not found");

        return ResponseEntityHelper.of(HttpStatus.OK,
                entity);
    }

    protected HttpEntity<RestApiSimpleResponse> getAllEntitiesImpl(Pageable pageable) {

        return ResponseEntityHelper.of(HttpStatus.OK,
                entityService.getAll(pageable));
    }

    protected HttpEntity<RestApiSimpleResponse> addEntityImpl(T newEntity) {
        try {
            entityService.add(newEntity);
        } catch (EntityAddDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.CREATED);
    }

    protected HttpEntity<RestApiSimpleResponse> updateEntityByIdImpl(U id, T updateEntity) {
        try {
            updateEntity.setId(id);
            updateEntity = entityService.update(updateEntity);
        } catch (EntityUpdateDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.OK,
                updateEntity);
    }

    protected HttpEntity<RestApiSimpleResponse> deleteEntityByIdImpl(U id) {
        try {
            if (!entityService.deleteById(id)) {
                return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
            }
        } catch (EntityDataIntegrityViolationException e) {
            throw new EntityDataIntegrityViolationException(e.getMessage(), e);
        }

        return ResponseEntityHelper.of(HttpStatus.OK);
    }

}
