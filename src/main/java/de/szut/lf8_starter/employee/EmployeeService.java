package de.szut.lf8_starter.employee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {

    private final RestTemplate restTemplate = new RestTemplate();
    private String employeeUrl = "https://employee.szut.dev/employees";

    public EmployeeGetDto getEmployeeById(long id) {
        return this.restTemplate.getForObject(employeeUrl + "/{id}", EmployeeGetDto.class, id);
    }

}
