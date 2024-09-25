package de.szut.lf8_starter.project;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
