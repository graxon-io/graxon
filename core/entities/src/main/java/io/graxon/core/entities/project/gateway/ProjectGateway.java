package io.graxon.core.entities.project.gateway;

import io.graxon.core.entities.project.Project;

import java.util.List;
import java.util.Optional;

/**
 * 
 */
public interface ProjectGateway {

    /**
     * fetch all projects
     *
     * @return list of projects
     */
    List<Project> findAll();

    /**
     * find project by id
     *
     * @param id project id
     * @return project
     */
    Optional<Project> findById(String id);

    /**
     * save project
     *
     * @param project project
     * @return saved project
     */
    Optional<Project> save(Project project);

    /**
     * update project
     *
     * @param project project
     * @return updated project
     */
    Optional<Project> update(Project project);

    /**
     * delete project
     *
     * @param project project
     */
    void delete(Project project);

    /**
     * publish project
     *
     * @param project project
     */
    void publish(Project project);
}
