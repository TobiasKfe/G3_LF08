package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.employee.EmployeeProjectEntity;
import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteEmployeeProjectIT extends AbstractIntegrationTest {
    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/project/1/employee/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "user")
    void happyPath() throws Exception{
        EmployeeProjectEntity stored = employeeProjectRepository.save(new EmployeeProjectEntity(1L, 1L, 1L));

        final var content = this.mockMvc.perform(
                        delete("/project/" + stored.getProjectId() + "/employee/" + stored.getEmployeeId())
                                .with(csrf()))
                .andExpect(status().isNoContent());
        assertThat(projectRepository.findById(stored.getId()).isPresent()).isFalse();
    }
}
