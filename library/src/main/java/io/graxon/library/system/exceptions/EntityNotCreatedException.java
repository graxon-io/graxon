package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is not created.
 */
public class EntityNotCreatedException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityNotCreatedException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityNotCreatedException() {
        super("Entity not created");
    }
    
}
