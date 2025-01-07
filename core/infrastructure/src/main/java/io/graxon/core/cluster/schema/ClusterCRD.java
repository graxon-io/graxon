package io.graxon.core.cluster.schema;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.*;

/**
 *
 */
@Group("graxon.io")
@Version("v1")
@Kind("Cluster")
@Singular("cluster")
@Plural("clusters")
public class ClusterCRD extends CustomResource<ClusterCRDSpec, ClusterCRDStatus> implements Namespaced {

}
