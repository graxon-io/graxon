package io.graxon.core.usecases.project;

import io.graxon.core.entities.project.exceptions.ProjectNotFoundException;
import io.graxon.core.usecases.project.dto.ProjectDTO;

/**
 *
 */
public interface GetProjectUseCase {

    ProjectDTO execute(String projectId) throws ProjectNotFoundException;

}
