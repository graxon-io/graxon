package io.graxon.core.usecases.project;

import io.graxon.core.entities.project.gateway.ProjectGateway;
import io.graxon.core.usecases.project.dto.ProjectDTO;

import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Fetch all namespaces use case
 */
@UseCase
class FetchProjectsUseCaseImpl implements FetchProjectsUseCase {

    //
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    //
    private final ProjectGateway gateway;

    /**
     * Constructor
     *
     * @param gateway ProjectGateway
     */
    FetchProjectsUseCaseImpl(ProjectGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * Fetches all namespaces
     *
     * @return List<Project>
     */
    @Override
    public List<ProjectDTO> execute() {
        log.info("fetch all projects");
        return gateway.findAll().stream().map(ProjectDTO::new).toList();
    }
}
