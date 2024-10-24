package de.szut.lf8_starter.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProjectValidator {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectValidator(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean doesProjectExist(Long id) {
        return projectRepository.existsById(id);
    }

}
