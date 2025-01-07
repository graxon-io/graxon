package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is already present.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityAlreadyExistsException() {
        super("Entity already exists.");
    }
    
}
