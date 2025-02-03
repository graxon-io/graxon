package io.graxon.core.project.controllers;

import io.graxon.core.usecases.project.GetProjectUseCase;
import io.graxon.core.usecases.project.dto.ProjectDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Tag(name = "Project")
@RestController
@RequestMapping("/api/v1/projects")
public class GetProjectController {

    //
    private final GetProjectUseCase useCase;

    //
    public GetProjectController(GetProjectUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     *
     */
    @GetMapping("/{project_id}")
    public ProjectDTO getProject(@PathVariable("project_id") String projectId) {
        return useCase.execute(projectId);
    }
}
