package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is in an invalid state.
 */
public class EntityInvalidStateException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityInvalidStateException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityInvalidStateException() {
        super("Entity is in an invalid state.");
    }
    
}
