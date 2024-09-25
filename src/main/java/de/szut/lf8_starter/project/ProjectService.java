package de.szut.lf8_starter.project;

public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) { this.repository = repository; }

    public ProjectEntity create(ProjectEntity entity) { return this.repository.save(entity); }
}
