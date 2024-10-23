package de.szut.lf8_starter.project;

import de.szut.lf8_starter.employee.EmployeeProjectEntity;
import de.szut.lf8_starter.project.dto.ProjectCreateDto;
import de.szut.lf8_starter.project.dto.ProjectDetailsGetDto;
import de.szut.lf8_starter.project.dto.ProjectGetDto;
import org.springframework.stereotype.Service;

@Service
public class ProjectMapper {

    private final ProjectService projectService;
    public ProjectMapper(ProjectService projectService) {
        this.projectService = projectService;
    }
    public ProjectGetDto mapToGetDto(ProjectEntity entity){
        return new ProjectGetDto(entity.getId(),
                entity.getProjectName(),
                entity.getResponsibleEmployeeId(),
                entity.getCustomerId(),
                entity.getCustomerContactName(),
                entity.getCommentForProjectGoal(),
                entity.getStartDate(),
                entity.getPlannedEnddate(),
                entity.getActualEnddate(),
                entity.getEmployeeIds());
    }
    public ProjectEntity mapCreateDtoToEntity(ProjectCreateDto dto){
        var entity = new ProjectEntity();
        entity.setProjectName(dto.getProjectName());
        entity.setResponsibleEmployeeId(dto.getResponsibleEmployeeId());
        entity.setCustomerId(dto.getCustomerId());
        entity.setCustomerContactName(dto.getCustomerContactName());
        entity.setCommentForProjectGoal(dto.getCommentForProjectGoal());
        entity.setStartDate(dto.getStartDate());
        entity.setPlannedEnddate(dto.getPlannedEnddate());
        entity.setActualEnddate(dto.getActualEnddate());

        entity.setEmployeeIds(dto.getEmployeeIds());
        return entity;
    }

    public ProjectDetailsGetDto mapToDetailsDto(EmployeeProjectEntity employeeProjectEntity) {
        ProjectEntity projectEntity = projectService.readById(employeeProjectEntity.getProjectId());

        return new ProjectDetailsGetDto(
                projectEntity.getId(),
                projectEntity.getProjectName(),
                projectEntity.getStartDate(),
                projectEntity.getPlannedEnddate(),
                employeeProjectEntity.getQualification()
        );
    }
}
