package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FindProjectByIdIT extends AbstractIntegrationTest {

    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/project/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void happyPath() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("Projekt A");
        project.setResponsibleEmployeeId(123);
        project.setCustomerId(456);
        project.setCustomerContactName("Max Mustermann");
        project.setCommentForProjectGoal("Ziel: Erfolgreiche Implementierung");
        project.setStartDate(new Date());
        project.setPlannedEnddate(new Date());
        project.setActualEnddate(null);

        projectRepository.save(project);

        this.mockMvc.perform(get("/project/" + project.getId())
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is((int) project.getId())))
                .andExpect(jsonPath("$.projectName", is("Projekt A")))
                .andExpect(jsonPath("$.responsibleEmployeeId", is(123)))
                .andExpect(jsonPath("$.customerId", is(456)))
                .andExpect(jsonPath("$.customerContactName", is("Max Mustermann")))
                .andExpect(jsonPath("$.commentForProjectGoal", is("Ziel: Erfolgreiche Implementierung")));
    }

    @Test
    @WithMockUser(roles = "user")
    void idDoesNotExist() throws Exception {
        final var contentAsString = this.mockMvc.perform(get("/project/5")
                        .with(csrf()))
                .andExpect(content().string(containsString("ProjectEntity not found on id = 5")))
                .andExpect(status().isNotFound());
    }
}
