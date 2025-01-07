package io.graxon.core.usecases.node;

import io.graxon.core.entities.node.exceptions.NodeNotConnectedException;
import io.graxon.core.entities.node.exceptions.NodeNotCreatedException;
import io.graxon.library.node.NodeRegistrationRequest;
import io.graxon.library.node.NodeRegistrationResponse;
import jakarta.validation.constraints.NotNull;

/**
 *
 */
public interface RegisterNodeUseCase {

    //
    NodeRegistrationResponse execute(

        @NotNull
        NodeRegistrationRequest connection

    ) throws NodeNotConnectedException, NodeNotCreatedException;
}
