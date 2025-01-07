package io.graxon.core.usecases.project;

import io.graxon.core.entities.project.exceptions.ProjectNotCreatedException;
import io.graxon.core.usecases.project.dto.ProjectDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 */
public interface CreateProjectUseCase {

    //
    ProjectDTO execute(

        @NotNull
        @Valid
        CreateProjectRequest request

    ) throws ProjectNotCreatedException;

    /**
     *
     * @param name
     * @param description
     */
    record CreateProjectRequest(

        @NotBlank
        String name,

        @Nullable
        String description

    ) {}
}
