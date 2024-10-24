package de.szut.lf8_starter.project;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) { this.repository = repository; }

    public ProjectEntity create(ProjectEntity entity) { return this.repository.save(entity); }

    public List<ProjectEntity> getAll() { return this.repository.findAll(); }

    public ProjectEntity readById(long id) { return this.repository.findById(id).orElse(null); }

    public void delete(ProjectEntity entity) { this.repository.delete(entity); }

    public void update(ProjectEntity entity) { repository.save(entity);}
}
