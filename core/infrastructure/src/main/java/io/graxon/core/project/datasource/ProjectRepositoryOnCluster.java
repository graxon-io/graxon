package io.graxon.core.project.datasource;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.graxon.core.project.schema.ProjectCRD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static io.graxon.core.system.configs.CacheConfig.CACHE_PROJECT;
import static io.graxon.core.system.configs.CacheConfig.CACHE_PROJECTS;

/**
 *
 */
@Profile("kubernetes")
@Repository
public class ProjectRepositoryOnCluster implements ProjectRepository {

    //
    private static final Logger log = LoggerFactory.getLogger(ProjectRepositoryOnCluster.class);

    //
    private final String CACHE_PROJECT_KEY = "#project.getMetadata().getName()";
    private final String CACHE_PROJECT_UNLESS = "#result == null";

    //
    private final KubernetesClient kubernetes;

    /**
     *
     */
    public ProjectRepositoryOnCluster(KubernetesClient kubernetes) {
        this.kubernetes = kubernetes;
    }

    /**
     * fetch all projects
     *
     * @return list of projects
     */
    @Cacheable(CACHE_PROJECTS)
    @Override
    public List<ProjectCRD> findAll() {
        log.trace("find all projects");
        return kubernetes.resources(ProjectCRD.class).list().getItems();
    }

    /**
     * find project by id
     *
     * @param id project id
     * @return project
     */
    @Cacheable(value = CACHE_PROJECT, key = "#id", unless = CACHE_PROJECT_UNLESS)
    @Override
    public Optional<ProjectCRD> findById(String id) {
        log.trace("find project by id: {}", id);
        try {
            return Optional.of(kubernetes.resources(ProjectCRD.class).withName(id).get());
        } catch (Exception e) {
            log.trace("project {} not found -> {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * save project
     *
     * @param project project
     * @return saved project
     */
    @Caching(
        put = {
            @CachePut(value = CACHE_PROJECT, key = CACHE_PROJECT_KEY, unless = CACHE_PROJECT_UNLESS)
        },
        evict = {
            @CacheEvict(value = CACHE_PROJECTS, allEntries = true)
        }
    )
    @Override
    public Optional<ProjectCRD> save(ProjectCRD project) {
        log.trace("saving project: {}", project);
        try {
            return Optional.of(kubernetes.resources(ProjectCRD.class).resource(project).create());
        } catch (Exception e) {
            log.trace("project not created -> {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * update project
     *
     * @param project project
     * @return updated project
     */
    @Caching(
        put = {
            @CachePut(value = CACHE_PROJECT, key = CACHE_PROJECT_KEY, unless = CACHE_PROJECT_UNLESS)
        },
        evict = {
            @CacheEvict(value = CACHE_PROJECTS, allEntries = true)
        }
    )
    @Override
    public Optional<ProjectCRD> update(ProjectCRD project) {
        log.trace("updating project: {}", project);
        try {
            return Optional.of(kubernetes.resources(ProjectCRD.class).resource(project).update());
        } catch (Exception e) {
            log.trace("project not updated -> {}", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * delete project
     *
     * @param project project
     */
    @Caching(
        evict = {
            @CacheEvict(value = CACHE_PROJECT, key = CACHE_PROJECT_KEY),
            @CacheEvict(value = CACHE_PROJECTS, allEntries = true)
        }
    )
    @Override
    public void delete(ProjectCRD project) {
        log.info("Deleting project: {}", project);
        try {
            kubernetes.resources(ProjectCRD.class).resource(project).delete();
        } catch (Exception e) {
            log.trace("project not deleted -> {}", e.getMessage());
        }
    }

}
