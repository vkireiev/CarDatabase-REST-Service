package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.DataProcessingException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityDataIntegrityViolationException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.exception.EntityNotFoundException;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.ResponseEntityHelper;
import ua.foxmided.foxstudent103852.cardatabaserestservice.response.RestApiSimpleResponse;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public HttpEntity<RestApiSimpleResponse> handleAuthenticationException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntityHelper.of(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public HttpEntity<RestApiSimpleResponse> handleAccessDeniedException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntityHelper.of(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {
            MissingPathVariableException.class,
            EntityNotFoundException.class })
    public HttpEntity<RestApiSimpleResponse> handleEntityNotFoundException(Exception ex) {
        log.warn(ex.getMessage(), ex);
        return ResponseEntityHelper.of(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            EntityDataIntegrityViolationException.class,
            HttpRequestMethodNotSupportedException.class,
            ValidationException.class,
            TypeMismatchException.class,
            MethodArgumentNotValidException.class,
            PropertyReferenceException.class,
            HttpMessageConversionException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpEntity<RestApiSimpleResponse> handleValidationException(Exception ex) {
        log.info(ex.getMessage(), ex);
        return ResponseEntityHelper.of(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataProcessingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpEntity<RestApiSimpleResponse> handleDataProcessingException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntityHelper.of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
