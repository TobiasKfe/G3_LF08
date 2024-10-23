package de.szut.lf8_starter.qualification;

import de.szut.lf8_starter.employee.EmployeeGetDto;

public class QualificationDto {

    private long skillId;
    private String skillName;
    private EmployeeGetDto[] employees;

    public long getSkillId() {
        return skillId;
    }

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public EmployeeGetDto[] getEmployees() {
        return employees;
    }

    public void setEmployees(EmployeeGetDto[] employees) {
        this.employees = employees;
    }
}
