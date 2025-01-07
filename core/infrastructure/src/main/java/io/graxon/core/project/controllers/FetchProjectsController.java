package io.graxon.core.project.controllers;

import io.graxon.core.usecases.project.FetchProjectsUseCase;
import io.graxon.core.usecases.project.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@Tag(name = "Project")
@RestController
@RequestMapping("/api/v1/projects")
public class FetchProjectsController {

    //
    private final FetchProjectsUseCase useCase;

    //
    public FetchProjectsController(FetchProjectsUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     *
     */
    @GetMapping
    public List<ProjectDTO> fetchProjects() {
        return useCase.execute();
    }
}
