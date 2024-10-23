package de.szut.lf8_starter.employee;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeProjectService {
    private EmployeeProjectRepository repository;
    public EmployeeProjectService(EmployeeProjectRepository repository){
        this.repository = repository;
    }
    public EmployeeProjectEntity getEmployeeProjectByEmployeeIdAndProjectId(long employeeId, long projectId){
        return repository.findByEmployeeIdAndProjectId(employeeId, projectId).orElse(null);
    }

    public List<EmployeeProjectEntity> getAllProjectsByEmployeeId(long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public void delete(EmployeeProjectEntity entity) {
        this.repository.delete(entity);
    }
}
