package io.graxon.core.project.gateway;

import io.graxon.core.entities.project.Project;
import io.graxon.core.entities.project.gateway.ProjectGateway;
import io.graxon.core.project.datasource.ProjectMapper;
import io.graxon.core.project.datasource.ProjectRepository;
import io.graxon.core.project.events.ProjectPublisher;
import io.graxon.core.project.events.ProjectStatusEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *
 */
@Component
public class ProjectGatewayImpl implements ProjectGateway {

    //
    private static final Logger log = LoggerFactory.getLogger(ProjectGatewayImpl.class);

    //
    private final ProjectRepository repository;
    private final ProjectPublisher publisher;

    /**
     * Constructor
     *
     * @param repository project repository
     */
    public ProjectGatewayImpl(ProjectRepository repository, ProjectPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    /**
     * fetch all projects
     *
     * @return list of projects
     */
    @Override
    public List<Project> findAll() {
        log.debug("find all projects");
        return repository.findAll().stream().map(ProjectMapper::toEntity).toList();
    }

    /**
     * find project by id
     *
     * @param id project id
     * @return project
     */
    @Override
    public Optional<Project> findById(String id) {
        log.debug("find project by id: {}", id);
        return repository.findById(id).map(ProjectMapper::toEntity);
    }

    /**
     * save project
     *
     * @param project project
     * @return saved project
     */
    @Override
    public Optional<Project> save(Project project) {
        log.debug("saving project: {}", project);
        return repository.save(ProjectMapper.toSchema(project)).map(ProjectMapper::toEntity);
    }

    /**
     * update project
     *
     * @param project project
     * @return updated project
     */
    @Override
    public Optional<Project> update(Project project) {
        log.debug("updating project: {}", project);
        return repository.update(ProjectMapper.toSchema(project)).map(ProjectMapper::toEntity);
    }

    /**
     * delete project
     *
     * @param project project
     */
    @Override
    public void delete(Project project) {
        log.info("deleting project: {}", project);
        repository.delete(ProjectMapper.toSchema(project));
    }

    /**
     *
     * @param project project
     */
    @Override
    public void publish(Project project) {
        log.debug("publishing project: {}", project);
        publisher.publishProjectStatusEvent(ProjectStatusEventMapper.toEvent(project));
    }
}
