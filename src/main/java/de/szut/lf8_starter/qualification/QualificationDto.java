package de.szut.lf8_starter.qualification;

import de.szut.lf8_starter.employee.EmployeeGetDto;

public class QualificationDto {

    private long id;
    private String skill;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

}
