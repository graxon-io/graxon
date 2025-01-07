package io.graxon.core.entities.cluster;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public enum ClusterStatus {
    @JsonProperty("unknown")
    UNKNOWN,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("inactive")
    INACTIVE,
    @JsonProperty("deleted")
    DELETED,
}
