package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is not deleted.
 */
public class EntityNotDeletedException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityNotDeletedException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityNotDeletedException() {
        super("Entity not deleted");
    }
    
}
