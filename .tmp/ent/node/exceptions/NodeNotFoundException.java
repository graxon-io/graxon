package io.graxon.core.entities.node.exceptions;

import io.graxon.library.system.exceptions.EntityNotFoundException;

/**
 * Exception thrown when a node is not found.
 */
public class NodeNotFoundException extends EntityNotFoundException {

    /**
     * Constructor with message.
     */
    public NodeNotFoundException() {
        super("Node not found");
    }

}
