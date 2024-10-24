package de.szut.lf8_starter.employee;

import de.szut.lf8_starter.qualification.QualificationDto;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeGetDto {
    private long id;
    private String firstName;
    private String lastName;
    private List<QualificationDto> qualificationIds;
}
