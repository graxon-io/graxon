package io.graxon.core.usecases.project;

import io.graxon.core.usecases.project.dto.ProjectDTO;

import java.util.List;

/**
 *
 */
public interface CollectProjectsUseCase {

    List<ProjectDTO> execute();

}
