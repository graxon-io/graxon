package io.graxon.library.node;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public enum NodeStatus {
    @JsonProperty("unknown")
    UNKNOWN,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("active")
    ACTIVE,
    @JsonProperty("unreachable")
    UNREACHABLE,
    @JsonProperty("deleted")
    DELETED
}
