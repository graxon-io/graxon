package io.graxon.library.node;

/**
 * Node register request
 *
 * @param clusterId   cluster id
 * @param clusterName cluster name
 * @param nodeId      node id
 * @param namespace   namespace
 * @param podName     pod name
 */
public record NodeRegistrationRequest(
    String clusterId,
    String clusterName,
    String nodeId,
    String namespace,
    String podName
) {
}
