package de.szut.lf8_starter.employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employeeProject")
public class EmployeeProjectEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long employeeId;
    private Long projectId;
    private String qualification;
}
