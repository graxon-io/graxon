package io.graxon.library.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
public enum ProjectStatus {
    @JsonProperty("unknown")
    UNKNOWN,
    @JsonProperty("creating")
    CREATING,
    @JsonProperty("created")
    CREATED,
    @JsonProperty("failed")
    FAILED,
    @JsonProperty("deleted")
    DELETED
}
