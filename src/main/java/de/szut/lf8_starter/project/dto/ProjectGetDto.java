package de.szut.lf8_starter.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ProjectGetDto {
    private long id;

    private String projectName;
    private long responsibleEmployeeId;
    private long customerId;
    private String customerContactName;
    private String commentForProjectGoal;
    private Date startDate;
    private Date plannedEnddate;
    private Date actualEnddate;
}
