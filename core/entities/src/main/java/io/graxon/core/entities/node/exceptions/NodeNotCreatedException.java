package io.graxon.core.entities.node.exceptions;

import io.graxon.library.system.exceptions.EntityNotCreatedException;

/**
 * Exception thrown when a node is not created.
 */
public class NodeNotCreatedException extends EntityNotCreatedException {

    /**
     * Constructor with message.
     */
    public NodeNotCreatedException() {
        super("Node not created");
    }

}
