package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is not updated.
 */
public class EntityNotUpdatedException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityNotUpdatedException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityNotUpdatedException() {
        super("Entity not updated");
    }
    
}
