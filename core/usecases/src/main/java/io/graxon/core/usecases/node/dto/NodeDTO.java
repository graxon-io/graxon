package io.graxon.core.usecases.node.dto;

import io.graxon.core.entities.node.Node;
import io.graxon.library.node.NodeStatus;

/**
 * Node (Data Transfer Object)
 *
 * @param id
 * @param status
 */
public record NodeDTO(
    String id,
    NodeStatus status
) {
    /**
     * Constructor
     *
     * @param entity node entity
     */
    public NodeDTO(Node entity) {
        this(
            entity.id(),
            entity.status());
    }
}
