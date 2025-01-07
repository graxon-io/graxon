package io.graxon.core.usecases.project;

import io.graxon.core.entities.project.Project;
import io.graxon.core.entities.project.exceptions.ProjectNotCreatedException;
import io.graxon.core.entities.project.gateway.ProjectGateway;
import io.graxon.core.usecases.project.dto.ProjectDTO;
import io.graxon.library.enums.ProjectStatus;
import io.graxon.library.system.annotations.UseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 *
 */
@UseCase
class CreateProjectUseCaseImpl implements CreateProjectUseCase {

    //
    private final Logger log = LoggerFactory.getLogger(getClass().getName());

    //
    private final ProjectGateway gateway;

    /**
     * Constructor
     *
     * @param gateway ProjectGateway
     */
    CreateProjectUseCaseImpl(ProjectGateway gateway) {
        this.gateway = gateway;
    }

    /**
     */
    @Override
    public ProjectDTO execute(CreateProjectRequest request) throws ProjectNotCreatedException {
        log.info("create project: {}", request);
        var entity = new Project(
            UUID.randomUUID().toString().substring(0, 8),
            request.name(),
            request.description(),
            ProjectStatus.CREATING
        );

        // save the project
        var saved = gateway.save(entity).orElseThrow(ProjectNotCreatedException::new);

        // publish the project
        gateway.publish(saved);

        // return the data transfer object
        return new ProjectDTO(saved);
    }
}
