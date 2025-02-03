package io.graxon.core.entities.node.exceptions;

import io.graxon.library.system.exceptions.EntityNotCreatedException;

/**
 * Exception thrown when a node is not updated.
 */
public class NodeNotUpdatedException extends EntityNotCreatedException {

    /**
     * Constructor with message.
     */
    public NodeNotUpdatedException() {
        super("Node not updated");
    }

}
