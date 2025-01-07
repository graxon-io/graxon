package io.graxon.core.project.datasource;

import io.graxon.core.project.schema.ProjectCRD;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {

    List<ProjectCRD> findAll();

    Optional<ProjectCRD> findById(String id);

    Optional<ProjectCRD> save(ProjectCRD project);

    Optional<ProjectCRD> update(ProjectCRD project);

    void delete(ProjectCRD project);

}
