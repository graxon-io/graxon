package io.graxon.library.system.exceptions;

/**
 * Exception thrown when an entity is not found in the database.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }

    /**
     * Default constructor.
     */
    public EntityNotFoundException() {
        super("Entity not found");
    }

}
