package io.graxon.core.entities.cluster.exceptions;

import io.graxon.library.system.exceptions.EntityNotCreatedException;

/**
 * Exception thrown when a cluster is not created.
 */
public class ClusterNotCreatedException extends EntityNotCreatedException {

    /**
     * Constructor with message.
     */
    public ClusterNotCreatedException() {
        super("Cluster not created");
    }

}
