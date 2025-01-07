package io.graxon.core.entities.cluster;

import java.time.LocalDateTime;

/**
 *
 * @param id
 * @param name
 * @param url
 * @param status
 * @param createdAt
 * @param updatedAt
 */
public record Cluster(
    String id,
    String name,
    String url,
    ClusterStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
