package io.graxon.core.cluster.schema;

import io.graxon.core.entities.cluster.ClusterStatus;

import java.time.LocalDateTime;

/**
 *
 */
public record ClusterCRDStatus(
    String url,
    ClusterStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
