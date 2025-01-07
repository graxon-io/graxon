package io.graxon.core.entities.cluster.exceptions;

import io.graxon.library.system.exceptions.EntityNotFoundException;

/**
 * Exception thrown when a cluster is still in pending state.
 */
public class ClusterKeystoreNotFoundException extends EntityNotFoundException {

    /**
     * Constructor with message.
     */
    public ClusterKeystoreNotFoundException() {
        super("Cluster keystores not found");
    }

}
