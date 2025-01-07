package io.graxon.core.entities.node.exceptions;

import io.graxon.library.system.exceptions.EntityAlreadyExistsException;

/**
 * Exception thrown when a node is not found.
 */
public class NodeAlreadyExistsException extends EntityAlreadyExistsException {

    /**
     * Constructor with message.
     */
    public NodeAlreadyExistsException() {
        super("Node already exists");
    }

}
