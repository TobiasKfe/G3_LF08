package de.szut.lf8_starter.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProjectEntity, Long> {
    public Optional<EmployeeProjectEntity> findByEmployeeIdAndProjectId(long employeeId, long projectId);
}
