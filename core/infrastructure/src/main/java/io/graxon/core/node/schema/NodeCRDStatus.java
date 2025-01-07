package io.graxon.core.node.schema;

import io.graxon.library.node.NodeStatus;

import java.time.LocalDateTime;

/**
 * Project entity.
 *
 * @param status Status of the project
 *
 */
public record NodeCRDStatus(
    NodeStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
