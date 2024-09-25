package de.szut.lf8_starter.integrationtests;

import de.szut.lf8_starter.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostProjectIT extends AbstractIntegrationTest {
   @Test
    void postProject() throws Exception {
        String content = """
                {
                    "projectName": "New Website Development",
                    "responsibleEmployeeId": 1,
                    "customerId": 4,
                    "customerContactName": "Max Mustermann",
                    "commentForProjectGoal": "Develop a new e-commerce website for the customer.",
                    "startDate": "2024-01-01",
                    "plannedEnddate": "2024-06-01",
                    "actualEnddate": null
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/project/") // Endpoint geändert
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("projectName", is("New Website Development")))
                .andExpect(jsonPath("responsibleEmployeeId", is(1)))
                .andExpect(jsonPath("customerId", is(4)))
                .andExpect(jsonPath("customerContactName", is("Max Mustermann")))
                .andExpect(jsonPath("commentForProjectGoal", is("Develop a new e-commerce website for the customer.")))
                .andExpect(jsonPath("startDate", is("2024-01-01")))
                .andExpect(jsonPath("plannedEnddate", is("2024-06-01")))
                .andExpect(jsonPath("actualEnddate").doesNotExist()) // Prüft, ob "actualEnddate" nicht gesetzt ist
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());
        final var loadedEntity = projectRepository.findById(id);

        assertThat(loadedEntity.isPresent()).isTrue();
        assertThat(loadedEntity.get().getProjectName()).isEqualTo("New Website Development");
        assertThat(loadedEntity.get().getResponsibleEmployeeId()).isEqualTo(1);
        assertThat(loadedEntity.get().getCustomerId()).isEqualTo(4);
        assertThat(loadedEntity.get().getCustomerContactName()).isEqualTo("Max Mustermann");
        assertThat(loadedEntity.get().getCommentForProjectGoal()).isEqualTo("Develop a new e-commerce website for the customer");

        assertThat(loadedEntity.get().getStartDate()).isEqualTo(LocalDate.of(2024, 1, 1));
        assertThat(loadedEntity.get().getPlannedEnddate()).isEqualTo(LocalDate.of(2024, 6, 1));
        assertThat(loadedEntity.get().getActualEnddate()).isNull();
    }
}
