package io.graxon.gateway.system.exceptions;

/**
 *
 * @param status
 * @param message
 */
public record ErrorMessage(
    Integer status,
    String message
) {
}
