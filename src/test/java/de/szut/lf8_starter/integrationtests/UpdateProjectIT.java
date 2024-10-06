package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UpdateProjectIT extends AbstractIntegrationTest {
    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(put("/project"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="user")
    void happyPath() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("before update");
        ProjectEntity project2 = new ProjectEntity();
        project2.setProjectName("to be updated");
    }
}
