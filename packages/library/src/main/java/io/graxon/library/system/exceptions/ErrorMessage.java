package io.graxon.library.system.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Error message
 *
 * @param message message
 * @param errors  errors
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorMessage(

    @JsonProperty("message")
    String message,

    @JsonProperty("errors")
    List<ErrorItem> errors

) {

    /**
     * Constructor
     *
     * @param message message
     */
    public ErrorMessage(String message) {
        this(message, null);
    }

    /**
     * @param field
     * @param message
     */
    public record ErrorItem(

        @JsonProperty("field")
        String field,

        @JsonProperty("message")
        String message

    ) {
    }
}
