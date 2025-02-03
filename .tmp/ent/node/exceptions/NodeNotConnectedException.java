package io.graxon.core.entities.node.exceptions;

import io.graxon.library.system.exceptions.EntityNotCreatedException;

/**
 * Exception thrown when a node is not created.
 */
public class NodeNotConnectedException extends EntityNotCreatedException {

    /**
     * Constructor with message.
     */
    public NodeNotConnectedException() {
        super("Node not connected");
    }

}
