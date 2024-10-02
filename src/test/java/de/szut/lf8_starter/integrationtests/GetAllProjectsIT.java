package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.hello.HelloEntity;
import de.szut.lf8_starter.project.ProjectEntity;
import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllProjectsIT extends AbstractIntegrationTest {
    @Test
    void authorization() throws Exception {
        this.mockMvc.perform(get("/hello/")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "user")
    void findAll() throws Exception {
        ProjectEntity project = new ProjectEntity();
        project.setProjectName("Projekt 1");
        ProjectEntity project2 = new ProjectEntity();
        project2.setProjectName("Projekt 2");
        project = projectRepository.save(project);
        project2 = projectRepository.save(project2);

        final var contentAsString = this.mockMvc.perform(get("/project")
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].projectName", is(project.getProjectName())))
                .andExpect(jsonPath("$[1].projectName", is(project2.getProjectName())));
    }
}
