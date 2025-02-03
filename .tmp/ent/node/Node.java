package io.graxon.core.entities.node;

import io.graxon.library.node.NodeStatus;

import java.time.LocalDateTime;

/**
 *
 * @param id
 * @param status
 * @param createdAt
 * @param updatedAt
 * @param clusterId
 */
public record Node(
    String id,
    NodeStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String clusterId
) {
}
