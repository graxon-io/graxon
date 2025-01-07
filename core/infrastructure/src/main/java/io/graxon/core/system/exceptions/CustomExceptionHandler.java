package io.graxon.core.system.exceptions;

import io.graxon.library.system.exceptions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * For CRUD operations, the following HTTP error codes are typically used when an exception is triggered:
 * <p>
 * Create (POST):
 * 409 Conflict: When there is a conflict with the current state of the resource (e.g., duplicate entry).
 * 400 Bad Request: When the request is malformed or invalid.
 * <p>
 * Read (GET):
 * 404 Not Found: When the requested resource is not found.
 * 400 Bad Request: When the request is malformed or invalid.
 * <p>
 * Update (PUT/PATCH):
 * 404 Not Found: When the resource to be updated is not found.
 * 409 Conflict: When there is a conflict with the current state of the resource.
 * 400 Bad Request: When the request is malformed or invalid.
 * <p>
 * Delete (DELETE):
 * 404 Not Found: When the resource to be deleted is not found.
 * 400 Bad Request: When the request is malformed or invalid.
 * <p>
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    // --------------------------------------------------------------------------------------------

    //
    private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    // +++++++++++++++++++++++++++++++++
    // entity exceptions

    /**
     * Handle EntityNotCreatedException, EntityNotUpdatedException, EntityNotDeletedException.
     *
     * @param ex Exception
     * @return ResponseEntity with status 409
     */
    @ExceptionHandler(value = {
        EntityNotCreatedException.class,
        EntityNotUpdatedException.class,
        EntityNotDeletedException.class,
        EntityAlreadyExistsException.class
    })
    public ResponseEntity<ErrorMessage> handleConflictException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handle EntityInvalidStateException.
     *
     * @param ex EntityInvalidStateException
     * @return ResponseEntity with status 406
     */
    @ExceptionHandler(EntityInvalidStateException.class)
    public ResponseEntity<ErrorMessage> handleNotAcceptableException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handle EntityNotFoundException.
     *
     * @param ex EntityNotFoundException
     * @return ResponseEntity with status 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    // +++++++++++++++++++++++++++++++++
    // fallback exception

    /**
     * handle Exception
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.warn("error class: {}", e.getClass().getCanonicalName());
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
