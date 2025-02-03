package io.graxon.core.node.schema;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.*;

/**
 *
 */
@Group("graxon.io")
@Version("v1")
@Kind("Node")
@Singular("node")
@Plural("nodes")
public class NodeCRD extends CustomResource<NodeCRDSpec, NodeCRDStatus> implements Namespaced {

}
