package io.graxon.core.project.controllers.admin;

import io.graxon.core.usecases.project.CollectProjectsUseCase;
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
@RequestMapping("/api/admin/v1/projects")
public class CollectProjectsController {

    //
    private final CollectProjectsUseCase useCase;

    //
    public CollectProjectsController(CollectProjectsUseCase useCase) {
        this.useCase = useCase;
    }

    /**
     *
     */
    @GetMapping
    public List<ProjectDTO> collectProjects() {
        return useCase.execute();
    }
}
