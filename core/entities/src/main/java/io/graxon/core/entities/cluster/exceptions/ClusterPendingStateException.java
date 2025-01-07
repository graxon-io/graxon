package io.graxon.core.entities.cluster.exceptions;

import io.graxon.library.system.exceptions.EntityInvalidStateException;

/**
 * Exception thrown when a cluster is still in pending state.
 */
public class ClusterPendingStateException extends EntityInvalidStateException {

    /**
     * Constructor with message.
     */
    public ClusterPendingStateException() {
        super("Cluster is in pending state");
    }

}
