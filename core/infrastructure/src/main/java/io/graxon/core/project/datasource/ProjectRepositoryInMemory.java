package io.graxon.core.project.datasource;

import io.graxon.core.project.schema.ProjectCRD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Profile("!kubernetes")
@Repository
public class ProjectRepositoryInMemory implements ProjectRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(ProjectRepositoryInMemory.class);

    //
    private final Map<String, ProjectCRD> database = new HashMap<>();

    /**
     * fetch all projects
     *
     * @return list of projects
     */
    @Override
    public List<ProjectCRD> findAll() {
        log.trace("find all projects");
        return database.values().stream().toList();
    }

    /**
     * find project by id
     *
     * @param id project id
     * @return project
     */
    @Override
    public Optional<ProjectCRD> findById(String id) {
        log.trace("find project by id: {}", id);
        return Optional.ofNullable(database.get(id));
    }

    /**
     * save project
     *
     * @param project project
     * @return saved project
     */
    @Override
    public Optional<ProjectCRD> save(ProjectCRD project) {
        log.trace("saving project: {}", project);
        database.put(project.getMetadata().getName(), project);
        return Optional.of(project);
    }

    /**
     * update project
     *
     * @param project project
     * @return updated project
     */
    @Override
    public Optional<ProjectCRD> update(ProjectCRD project) {
        log.trace("updating project: {}", project);
        database.put(project.getMetadata().getName(), project);
        return Optional.of(project);
    }

    /**
     * delete project
     *
     * @param project project
     */
    @Override
    public void delete(ProjectCRD project) {
        log.info("Deleting project: {}", project);
        database.remove(project.getMetadata().getName());
    }

}
