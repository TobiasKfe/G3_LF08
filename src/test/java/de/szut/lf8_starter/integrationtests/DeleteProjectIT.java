package de.szut.lf8_starter.integrationtests;

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

public class DeleteProjectIT extends AbstractIntegrationTest {
    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(delete("/project/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "user")
    void happyPath() throws Exception{
        ProjectEntity stored = projectRepository.save(new ProjectEntity());

        final var content = this.mockMvc.perform(
                delete("/project/" + stored.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());
        assertThat(projectRepository.findById(stored.getId()).isPresent()).isFalse();
    }

    @Test
    @WithMockUser(roles = "user")
    void idDoesNotExist() throws Exception {
        final var contentAsString = this.mockMvc.perform(delete("/project/5")
                        .with(csrf()))
                .andExpect(content().string(containsString("ProjectEntity not found on id = 5")))
                .andExpect(status().isNotFound());
    }
}
