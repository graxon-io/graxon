package io.graxon.core.usecases.project;

import io.graxon.core.entities.project.exceptions.ProjectNotFoundException;
import io.graxon.core.entities.project.gateway.ProjectGateway;
import io.graxon.core.usecases.project.dto.ProjectDTO;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
@UseCase
class GetProjectUseCaseImpl implements GetProjectUseCase {

    //
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    //
    private final ProjectGateway gateway;

    /**
     * Constructor
     *
     * @param gateway ProjectGateway
     */
    GetProjectUseCaseImpl(ProjectGateway gateway) {
        this.gateway = gateway;
    }

    /**
     * @param projectId String
     * @return ProjectDTO
     * @throws ProjectNotFoundException ProjectNotFoundException
     */
    @Override
    public ProjectDTO execute(String projectId) throws ProjectNotFoundException {
        log.info("get project: {}", projectId);
        return gateway.findById(projectId).map(ProjectDTO::new).orElseThrow(ProjectNotFoundException::new);
    }
}
