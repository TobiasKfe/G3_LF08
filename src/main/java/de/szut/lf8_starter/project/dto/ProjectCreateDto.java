package de.szut.lf8_starter.project.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ProjectCreateDto {

    @NotNull(message = "project name is mandatory")
    private String projectName;

    private long responsibleEmployeeId;

    @NotNull(message = "customer is mandatory")
    private long customerId;

    @NotNull(message = "customer contact name is mandatory")
    private String customerContactName;

    @NotNull(message = "comment for project goal is mandatory")
    private String commentForProjectGoal;

    @NotNull(message = "start date is mandatory")
    private Date startDate;

    @NotNull(message = "planned end date is mandatory")
    private Date plannedEnddate;

    private Date actualEnddate;

    @JsonCreator
    public ProjectCreateDto(String projectName, long responsibleEmployeeId, long customerId, String customerContactName, String commentForProjectGoal, Date startDate, Date plannedEnddate, Date actualEnddate) {
        this.projectName = projectName;
        this.responsibleEmployeeId = responsibleEmployeeId;
        this.customerId = customerId;
        this.customerContactName = customerContactName;
        this.commentForProjectGoal = commentForProjectGoal;
        this.startDate = startDate;
        this.plannedEnddate = plannedEnddate;
        this.actualEnddate = actualEnddate;
    }
}
