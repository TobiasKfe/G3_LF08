package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import de.szut.lf8_starter.employee.EmployeeProjectEntity;
import de.szut.lf8_starter.project.ProjectEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetProjectsByEmployeeIdIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/project/employee/1/projects")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void happyPath() throws Exception {
        // Testdaten erstellen
        ProjectEntity project1 = new ProjectEntity(1L, "Project 1", 1L, 1L, "Contact 1", "Goal 1", null, null, null);
        ProjectEntity project2 = new ProjectEntity(2L, "Project 2", 1L, 1L, "Contact 2", "Goal 2", null, null, null);

        projectRepository.save(project1);
        projectRepository.save(project2);

        employeeProjectRepository.save(new EmployeeProjectEntity(1L, 1L, 1L, "Developer"));
        employeeProjectRepository.save(new EmployeeProjectEntity(2L, 1L, 2L, "Tester"));

        // GET-Anfrage ausführen
        this.mockMvc.perform(get("/project/employee/{employeeId}/projects", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Project 1")))
                .andExpect(jsonPath("$[0].qualification", is("Developer")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Project 2")))
                .andExpect(jsonPath("$[1].qualification", is("Tester")));
    }

    @Test
    @WithMockUser(roles = "user")
    void noProjectsFound() throws Exception {
        this.mockMvc.perform(get("/project/employee/9/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
