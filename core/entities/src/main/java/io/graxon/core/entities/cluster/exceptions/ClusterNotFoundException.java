package io.graxon.core.entities.cluster.exceptions;

import io.graxon.library.system.exceptions.EntityNotFoundException;

/**
 * Exception thrown when a cluster is not found.
 */
public class ClusterNotFoundException extends EntityNotFoundException {

    /**
     * Constructor with message.
     */
    public ClusterNotFoundException() {
        super("Cluster not found");
    }

}
