package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.project.dto.ProjectDetailsGetDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeProjectsResponseDto {
    private Long employeeId;
    private List<ProjectDetailsGetDto> projects;
}
