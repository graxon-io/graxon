package io.graxon.library.node;

/**
 * Node register response
 *
 * @param status node status
 */
public record NodeRegistrationResponse(
    NodeStatus status
) {
}
