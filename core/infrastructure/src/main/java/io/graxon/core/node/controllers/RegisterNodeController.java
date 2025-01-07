package io.graxon.core.node.controllers;

import io.graxon.core.usecases.node.RegisterNodeUseCase;
import io.graxon.library.node.NodeRegistrationRequest;
import io.graxon.library.node.NodeRegistrationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Tag(name = "Node")
@RestController
@RequestMapping("/api/v1/node/register")
public class RegisterNodeController {

    //
    private final RegisterNodeUseCase useCase;

    /**
     *
     */
    public RegisterNodeController(RegisterNodeUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     */
    @PostMapping
    public NodeRegistrationResponse registerNode(@RequestBody NodeRegistrationRequest connection) {
        return useCase.execute(connection);
    }

}
