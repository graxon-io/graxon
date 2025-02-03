package io.graxon.core.project.controllers;

import io.graxon.core.usecases.project.CreateProjectUseCase;
import io.graxon.core.usecases.project.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Tag(name = "Project")
@RestController
@RequestMapping("/api/v1/projects")
public class CreateProjectController {

    //
    private final CreateProjectUseCase useCase;

    /**
     */
    public CreateProjectController(CreateProjectUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     */
    @PostMapping
    public ProjectDTO createProject(@RequestBody @Valid CreateProjectUseCase.CreateProjectRequest request) {
        return useCase.execute(request);
    }

}
